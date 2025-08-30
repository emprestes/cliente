package repository;

import domain.exception.MunicipioException;
import domain.model.Municipio;
import domain.model.UFVO;

import java.util.Set;

/**
 * DAO específico para operações com {@link Municipio}.
 */
public interface MunicipioDAO extends DAO<Integer, Municipio, MunicipioException> {

    /**
     * Seleciona os municípios pertencentes à UF informada.
     *
     * @param uf UF utilizada como filtro
     * @return conjunto de municípios da UF
     * @throws MunicipioException em caso de erro de leitura
     */
    Set<Municipio> selecionar(UFVO uf) throws MunicipioException;
}
