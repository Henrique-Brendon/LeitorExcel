package com.henrique.backend.jobs;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.henrique.backend.dtos.ProductDTO;
import com.henrique.backend.util.ExcelFileService;

@Configuration
public class BatchConfig {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }
    
    @Bean
    Job jobExcelProcessing(Step stepExcelFile) {
        return new JobBuilder("jobExcelProcessing", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(stepExcelFile)
            .build();
    }

    @Bean
    Step stepExcelFile(ItemReader<ProductDTO> reader, ItemProcessor<ProductDTO, Product> processor, ItemWriter<Product> writer) {
        return new StepBuilder("stepExcelFile", jobRepository)
            .<ProductDTO, Product>chunk(1000, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    ItemReader<ProductDTO> reader() {
        return new ExcelFileService("files//amd.xlsx");
    }

}