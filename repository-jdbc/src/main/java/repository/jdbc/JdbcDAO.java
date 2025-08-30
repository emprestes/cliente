package repository.jdbc;

import domain.model.Entidade;
import repository.DAO;
import repository.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementação base para DAOs JDBC, com operações comuns de inserção,
 * atualização e exclusão e pontos de extensão para consultas específicas.
 *
 * @param <PK> tipo do identificador
 * @param <T>  tipo da entidade de domínio
 * @param <E>  tipo da exceção de persistência
 */
abstract class JdbcDAO<PK, T extends Entidade<PK, E>, E extends Throwable> implements DAO<PK, T, E> {

    protected abstract E getFailInsert();
    protected abstract E getExceptionInsert(Throwable cause);
    protected abstract String getSQLInsert();

    protected abstract void prepareStatementInsert(PreparedStatement query, T domain) throws SQLException;

    protected JdbcDAO() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inserir(T domain) throws E {
        Connection c;
        PreparedStatement ps;

        try {
            c = DataSource.openConnection();
            ps = c.prepareStatement(getSQLInsert());

            try {
                prepareStatementInsert(ps, domain);

                int rows = ps.executeUpdate();

                if (rows == 0) {
                    throw getFailInsert();
                }

                setId(domain, c);
            } finally {
                DataSource.close(ps);
                DataSource.close(c);
            }
        } catch (SQLException | DatabaseException cause) {
            throw getExceptionInsert(cause);
        }
    }

    protected abstract String getSQLSelectId();

    protected abstract void prepareStatementSelectId(PreparedStatement query, T domain) throws SQLException;

    protected abstract void setId(ResultSet resultSet, T domain) throws SQLException;

    private void setId(T domain, Connection c) throws SQLException {
        PreparedStatement query = c.prepareStatement(getSQLSelectId());

        prepareStatementSelectId(query, domain);

        ResultSet rs = query.executeQuery();

        if (rs.next()) {
            setId(rs, domain);
        }
    }

    protected abstract E getFailUpdate();
    protected abstract E getExceptionUpdate(Throwable cause);
    protected abstract String getSQLUpdate();

    protected abstract void prepareStatementUpdate(PreparedStatement query, T domain) throws SQLException;

    /** {@inheritDoc} */
    @Override
    public void atualizar(T domain) throws E {
        try (Connection c = DataSource.openConnection();
             PreparedStatement ps = c.prepareStatement(getSQLUpdate());) {

            prepareStatementUpdate(ps, domain);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw getFailUpdate();
            }
        } catch (SQLException | DatabaseException cause) {
            throw getExceptionUpdate(cause);
        }
    }

    protected abstract E getFailDelete();
    protected abstract E getExceptionDelete(Throwable cause);
    protected abstract String getSQLDelete();

    protected abstract void prepareStatementDelete(PreparedStatement query, T domain) throws SQLException;

    /** {@inheritDoc} */
    @Override
    public void apagar(T domain) throws E {
        try (Connection c = DataSource.openConnection();
             PreparedStatement ps = c.prepareStatement(getSQLDelete());) {

            prepareStatementDelete(ps, domain);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw getFailDelete();
            }
        } catch (SQLException | DatabaseException cause) {
            throw getExceptionDelete(cause);
        }
    }
}
