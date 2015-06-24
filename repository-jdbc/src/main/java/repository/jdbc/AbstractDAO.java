package repository.jdbc;

import domain.model.Entidade;
import repository.DAO;

abstract class AbstractDAO<T extends Entidade<?, E>, E extends Throwable> implements DAO<T, E> {

    protected abstract E getExceptionInsert();
    protected abstract String getSQLInsert();
    protected abstract void prepareStatementInsert();

    @Override
    public void inserir(T domain) throws E {
        
    }
}
