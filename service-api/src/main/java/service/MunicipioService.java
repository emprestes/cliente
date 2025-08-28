package service;

import domain.exception.MunicipioException;
import domain.model.Municipio;
import domain.model.UFVO;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Serviço de aplicação para operações com {@link Municipio}.
 */
public interface MunicipioService extends Service<Municipio, MunicipioException> {

    /**
     * Lista os municípios pertencentes à UF informada.
     *
     * @param uf UF utilizada como filtro
     * @return coleção de municípios
     * @throws MunicipioException em caso de erro de acesso
     */
    Collection<Municipio> listar(UFVO uf) throws MunicipioException;

    /**
     * Lista os municípios da UF em ordem inversa à ordenação natural.
     *
     * @param uf UF utilizada como filtro
     * @return coleção de municípios ordenada de forma decrescente
     * @throws MunicipioException em caso de erro de acesso
     */
    default Collection<Municipio> listarInvertido(UFVO uf) throws MunicipioException {
        Collection<Municipio> municipios = listar(uf);

        return municipios.stream()
                .sorted((m1, m2) -> -m1.compareTo(m2))
                .collect(Collectors.toSet());
    }
}
