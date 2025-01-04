package com.ramchaik.jobsms.job.dto;

import com.ramchaik.jobsms.job.external.Company;
import com.ramchaik.jobsms.job.external.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
