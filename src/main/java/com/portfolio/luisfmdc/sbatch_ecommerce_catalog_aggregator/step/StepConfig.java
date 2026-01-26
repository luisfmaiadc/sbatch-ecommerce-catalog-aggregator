package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.step;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StepConfig {

    private final ItemReader<Eletronico> electronicItemReader;
    private final ItemWriter<Eletronico> electronicItemWriter;
    private final ItemReader<Livro> bookItemReader;
    private final ItemWriter<Livro> bookItemWriter;
    private final ItemReader<Roupa> fashionItemReader;
    private final ItemWriter<Roupa> fashionItemWriter;

    @Bean
    public Step electronicCatalogStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("electronicCatalogStep", jobRepository)
                .<Eletronico, Eletronico>chunk(250, transactionManager)
                .reader(electronicItemReader)
                .writer(electronicItemWriter)
                .build();
    }

    @Bean
    public Step bookCatalogStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("bookCatalogStep", jobRepository)
                .<Livro, Livro>chunk(250, transactionManager)
                .reader(bookItemReader)
                .writer(bookItemWriter)
                .build();
    }

    @Bean
    public Step fashionCatalogStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("fashionCatalogStep", jobRepository)
                .<Roupa, Roupa>chunk(250, transactionManager)
                .reader(fashionItemReader)
                .writer(fashionItemWriter)
                .build();
    }
}