package com.ramchaik.jobsms.job;

import com.ramchaik.jobsms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getById(Long id);

    Boolean deleteById(Long id);

    Job updateJobById(Long id, Job job);
}
