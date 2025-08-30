package repository;

import domain.Domain;

/**
 * Contrato base para objetos de acesso a dados (DAO).
 *
 * @param <PK> tipo do identificador da entidade
 * @param <T>  tipo da entidade de domínio manipulada
 * @param <E>  tipo da exceção de persistência
 */
public interface DAO<PK, T extends Domain<PK, E>, E extends Throwable> {

    /**
     * Insere uma nova entidade no repositório.
     *
     * @param domain entidade a ser inserida
     * @throws E em caso de erro de persistência
     */
    void inserir(T domain) throws E;

    /**
     * Atualiza uma entidade previamente persistida.
     *
     * @param domain entidade com dados atualizados
     * @throws E em caso de erro de persistência
     */
    void atualizar(T domain) throws E;

    /**
     * Remove uma entidade do repositório.
     *
     * @param domain entidade a ser removida
     * @throws E em caso de erro de persistência
     */
    void apagar(T domain) throws E;
}
