package service.impl;

import domain.exception.MunicipioException;
import domain.model.Municipio;
import domain.model.UFVO;
import repository.MunicipioDAO;
import repository.factory.FactoryDAO;
import service.MunicipioService;

import java.util.Collection;

/**
 * Implementação padrão do serviço de municípios.
 */
public class DefaultMunicipioService extends DefaultService<Municipio, MunicipioException> implements MunicipioService {

    /**
     * DAO de municípios utilizado pelo serviço.
     */
    private MunicipioDAO dao;

    /**
     * Cria o serviço utilizando a fábrica padrão de DAOs.
     */
    public DefaultMunicipioService() {
        this(FactoryDAO.createMunicipioDAO());
    }

    /**
     * Cria o serviço com a implementação de DAO informada.
     *
     * @param dao implementação de acesso a dados de municípios
     */
    protected DefaultMunicipioService(MunicipioDAO dao) {
        super(dao);

        this.dao = dao;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Municipio> listar(UFVO uf) throws MunicipioException {
        return dao.selecionar(uf);
    }
}
