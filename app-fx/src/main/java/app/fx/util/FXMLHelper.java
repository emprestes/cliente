package app.fx.util;

import app.fx.api.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Helper para construção de {@link Scene} e carregamento de FXML.
 */
public final class FXMLHelper {

    private FXMLHelper() {
        super();
    }

    /**
     * Cria uma cena carregando um FXML por caminho relativo.
     *
     * @param stage     janela alvo
     * @param viewTitle título da visão
     * @param view      caminho do recurso FXML
     * @param styles    estilos (CSS) opcionais
     * @return cena criada
     * @throws Exception quando ocorre falha no carregamento
     */
    public static Scene createScene(final Stage stage,
                                    final String viewTitle,
                                    final String view,
                                    final String... styles) throws Exception {
        URL v = getResource(view);
        URL[] s = getResources(styles);

        return createScene(stage, viewTitle, v, s);
    }

    /**
     * Cria uma cena a partir de um recurso FXML já resolvido.
     *
     * @param stage     janela alvo
     * @param viewTitle título da visão
     * @param view      recurso FXML
     * @param styles    estilos (CSS) opcionais
     * @return cena criada
     * @throws Exception quando ocorre falha no carregamento
     */
    public static Scene createScene(final Stage stage,
                                    final String viewTitle,
                                    final URL view,
                                    final URL... styles) throws Exception {
        Parent root = createView(stage, viewTitle, view, styles);

        return new Scene(root);
    }

    /**
     * Cria a raiz de uma visão carregando um FXML por caminho relativo.
     *
     * @param <V>       tipo da raiz (Parent)
     * @param stage     janela alvo
     * @param viewTitle título da visão
     * @param view      caminho do FXML
     * @param styles    estilos (CSS) opcionais
     * @return raiz carregada
     * @throws Exception quando ocorre falha no carregamento
     */
    public static <V extends Parent> V createView(final Stage stage,
                                                  final String viewTitle,
                                                  final String view,
                                                  final String... styles)
            throws Exception {
        URL v = getResource(view);
        URL[] s = getResources(styles);

        return createView(stage, viewTitle, v, s);
    }

    /**
     * Cria a raiz de uma visão a partir de um recurso FXML resolvido.
     *
     * @param <V>       tipo da raiz (Parent)
     * @param stage     janela alvo
     * @param viewTitle título da visão
     * @param view      recurso FXML
     * @param styles    estilos (CSS) opcionais
     * @return raiz carregada
     * @throws Exception quando ocorre falha no carregamento
     */
    public static <V extends Parent> V createView(final Stage stage,
                                                  final String viewTitle,
                                                  final URL view,
                                                  final URL... styles)
            throws Exception {
        FXMLLoader fxml;
        Controller controller;
        V root;

        fxml = new FXMLLoader(view);
        root = fxml.load();
        controller = fxml.getController();

        for (URL style : styles) {
            if (style != null) {
                root.getStylesheets().add(style.toExternalForm());
            }
        }

        stage.setTitle(viewTitle);

        controller.setStage(stage);

        return root;
    }

    /**
     * Resolve um recurso relativo ao helper.
     *
     * @param r caminho do recurso
     * @return URL do recurso ou {@code null}
     */
    private static URL getResource(final String r) {
        return FXMLHelper.class.getResource(r);
    }

    /**
     * Resolve múltiplos recursos relativos ao helper.
     *
     * @param r caminhos de recursos
     * @return vetor de URLs (posições sem recurso permanecem nulas)
     */
    private static URL[] getResources(final String... r) {
        URL[] rs = new URL[r != null ? r.length : 0];

        for (int i = 0; i < rs.length; i++) {
            rs[i] = FXMLHelper.class.getResource(r[i]);
        }

        return rs;
    }
}
