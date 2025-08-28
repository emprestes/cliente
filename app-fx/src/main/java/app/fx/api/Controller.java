package app.fx.api;

import javafx.stage.Stage;

/**
 * Contrato comum para controllers JavaFX com referência ao {@link Stage} principal.
 */
public interface Controller {

    /**
     * Obtém o stage associado ao controller.
     *
     * @return stage atual
     */
    Stage getStage();

    /**
     * Define o stage associado ao controller.
     *
     * @param stage stage principal
     */
    void setStage(Stage stage);
}
