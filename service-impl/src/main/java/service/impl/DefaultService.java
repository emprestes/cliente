package service.impl;

import domain.Domain;
import repository.DAO;
import service.Service;

/**
 * Implementação base para serviços que delegam persistência a um {@link DAO}.
 *
 * @param <T> tipo da entidade de domínio
 * @param <E> tipo da exceção de regra/persistência
 */
abstract class DefaultService<T extends Domain<?, E>, E extends Throwable> implements Service<T, E> {

    /**
     * DAO responsável pela persistência.
     */
    private DAO<T, E> dao;

    /**
     * Cria um serviço padrão com o DAO informado.
     *
     * @param dao implementação de acesso a dados
     */
    protected DefaultService(DAO<T, E> dao) {
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
