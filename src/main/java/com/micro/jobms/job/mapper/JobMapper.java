package com.micro.jobms.job.mapper;

import com.micro.jobms.job.Job;
import com.micro.jobms.job.dto.JobWithCompanyDTO;
import com.micro.jobms.job.external.Company;

public class JobMapper {
    public static JobWithCompanyDTO mapToJobWithCompanyDto(
            Job job,
            Company company) {

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
