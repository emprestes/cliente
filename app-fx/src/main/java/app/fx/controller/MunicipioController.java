package app.fx.controller;

import app.fx.util.AlertHelper;
import domain.Domain;
import domain.exception.MunicipioInvalidoException;
import domain.model.Municipio;
import facade.api.Facade;
import facade.impl.MunicipioFacade;
import facade.impl.UFFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.Map;

public class MunicipioController extends AbstractController {

    public static final String STYLE_URL = "/app/fx/view/municipio.css";

    public static final String VIEW_URL = "/app/fx/view/municipio.fxml";
    public static final String VIEW_TITLE = "Cadastro de Municípios";

    private MunicipioFacade facade;
    private UFFacade ufFacade;

    private Domain aalterar = null;

    @FXML
    private ComboBox<Object> cbUF;

    @FXML
    private TextField tfNOME;

    @FXML
    private TableView<app.fx.model.Municipio> tvMUNICIPIOS;

    private ContextMenu cm;

    @FXML
    private TableColumn<app.fx.model.Municipio, String> tcNOME;

    @FXML
    private TableColumn<app.fx.model.Municipio, Object> tcUF;

    @FXML
    public void initialize() {
        initFacades();
        initView();
        initActions();
    }

    private void initFacades() {
        facade = new MunicipioFacade();
        ufFacade = new UFFacade();
    }

    private void initActions() {
        MenuItem mi;

        mi = cm.getItems().get(0);
        mi.setOnAction(this::onUpdateAction);

        mi = cm.getItems().get(1);
        mi.setOnAction(this::onDeleteAction);

        mi = cm.getItems().get(3);
        mi.setOnAction(e -> {
            TableViewSelectionModel<app.fx.model.Municipio> model;
            int size, sizeSelected;

            model = tvMUNICIPIOS.getSelectionModel();
            size = tvMUNICIPIOS.getItems().size();
            sizeSelected = model.getSelectedItems().size();

            if (size != sizeSelected) {
                ((MenuItem) e.getSource()).setText("Limpar seleção");
                model.selectAll();
            } else {
                ((MenuItem) e.getSource()).setText("Selecionar todos");
                model.clearSelection();
            }
        });
    }

    private void initView() {
        MenuItem mi;

        cm = new ContextMenu();
        mi = new MenuItem("Atualizar");
        cm.getItems().add(0, mi);
        mi = new MenuItem("Apagar");
        cm.getItems().add(1, mi);
        cm.getItems().add(2, new SeparatorMenuItem());
        mi = new MenuItem("Selecionar todos");
        cm.getItems().add(3, mi);

        tvMUNICIPIOS.setContextMenu(cm);
        tvMUNICIPIOS.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cbUF.getItems().addAll(ufFacade.listarUFs());
        cbUF.setValue(ufFacade.getSelecione());

        tcNOME.setCellValueFactory(data -> data.getValue().getNome());
        tcUF.setCellValueFactory(data -> data.getValue().getUf());
    }

    @FXML
    private void onChangeUFAction(ActionEvent e) {
        try {
            loadTable();
            clearForm();
        } catch (Exception cause) {
            AlertHelper.createError(getStage(), cause)
                    .showAndWait();
        }
    }

    @FXML
    private void onSaveAction(ActionEvent e) {
        Map<CharSequence, Object> map;
        Domain domain;
        try {
            if (aalterar == null) {
                domain = new Municipio();
            } else {
                domain = aalterar;
                aalterar = null;
            }

            map = MunicipioFacade.toMap(domain);
            map.put(Facade.Municipio.KEY_NOME, tfNOME.getText());
            map.put(Facade.Municipio.KEY_UF, cbUF.getValue());

            facade.validar(map);
            facade.salvar(map);
            clearForm();
            loadTable();
        } catch (MunicipioInvalidoException cause) {
            tfNOME.requestFocus();
            tfNOME.selectAll();
            AlertHelper.createWarning(getStage(), cause, "Falha na Validação!")
                    .showAndWait();
        } catch (Exception cause) {
            AlertHelper.createError(getStage(), cause)
                    .showAndWait();
        }
    }

    @FXML
    private void onUpdateAction(ActionEvent e) {
        TableViewSelectionModel<app.fx.model.Municipio> model;
        ObservableList<Integer> indices;
        int size;

        try {
            model = tvMUNICIPIOS.getSelectionModel();
            indices = model.getSelectedIndices();
            size = indices.size();

            switch (size) {
                default:
            case 0:
                AlertHelper.createWarning(getStage())
                        .setHeaderText("Alterando...")
                        .setContentText("Favor, selecione pelo menos 1 município para alterar!")
                        .showAndWait();
                break;
            case 1:
                app.fx.model.Municipio m = model.getSelectedItem();

                aalterar = m.getDomain().get();
                tfNOME.setText(m.getNome().get());
                tfNOME.requestFocus();
                tfNOME.selectAll();
                break;
            }
        } catch (Exception cause) {
            AlertHelper.createError(getStage(), cause)
                    .showAndWait();
        }
    }

    @FXML
    private void onDeleteAction(ActionEvent e) {
        TableViewSelectionModel<app.fx.model.Municipio> model;
        ObservableList<Integer> indices;
        int size;

        try {
            model = tvMUNICIPIOS.getSelectionModel();
            indices = model.getSelectedIndices();
            size = indices.size();

            switch (size) {
            case 0:
                AlertHelper.createWarning(getStage())
                        .setHeaderText("Apagando...")
                        .setContentText("Favor, selecione pelo menos 1 município para apagar!")
                        .showAndWait();
                break;
            default:
                String message;
                app.fx.model.Municipio m;

                if (size == 1) {
                    m = model.getSelectedItem();
                    message = String.format("%s / %s", m.getNome().get(), m.getUf().get());
                } else {
                    message = String.format("%s municípios", size);
                }

                if (AlertHelper.createConfirmation(getStage(), "Confirma apagar?")
                        .showAndWait(message)) {
                    for (app.fx.model.Municipio i : model.getSelectedItems()) {
                        facade.apagar(i.getDomain().get());
                    }

                    loadTable();
                } else {
                    model.clearSelection();
                }
                break;
            }
        } catch (Exception cause) {
            AlertHelper.createError(getStage(), cause)
                    .showAndWait();
        }
    }

    @FXML
    private void onCancelAction(ActionEvent e) {
        clearForm();
    }

    private void loadTable() throws Exception {
        ObservableList<app.fx.model.Municipio> items;
        Collection<? extends Domain> municipios;
        Object uf;

        items = tvMUNICIPIOS.getItems();
        uf = cbUF.getValue();
        municipios = facade.listar(uf.toString());

        items.clear();
        municipios.forEach(m -> items.add(new app.fx.model.Municipio(m)));
        setStatus("%s município(s)", municipios.size());
    }

    private void clearForm() {
        tfNOME.setText(null);
        tfNOME.requestFocus();
        tvMUNICIPIOS.getSelectionModel().clearSelection();
        aalterar = null;
    }
}
