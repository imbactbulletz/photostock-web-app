package services;

import entities.Company;

public interface IServiceCompany {
    Company login(Company company);
    Company register(Company company);
}
