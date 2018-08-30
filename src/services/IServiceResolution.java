package services;

import entities.Resolution;

import java.util.List;

public interface IServiceResolution {
    List<Resolution> getAllResolutions();
    Resolution getResolutionByName(String name);
}
