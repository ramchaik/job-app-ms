package com.ramchaik.jobsms.job.impl;

import com.ramchaik.jobsms.job.Job;
import com.ramchaik.jobsms.job.JobRepository;
import com.ramchaik.jobsms.job.JobService;
import com.ramchaik.jobsms.job.clients.CompanyClient;
import com.ramchaik.jobsms.job.clients.ReviewClient;
import com.ramchaik.jobsms.job.dto.JobDTO;
import com.ramchaik.jobsms.job.external.Company;
import com.ramchaik.jobsms.job.external.Review;
import com.ramchaik.jobsms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return  jobs.stream().map(this::convertToDto).toList();
    }

    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        return JobMapper.mapToJobWithCompanyDto(
                job,
                company,
                reviews
        );
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) return null;
        return convertToDto(job);
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (jobRepository.existsById(id)) {
                Job job = jobRepository.findById(id).orElse(null);
                assert job != null;

                jobRepository.deleteById(id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Job updateJobById(Long id, Job job) {
        Job jobFromDb = jobRepository.findById(id).orElse(null);
        if (jobFromDb == null) return null;

        jobFromDb.setTitle(job.getTitle());
        jobFromDb.setDescription(job.getDescription());
        jobFromDb.setMinSalary(job.getMinSalary());
        jobFromDb.setMaxSalary(job.getMaxSalary());
        jobFromDb.setLocation(job.getLocation());

        jobRepository.save(jobFromDb);

        return jobFromDb;
    }
}
