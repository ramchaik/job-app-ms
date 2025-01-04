package com.ramchaik.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    void createCompany(Company company);
    Company updateCompany(Long id, Company company);
    boolean deleteCompany(Long id);
}
