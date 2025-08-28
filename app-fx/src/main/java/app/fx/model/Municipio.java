package app.fx.model;

import domain.model.UFVO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;

/**
 * Wrapper JavaFX (camada de apresentação) para {@link domain.model.Municipio}.
 */
public class Municipio {

    private ObservableObjectValue<domain.model.Municipio> domain;
    private ObservableStringValue nome;
    private ObservableObjectValue<UFVO> uf;

    /**
     * Cria o modelo observável a partir do domínio.
     *
     * @param domain instância do domínio
     */
    public Municipio(domain.model.Municipio domain) {
        super();

        this.domain = new SimpleObjectProperty<>(domain);
        this.nome = new SimpleStringProperty(String.valueOf(domain.getNome()));
        this.uf = new SimpleObjectProperty<>(domain.getUf());
    }

    /**
     * Entidade de domínio associada.
     *
     * @return valor observável do domínio
     */
    public ObservableObjectValue<domain.model.Municipio> getDomain() {
        return domain;
    }

    /**
     * Nome do município (observável).
     *
     * @return valor observável do nome
     */
    public ObservableStringValue getNome() {
        return nome;
    }

    /**
     * UF do município (observável).
     *
     * @return valor observável da UF
     */
    public ObservableObjectValue<UFVO> getUf() {
        return uf;
    }
}
