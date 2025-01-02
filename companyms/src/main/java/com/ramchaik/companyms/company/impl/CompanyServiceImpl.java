package com.ramchaik.companyms.company.impl;

import com.ramchaik.companyms.company.Company;
import com.ramchaik.companyms.company.CompanyRepository;
import com.ramchaik.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company companyOnDb = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));

        companyOnDb.setName(company.getName());
        companyOnDb.setDescription(company.getDescription());

        companyRepository.save(companyOnDb);
        return companyOnDb;
    }

    @Override
    public boolean deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
