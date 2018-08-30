package dao;

import entities.Resolution;

import java.util.List;

public interface IDAOResolution {

    List<Resolution> getAllResolutions();
    Resolution getResolutionByName(String name);
}
