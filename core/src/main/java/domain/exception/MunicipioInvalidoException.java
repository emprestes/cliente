package domain.exception;

public class MunicipioInvalidoException extends MunicipioException {

    private static final long serialVersionUID = 6405222188828268110L;

    /**
     * Cria a exceção indicando inconsistência nos dados do município.
     *
     * @param message mensagem descritiva
     */
    public MunicipioInvalidoException(String message) {
        super(message);
    }
}
