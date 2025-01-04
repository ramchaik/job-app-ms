package com.ramchaik.companyms.company.impl;

import com.ramchaik.companyms.company.Company;
import com.ramchaik.companyms.company.CompanyRepository;
import com.ramchaik.companyms.company.CompanyService;
import com.ramchaik.companyms.company.client.ReviewClient;
import com.ramchaik.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
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

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        long companyId = reviewMessage.getCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Resource not found with companyId: " + companyId));
        Double averageRating = reviewClient.getAverageRating(companyId);
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
