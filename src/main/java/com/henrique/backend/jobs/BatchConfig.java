package com.henrique.backend.jobs;

import java.util.ArrayList;
import java.util.List;

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

import com.henrique.backend.dtos.ExcelDTO;
import com.henrique.backend.entities.ListCode;
import com.henrique.backend.entities.Product;
import com.henrique.backend.entities.Sector;
import com.henrique.backend.repositories.ListCodeRepository;
import com.henrique.backend.repositories.ProductRepository;
import com.henrique.backend.repositories.SectorRepository;
import com.henrique.backend.util.ExcelFileUtil;


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
    Step stepExcelFile(ItemReader<ExcelDTO> reader, ItemProcessor<ExcelDTO, Product> processor, ItemWriter<Product> writer) {
        return new StepBuilder("stepExcelFile", jobRepository)
            .<ExcelDTO, Product>chunk(1000, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    ItemReader<ExcelDTO> reader() {
        return new ExcelFileUtil("files//amd.xlsx");
    }

    @Bean
    ItemProcessor<ExcelDTO, Product> processor() {
        return item -> {
            var product = new Product(null, item.name(), item.characteristics(), null, null, null,
                null, new Sector().mapSetor(item.name()), new ListCode(item.listCode()))
                .convertCurrency(item.cost(), item.price())
                .convertDate(item.dateEntry(), item.dateExit());
            return product;
        };
    }


    @Bean
    ItemWriter<Product> writer(SectorRepository sectorRepository, ProductRepository productRepository, ListCodeRepository listCodeRepository) {
        return items -> {
            List<Sector> listSectors = new ArrayList<>();
            List<ListCode> listCodes = new ArrayList<>();
    
            items.forEach(product -> {
                Sector sector = new Sector().mapSetor(product.getName());

                product.setSector(sector);

                sector.getProducts().add(product);
            
                if (!listSectors.contains(sector)) {
                    listSectors.add(sector);
                }

                if (product.getListCode() != null && !listCodes.contains(product.getListCode())) {
                    listCodes.add(product.getListCode());
                }
            });

            sectorRepository.saveAll(listSectors);
            listCodeRepository.saveAll(listCodes);
            productRepository.saveAll(items);
        };
    }
}