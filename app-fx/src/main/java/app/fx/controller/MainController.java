package app.fx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controller principal do aplicativo JavaFX.
 */
public final class MainController extends AbstractController {

    /**
     * Caminho do CSS principal.
     */
    public static final String STYLE_URL = "/app/fx/view/main.css";

    /** Caminho do FXML da tela principal. */
    public static final String VIEW_URL = "/app/fx/view/main.fxml";
    /** Título da janela principal. */
    public static final String VIEW_TITLE = "Cadastro de Clientes";

    @FXML
    /**
     * Ação do menu Cliente > Listar.
     *
     * @param event evento do menu
     */
    public void onClickClienteListarMenu(final ActionEvent event) {
        System.out.println("Menu cliente > listar funcionando!");
    }

    @FXML
    /**
     * Ação do menu Cliente > Novo.
     *
     * @param event evento do menu
     */
    public void onClickClienteNovoMenu(final ActionEvent event) {
        System.out.println("Menu cliente > novo funcionando!");
    }

    @FXML
    /**
     * Ação do menu Logradouro.
     *
     * @param event evento do menu
     */
    public void onClickLogradouroMenu(final ActionEvent event) {
        System.out.println("Menu logradouro funcionando!");
    }

    @FXML
    /**
     * Ação do menu Bairro.
     *
     * @param event evento do menu
     */
    public void onClickBairroMenu(final ActionEvent event) {
        System.out.println("Menu bairro funcionando!");
    }

    @FXML
    /**
     * Ação do menu Município: carrega a tela de municípios.
     *
     * @param event evento do menu
     */
    public void onClickMunicipioMenu(final ActionEvent event) {
        try {
            loadView(MunicipioController.VIEW_TITLE, MunicipioController.VIEW_URL, MunicipioController.STYLE_URL);
        } catch (Exception cause) {
            cause.printStackTrace();
        }
    }

    @FXML
    /**
     * Ação do menu Fechar: solicita confirmação e encerra.
     *
     * @param event evento do menu
     */
    public void onClickFecharMenu(final ActionEvent event) {
        confirmAndClose(event);
    }

    @Override
    public void setStage(final Stage stage) {
        stage.setOnCloseRequest(this::confirmAndClose);
        super.setStage(stage);
    }
}
