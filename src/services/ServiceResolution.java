package services;

import dao.DAOResolution;
import entities.Resolution;

import java.util.List;

public class ServiceResolution implements IServiceResolution {

    protected DAOResolution dao;

    public ServiceResolution(){
        this.dao = new DAOResolution();
    }

    @Override
    public List<Resolution> getAllResolutions() {
        return this.dao.getAllResolutions();
    }
}
