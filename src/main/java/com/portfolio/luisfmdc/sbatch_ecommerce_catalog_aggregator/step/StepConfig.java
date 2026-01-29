package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.step;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.processor.BookItemProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class StepConfig {

    @Bean
    public Step electronicCatalogStep(JobRepository jobRepository,
                                      PlatformTransactionManager transactionManager,
                                       ItemReader<Eletronico> electronicItemReader,
                                      ItemProcessor<Eletronico, Eletronico> electronicItemProcessor,
                                      ItemWriter<Eletronico> electronicItemWriter) {
        return new StepBuilder("electronicCatalogStep", jobRepository)
                .<Eletronico, Eletronico>chunk(250, transactionManager)
                .reader(electronicItemReader)
                .processor(electronicItemProcessor)
                .writer(electronicItemWriter)
                .build();
    }

    @Bean
    public Step bookCatalogStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                ItemReader<Livro> bookItemReader,
                                BookItemProcessor bookItemProcessor,
                                ItemWriter<Livro> bookCompositeWriter,
                                FlatFileItemWriter<Livro> bookFlatFileItemWriter) {
        return new StepBuilder("bookCatalogStep", jobRepository)
                .<Livro, Livro>chunk(250, transactionManager)
                .reader(bookItemReader)
                .processor(bookItemProcessor)
                .writer(bookCompositeWriter)
                .stream(bookFlatFileItemWriter)
                .listener(bookItemProcessor)
                .build();
    }

    @Bean
    public Step fashionCatalogStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<Roupa> fashionItemReader,
                                   ItemProcessor<Roupa, Roupa> fashionItemProcessor,
                                   ItemWriter<Roupa> fashionItemWriter) {
        return new StepBuilder("fashionCatalogStep", jobRepository)
                .<Roupa, Roupa>chunk(250, transactionManager)
                .reader(fashionItemReader)
                .processor(fashionItemProcessor)
                .writer(fashionItemWriter)
                .build();
    }
}