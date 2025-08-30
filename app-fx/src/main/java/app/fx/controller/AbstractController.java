package app.fx.controller;

import app.fx.api.Controller;
import app.fx.util.FXMLHelper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * Base para controllers JavaFX com utilitários de status e navegação.
 */
@SuppressWarnings("deprecation")
abstract class AbstractController implements Controller {

    /**
     * Rótulo de status exibido na barra inferior (FXML).
     */
    @FXML
    private Label statusLabel;

    /** Stage associado a este controller. */
    private Stage stage;

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(final Stage pStage) {
        this.stage = pStage;
    }

    protected Scene getScene() {
        return stage.getScene();
    }

    @SuppressWarnings("unchecked")
    protected <V extends Parent> V getRoot() {
        return (V) getScene().getRoot();
    }

    /**
     * Atualiza o status formatando a mensagem com parâmetros.
     *
     * @param format padrão da mensagem
     * @param values parâmetros a aplicar no formato
     */
    protected void setStatus(final String format, final Object... values) {
        setStatus(String.format(format, values));
    }

    /**
     * Atualiza o status diretamente.
     *
     * @param message mensagem a exibir
     */
    protected void setStatus(final String message) {
        if (statusLabel == null) {
            statusLabel = (Label) stage.getScene().lookup("#statusLabel");
        }

        statusLabel.setText(message);
    }

    protected void clearStatus() {
        setStatus(null);
    }

    protected void done() {
        setStatus("Feito!");
    }

    /**
     * Carrega uma nova view (FXML) ao centro do layout principal.
     *
     * @param viewTitle título da visão
     * @param view      caminho do FXML
     * @param styles    estilos (CSS) opcionais
     * @throws Exception quando ocorre falha no carregamento
     */
    protected void loadView(final String viewTitle, final String view,
                            final String... styles) throws Exception {
        BorderPane root = getRoot();
        Stage s = getStage();
        Parent v = FXMLHelper.createView(s, viewTitle, view, styles);

        root.setCenter(v);
        done();
    }

    protected void close() {
        stage.close();
    }

    /**
     * Pede confirmação do usuário antes de fechar a janela.
     *
     * @param event evento de fechamento
     */
    protected void confirmAndClose(final Event event) {
        Action a = Dialogs
                .create()
                .title(stage.getTitle())
                .masthead("Deseja sair?")
                .message("Caso sim, bye bye!")
                .showConfirm();

        if (Dialog.ACTION_YES.equals(a)) {
            close();
        } else {
            event.consume();
        }
    }
}
