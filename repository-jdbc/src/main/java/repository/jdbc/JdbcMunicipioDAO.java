package repository.jdbc;

import domain.exception.MunicipioException;
import domain.model.Municipio;
import domain.model.UFVO;
import repository.MunicipioDAO;
import repository.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

public class JdbcMunicipioDAO extends JdbcDAO<Integer, Municipio, MunicipioException> implements MunicipioDAO {

    private static final String SCRIPT_DDL = "/repository/jdbc/municipio.ddl";

    public JdbcMunicipioDAO() {
        super();

        DataSource.createHsqlDBTable(SCRIPT_DDL);
    }

    @Override
    protected MunicipioException getFailInsert() {
        return new MunicipioException("Deveria inserir e não inseriu!");
    }

    @Override
    protected MunicipioException getExceptionInsert(Throwable cause) {
        return new MunicipioException("PROBLEMAS AO INSERIR MUNICÍPIO NO BANCO DE DADOS!", cause);
    }

    @Override
    protected String getSQLInsert() {
        return "INSERT INTO municipio (nm_municipio, id_uf) VALUES(?, ?)";
    }

    @Override
    protected void prepareStatementInsert(PreparedStatement query, Municipio domain) throws SQLException {
        query.setString(1, domain.getNome().toString());
        query.setString(2, domain.getUf().toString());
    }

    @Override
    protected String getSQLSelectId() {
        return "SELECT id_municipio FROM municipio WHERE nm_municipio = ? AND id_uf = ?";
    }

    @Override
    protected void prepareStatementSelectId(PreparedStatement query, Municipio domain) throws SQLException {
        query.setString(1, domain.getNome().toString());
        query.setString(2, domain.getUf().toString());
    }

    @Override
    protected void setId(ResultSet resultSet, Municipio domain) throws SQLException {
        domain.setId(resultSet.getInt("id_municipio"));
    }

    @Override
    protected MunicipioException getFailUpdate() {
        return new MunicipioException("Deveria atualizar e não atualizou!");
    }

    @Override
    protected MunicipioException getExceptionUpdate(Throwable cause) {
        return new MunicipioException("PROBLEMAS AO ATUALIZAR MUNICÍPIO NO BANCO DE DADOS!", cause);
    }

    @Override
    protected String getSQLUpdate() {
        return "UPDATE municipio SET nm_municipio = ?, id_uf = ? WHERE id_municipio = ?";
    }

    @Override
    protected void prepareStatementUpdate(PreparedStatement query, Municipio domain) throws SQLException {
        query.setString(1, domain.getNome().toString());
        query.setString(2, domain.getUf().toString());
        query.setInt(3, domain.getId());
    }

    @Override
    protected MunicipioException getFailDelete() {
        return new MunicipioException("Deveria apagar e não apagou!");
    }

    @Override
    protected MunicipioException getExceptionDelete(Throwable cause) {
        return new MunicipioException("PROBLEMAS AO APAGAR MUNICÍPIO NO BANCO DE DADOS!", cause);
    }

    @Override
    protected String getSQLDelete() {
        return "DELETE FROM municipio WHERE id_municipio = ?";
    }

    @Override
    protected void prepareStatementDelete(PreparedStatement query, Municipio domain) throws SQLException {
        query.setInt(1, domain.getId());
    }

    @Override
    public Set<Municipio> selecionar(UFVO uf) throws MunicipioException {
        final String sql = "SELECT id_municipio, nm_municipio FROM municipio WHERE id_uf = ?";

        try (Connection c = DataSource.openConnection();
             PreparedStatement ps = c.prepareStatement(sql);) {
            final Set<Municipio> municipios = new TreeSet<>();
            final ResultSet rs;
            Municipio m;

            ps.setString(1, uf.toString());

            rs = ps.executeQuery();

            while (rs.next()) {
                m = new Municipio();

                m.setId(rs.getInt("id_municipio"));
                m.setNome(rs.getString("nm_municipio"));
                m.setUf(uf);

                municipios.add(m);
            }

            return municipios;
        } catch (SQLException | DatabaseException cause) {
            throw new MunicipioException(
                    "PROBLEMAS AO SELECIONAR MUNICÍPIOS NA BANCO DE DADOS!",
                    cause);
        }
    }
}
