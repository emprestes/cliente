package repository.io;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Fornece utilitários de acesso a arquivos utilizados pelos DAOs em filesystem.
 */
final class DataSource {

    /**
     * Abre um arquivo em modo leitura/escrita baseado nas propriedades de origem.
     *
     * @param fileName nome do arquivo a abrir
     * @return instância de {@link RandomAccessFile}
     * @throws IOException quando não é possível abrir o arquivo
     */
    public static RandomAccessFile openWriteableFile(String fileName) throws IOException {
        Properties p = new Properties();
        Reader r = new FileReader(Property.FILE_NAME);
        URI uri;
        Path path;

        p.load(r);

        uri = URI.create(String.format("file://%s/%s", p.getProperty(Property.SOURCE), fileName));
        path = Paths.get(uri);
        path = path.toAbsolutePath();

        return new RandomAccessFile(path.toFile(), Property.Permission.RW);
    }

    private DataSource() {
        super();
    }

    /**
     * Fecha com segurança um arquivo aleatório quando não for nulo.
     *
     * @param file arquivo aberto
     * @throws IOException caso ocorra erro no fechamento
     */
    public static void close(RandomAccessFile file) throws IOException {
        if (file != null)
            file.close();
    }

    /**
     * Chaves de configuração e permissões de acesso.
     */
    interface Property {
        String FILE_NAME = "conf/io.properties";
        String SOURCE = "io.source";

        interface Permission {
            String RW = "rw";
        }
    }
}
