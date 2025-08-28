package service;

import domain.Domain;

/**
 * Contrato base para serviços de aplicação que manipulam entidades de domínio.
 *
 * @param <T> tipo da entidade de domínio
 * @param <E> tipo da exceção de regra/validação
 */
public interface Service<T extends Domain<?, ? extends Throwable>, E extends Throwable> {

    /**
     * Valida a entidade segundo as regras de negócio.
     *
     * @param domain entidade a validar
     * @throws E quando inválida
     */
    void validar(T domain) throws E;

    /**
     * Persiste a entidade (insere ou atualiza conforme o identificador).
     *
     * @param domain entidade a salvar
     * @throws E em caso de erro/regra
     */
    void salvar(T domain) throws E;

    /**
     * Remove a entidade do repositório.
     *
     * @param domain entidade a apagar
     * @throws E em caso de erro/regra
     */
    void apagar(T domain) throws E;
}
