package repository.jdbc;

import repository.exception.DatabaseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utilitários para abertura e fechamento de conexões JDBC e execução de DDL.
 */
final class DataSource {

    /**
     * Abre uma conexão de banco de dados conforme configuração em {@code jdbc.properties}.
     *
     * @return conexão aberta
     * @throws DatabaseException quando ocorre erro de IO, driver ou conexão
     */
    public static Connection openConnection() throws DatabaseException {
        Properties p = new Properties();
        try (Reader r = new FileReader(Jdbc.FILE_NAME.toFile())) {
            p.load(r);

            Class.forName(p.getProperty(Jdbc.DRIVER));

            return DriverManager.getConnection(p.getProperty(Jdbc.URL), p.getProperty(Jdbc.USER),
                    p.getProperty(Jdbc.PASSWORD));
        } catch (SQLException cause) {
            throw new DatabaseException("PROBLEMAS AO ABRIR CONEXÃO COM BANCO DE DADOS!", cause);
        } catch (IOException cause) {
            throw new DatabaseException("PROBLEMAS AO ABRIR 'jdbc.properties'!", cause);
        } catch (ClassNotFoundException cause) {
            throw new DatabaseException("PROBLEMAS AO CARREGAR O DRIVER!", cause);
        }
    }

    private DataSource() {
        super();
    }

    /**
     * Executa um script DDL do classpath para criar estruturas no HSQLDB.
     *
     * @param scriptDDL caminho do recurso contendo comandos DDL
     */
    public static void createHsqlDBTable(String scriptDDL) {
        InputStream in = DataSource.class.getResourceAsStream(scriptDDL);

        createHsqlDBTable(in);
    }

    /**
     * Executa um script DDL através de um {@link InputStream}.
     *
     * @param scriptDDL fluxo com comandos DDL
     */
    public static void createHsqlDBTable(InputStream scriptDDL) {
        try (Connection c = DataSource.openConnection();
             Statement query = c.createStatement();
             BufferedReader ddl = new BufferedReader(new InputStreamReader(scriptDDL))) {

            while (ddl.ready()) {
                query.execute(ddl.readLine());
            }
        } catch (Exception cause) {
            cause.printStackTrace();
        }
    }

    /**
     * Fecha com segurança um {@link Statement} caso não seja nulo.
     *
     * @param statement instrução a ser fechada
     * @throws DatabaseException quando ocorre erro no fechamento
     */
    public static void close(Statement statement) throws DatabaseException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException cause) {
            throw new DatabaseException("PROBLEMAS AO FECHAR CONSULTA!", cause);
        }
    }

    /**
     * Fecha com segurança uma {@link Connection} caso não seja nula.
     *
     * @param connection conexão a ser fechada
     * @throws DatabaseException quando ocorre erro no fechamento
     */
    public static void close(Connection connection) throws DatabaseException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException cause) {
            throw new DatabaseException("PROBLEMAS AO FECHAR CONEXÃO COM BANCO DE DADOS!", cause);
        }
    }

    /**
     * Chaves e caminhos de configuração utilizados para abrir conexões.
     */
    interface Jdbc {
        Path FILE_NAME = Paths.get("./conf/jdbc.properties").toAbsolutePath();
        String DATABASE = "jdbc.database";
        String DRIVER = "jdbc.driver";
        String URL = "jdbc.url";
        String USER = "jdbc.user";
        String PASSWORD = "jdbc.password";
    }
}
