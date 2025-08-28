package domain.model;

import domain.Domain;

/**
 * Implementação base de uma entidade de domínio com identificador.
 *
 * @param <PK> tipo do identificador (chave primária)
 * @param <E>  tipo da exceção de validação
 */
public abstract class Entidade<PK, E extends Throwable> implements Domain<PK, E> {

    /**
     * Identificador único da entidade.
     */
    private PK id;

    /** {@inheritDoc} */
    @Override
    public PK getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void setId(PK id) {
        this.id = id;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isNullId() {
        return id == null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isNotNullId() {
        return !isNullId();
    }

    /**
     * Representação textual incluindo o identificador.
     *
     * @return string no formato {@code id=<valor>}
     */
    @Override
    public String toString() {
        return String.format("id=%s", id);
    }
}
