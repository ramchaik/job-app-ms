package com.ramchaik.jobsms.job.mapper;

import com.ramchaik.jobsms.job.Job;
import com.ramchaik.jobsms.job.dto.JobDTO;
import com.ramchaik.jobsms.job.external.Company;
import com.ramchaik.jobsms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(
            Job job,
           Company company,
            List<Review> reviews
    ) {
        JobDTO jobDTO = new JobDTO();

        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
