package domain;

/**
 * Contrato básico para entidades de domínio do sistema.
 *
 * @param <PK> tipo da chave primária/identificador
 * @param <E>  tipo da exceção de validação lançada pelas operações do domínio
 */
public interface Domain<PK, E extends Throwable> {

    /**
     * Obtém o identificador único da entidade.
     *
     * @return identificador atual (pode ser {@code null})
     */
    PK getId();

    /**
     * Define o identificador único da entidade.
     *
     * @param id novo identificador
     */
    void setId(PK id);

    /**
     * Indica se o identificador está ausente ({@code null}).
     *
     * @return {@code true} quando o identificador é {@code null}
     */
    boolean isNullId();

    /**
     * Indica se o identificador foi atribuído (não nulo).
     *
     * @return {@code true} quando o identificador não é {@code null}
     */
    boolean isNotNullId();

    /**
     * Realiza a validação de consistência dos dados da entidade.
     *
     * @throws E caso sejam encontrados dados inválidos
     */
    void validar() throws E;

    /**
     * Converte os dados principais da entidade para o formato CSV.
     *
     * @param separator separador de campos a ser utilizado
     * @return linha CSV correspondente à entidade
     */
    String toCSV(char separator);
}
