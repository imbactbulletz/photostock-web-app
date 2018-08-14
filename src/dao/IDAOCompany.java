package dao;

import entities.Company;

public interface IDAOCompany {
    Company login(Company company);
    Company register(Company company);
}
