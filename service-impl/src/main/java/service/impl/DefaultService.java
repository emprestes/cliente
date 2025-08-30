package service.impl;

import domain.Domain;
import repository.DAO;
import service.Service;

/**
 * Implementação base para serviços que delegam persistência a um {@link DAO}.
 *
 * @param <PK> tipo do identificador da entidade
 * @param <T>  tipo da entidade de domínio
 * @param <E>  tipo da exceção de regra/persistência
 */
abstract class DefaultService<PK, T extends Domain<PK, E>, E extends Throwable>
        implements Service<PK, T, E> {

    /**
     * DAO responsável pela persistência.
     */
    private DAO<PK, T, E> dao;

    /**
     * Cria um serviço padrão com o DAO informado.
     *
     * @param dao implementação de acesso a dados
     */
    protected DefaultService(DAO<PK, T, E> dao) {
        super();

        this.dao = dao;
    }

    /** {@inheritDoc} */
    @Override
    public void validar(T domain) throws E {
        domain.validar();
    }

    /** {@inheritDoc} */
    @Override
    public void salvar(T domain) throws E {
        if (domain.isNullId()) {
            dao.inserir(domain);
        } else {
            dao.atualizar(domain);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void apagar(T domain) throws E {
        dao.apagar(domain);
    }
}
