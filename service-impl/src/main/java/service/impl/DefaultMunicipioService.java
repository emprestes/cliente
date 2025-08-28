package service.impl;

import java.util.Collection;

import repository.MunicipioDAO;
import repository.factory.FactoryDAO;
import service.MunicipioService;
import domain.exception.MunicipioException;
import domain.model.Municipio;
import domain.model.UFVO;

public class DefaultMunicipioService extends DefaultService<Municipio, MunicipioException> implements MunicipioService {

    private final MunicipioDAO dao;

    public DefaultMunicipioService() {
        this(FactoryDAO.createMunicipioDAO());
    }

    protected DefaultMunicipioService(MunicipioDAO dao) {
        super(dao);

        this.dao = dao;
    }

    @Override
    public Collection<Municipio> listar(UFVO uf) throws MunicipioException {
        return dao.selecionar(uf);
    }
}
