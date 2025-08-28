package repository.io;

/**
 * Classe utilitária base para DAOs baseados em arquivos texto.
 */
abstract class FileDAO {

    /**
     * Separador padrão de campos em CSV.
     */
    public static final char CSV_SEPARATOR = ';';

    /** Expressão para fracionar uma linha CSV pelo separador. */
    public static final String CSV_SPLIT_REGEX = String.valueOf(CSV_SEPARATOR);

    /** Posição inicial de um arquivo. */
    public static final long FILE_BEGIN = 0L;
    /** Tamanho zero (arquivo vazio). */
    public static final long FILE_EMPTY = 0L;

    /** Prefixo de índice para controle incremental de IDs. */
    public static final String INDEX_ID = "#";

    /** Quebra de linha utilizada para persistência. */
    public static final char NEW_LINE = '\n';

    /**
     * Agrupa campos e suas posições para cada entidade manipulada em arquivo.
     */
    interface Fields {
        /** Campos da entidade Município no arquivo. */
        enum Municipio {
            ID, NOME, UF;
        }
        // TODO Bairro
        // TODO Logradouro
        // TODO Endereco
        // TODO Cliente
        // TODO Contato
    }
}
