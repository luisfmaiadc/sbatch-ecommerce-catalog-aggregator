package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    @Bean
    public Job catalogAggregatorJob(JobRepository jobRepository, Step electronicCatalogStep, Step bookCatalogStep, Step fashionCatalogStep) {
        return new JobBuilder("catalogAggregatorJob", jobRepository)
                .start(applicationFlow(electronicCatalogStep, bookCatalogStep, fashionCatalogStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    public Flow applicationFlow(Step electronicCatalogStep, Step bookCatalogStep, Step fashionCatalogStep) {
        return new FlowBuilder<Flow>("applicationFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(electronicsFlow(electronicCatalogStep),
                        bookFlow(bookCatalogStep),
                        fashionFlow(fashionCatalogStep))
                .build();
    }

    public Flow electronicsFlow(Step electronicCatalogStep) {
        return new FlowBuilder<Flow>("electronicsFlow")
                .start(electronicCatalogStep)
                .build();
    }

    public Flow bookFlow(Step bookCatalogStep) {
        return new FlowBuilder<Flow>("bookFlow")
                .start(bookCatalogStep)
                .build();
    }

    public Flow fashionFlow(Step fashionCatalogStep) {
        return new FlowBuilder<Flow>("fashionFlow")
                .start(fashionCatalogStep)
                .build();
    }
}