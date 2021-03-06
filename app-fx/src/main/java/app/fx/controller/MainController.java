package app.fx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController extends AbstractController {

    public static final String STYLE_URL = "/app/fx/view/main.css";

    public static final String VIEW_URL = "/app/fx/view/main.fxml";
    public static final String VIEW_TITLE = "Cadastro de Clientes";

    @FXML
    public void onClickClienteListarMenu(ActionEvent event) {
        System.out.println("Menu cliente > listar funcionando!");
    }

    @FXML
    public void onClickClienteNovoMenu(ActionEvent event) {
        System.out.println("Menu cliente > novo funcionando!");
    }

    @FXML
    public void onClickLogradouroMenu(ActionEvent event) {
        System.out.println("Menu logradouro funcionando!");
    }

    @FXML
    public void onClickBairroMenu(ActionEvent event) {
        System.out.println("Menu bairro funcionando!");
    }

    @FXML
    public void onClickMunicipioMenu(ActionEvent event) {
        try {
            loadView(MunicipioController.VIEW_TITLE, MunicipioController.VIEW_URL, MunicipioController.STYLE_URL);
        } catch (Exception cause) {
            cause.printStackTrace();
        }
    }

    @FXML
    public void onClickFecharMenu(ActionEvent event) {
        confirmAndClose(event);
    }

    @Override
    public void setStage(Stage stage) {
        stage.setOnCloseRequest(this::confirmAndClose);
        super.setStage(stage);
    }
}
