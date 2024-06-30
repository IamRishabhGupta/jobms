package com.micro.jobms.job.impl;

import com.micro.jobms.job.Job;
import com.micro.jobms.job.JobRepository;
import com.micro.jobms.job.JobService;
import com.micro.jobms.job.dto.JobWithCompanyDTO;
import com.micro.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();





//        return jobWithCompanyDTOs;
        return jobs.stream().map(this::convertToDto).collect(Collectors.toUnmodifiableList());
    }

    private JobWithCompanyDTO convertToDto(Job job)
    {
        RestTemplate restTemplate = new RestTemplate();
//        for (Job job : jobs) {
            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);

            Company company = restTemplate.getForObject(
                    "http://localhost:8081/companies/" + job.getCompanyId(),
                    Company.class);
            jobWithCompanyDTO.setCompany(company);

//            jobWithCompanyDTOs.add(jobWithCompanyDTO);
//        }
        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}