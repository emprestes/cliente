package app.fx.controller;

import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.controlsfx.dialog.Dialogs;

import service.MunicipioService;
import service.factory.FactoryService;
import domain.exception.MunicipioInvalidoException;
import domain.model.Municipio;
import domain.model.UFVO;

@SuppressWarnings("deprecation")
public class MunicipioController extends AbstractController {

    public static final String STYLE_URL = "/app/fx/view/municipio.css";

    public static final String VIEW_URL = "/app/fx/view/municipio.fxml";
    public static final String VIEW_TITLE = "Cadastro de Municípios";

    private MunicipioService service;

    @FXML
    private ComboBox<UFVO> cbUF;

    @FXML
    private TextField tfNOME;

    @FXML
    private TableView<app.fx.model.Municipio> tvMUNICIPIOS;

    private ContextMenu cm;

    @FXML
    private TableColumn<app.fx.model.Municipio, String> tcNOME;

    @FXML
    private TableColumn<app.fx.model.Municipio, UFVO> tcUF;

    @FXML
    public void initialize() {
        initView();
        initActions();
        initServices();
    }

    private void initServices() {
        service = FactoryService.createMunicipioService();
    }

    private void initActions() {
        MenuItem mi;

        mi = cm.getItems().get(0);
        mi.setOnAction(e -> System.out.println("Menu 'atualizar' municipio funcionando!"));

        mi = cm.getItems().get(1);
        mi.setOnAction(e -> System.out.println("Menu 'apagar' municipio funcionando!"));

        mi = cm.getItems().get(3);  
        mi.setOnAction(e -> System.out.println("Menu 'selecionar todos' municipio funcionando!"));
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

        cbUF.getItems().addAll(UFVO.values());
        cbUF.setValue(UFVO.SELECIONE);

        tcNOME.setCellValueFactory(data -> data.getValue().getNome());
        tcUF.setCellValueFactory(data -> data.getValue().getUf());
    }

    @FXML
    private void onChangeUFAction(ActionEvent e) {
        try {
            loadTable();
            clearStatus();
        } catch (Exception cause) {
            Dialogs
            .create()
            .title(getStage().getTitle())
            .masthead(cause.getMessage())
            .message("OPS...")
            .showException(cause);
        }
    }

    @FXML
    private void onSaveAction(ActionEvent e) {
        Municipio domain;
        try {
            domain = new Municipio();

            domain.setNome(tfNOME.getText());
            domain.setUf(cbUF.getValue());

            service.validar(domain);
            service.salvar(domain);
            clearForm();
            loadTable();
            setStatus("Salvo!");
        } catch (MunicipioInvalidoException cause) {
            Dialogs
            .create()
            .title(getStage().getTitle())
            .masthead("Falha na validação...")
            .message(cause.getMessage())
            .showWarning();
        } catch (Exception cause) {
            Dialogs
                .create()
                .title(getStage().getTitle())
                .masthead(cause.getMessage())
                .message("OPS...")
                .showException(cause);
        }
    }


    @FXML
    private void onDeleteAction(ActionEvent e) {
        
    }

    @FXML
    private void onCancelAction(ActionEvent e) {
        clearForm();
    }

    private void loadTable() throws Exception {
        ObservableList<app.fx.model.Municipio> items;
        Collection<Municipio> municipios;
        UFVO uf;

        items = tvMUNICIPIOS.getItems();
        uf = cbUF.getValue();
        municipios = service.listar(uf);

        items.clear();
        municipios.forEach(m -> items.add(new app.fx.model.Municipio(m)));
    }

    private void clearForm() {
        tfNOME.setText(null);
        tfNOME.requestFocus();
    }
}
