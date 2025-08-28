package domain.exception;

public class MunicipioException extends Exception {

    private static final long serialVersionUID = -2194177049299589050L;

    /**
     * Cria a exceção com mensagem e causa.
     *
     * @param message mensagem descritiva
     * @param cause   exceção raiz
     */
    public MunicipioException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Cria a exceção somente com mensagem.
     *
     * @param message mensagem descritiva
     */
    public MunicipioException(String message) {
        super(message);
    }

    /**
     * Cria a exceção somente com causa.
     *
     * @param cause exceção raiz
     */
    public MunicipioException(Throwable cause) {
        super(cause);
    }
}
