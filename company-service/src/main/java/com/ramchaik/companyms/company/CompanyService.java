package com.ramchaik.companyms.company;

import com.ramchaik.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    void createCompany(Company company);
    Company updateCompany(Long id, Company company);
    boolean deleteCompany(Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);
}
