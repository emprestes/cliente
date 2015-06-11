package app.fx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class MunicipioController extends AbstractController {

    public static final String STYLE_URL = "/app/fx/view/municipio.css";

    public static final String VIEW_URL = "/app/fx/view/municipio.fxml";
    public static final String VIEW_TITLE = "Cadastro de Municípios";

    @FXML
    private TableView<?> tvMUNICIPIOS;

    private ContextMenu cm;

    @FXML
    public void initialize() {
        initView();
        initActions();
    }

    private void initActions() {
        
    }

    private void initView() {
        MenuItem mi;

        cm = new ContextMenu();
        mi = new MenuItem("Atualizar");
        cm.getItems().add(0, mi);

        tvMUNICIPIOS.setContextMenu(cm);
    }
}