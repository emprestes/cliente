package repository.jdbc.hsqldb;

import org.hsqldb.Server;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Utilitários para controle de um servidor HSQLDB embutido durante o desenvolvimento/testes.
 */
public final class HsqlDBHelper {

    private static final Server HSQL_SERVER = new Server();

    private static final Path FILE_PROPERTIES = Paths.get("./conf/jdbc.properties").toAbsolutePath();
    private static final String KEY_DATABASE = "jdbc.database";

    private HsqlDBHelper() {
        super();
    }

    /**
     * Inicia o servidor HSQLDB utilizando a base informada quando a configuração ativa for HSQLDB.
     *
     * @param database nome da base de dados
     */
    public static void startServer(String database) {
        if (isHsqlDB()) {
            HSQL_SERVER.setLogWriter(null);
            HSQL_SERVER.setSilent(true);
            HSQL_SERVER.setDatabaseName(0, database);
            HSQL_SERVER.setDatabasePath(0, "file:target/".concat(database));

            HSQL_SERVER.start();
        }
    }

    /**
     * Verifica se a configuração ativa refere-se ao HSQLDB.
     *
     * @return {@code true} quando o banco configurado é HSQLDB
     */
    public static boolean isHsqlDB() {
        Properties p = new Properties();
        String database = null;

        try (Reader r = new FileReader(FILE_PROPERTIES.toFile())) {
            p.load(r);

            database = p.getProperty(KEY_DATABASE);
        } catch (Exception cause) {
            throw new RuntimeException("Problems loading file properties", cause);
        }

        return "hsqldb".equalsIgnoreCase(database);
    }

    /**
     * Interrompe o servidor HSQLDB quando ativo.
     */
    public static void stopServer() {
        if (isHsqlDB()) {
            HSQL_SERVER.stop();
            HSQL_SERVER.shutdown();
        }
    }
}
