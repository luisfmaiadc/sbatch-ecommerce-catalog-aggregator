package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.reader;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ItemReaderConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Eletronico> electronicItemReader(
            @Value("#{jobParameters['electronicFile']}") Resource resource) {
        return new FlatFileItemReaderBuilder<Eletronico>()
                .name("electronicItemReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names("id", "nome", "preco", "taxa")
                .targetType(Eletronico.class)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Livro> bookItemReader(
            @Value("#{jobParameters['bookFile']}") Resource resource) {
        return new FlatFileItemReaderBuilder<Livro>()
                .name("bookItemReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names("id", "titulo", "isbn", "preco")
                .targetType(Livro.class)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Roupa> fashionItemReader(
            @Value("#{jobParameters['fashionFile']}") Resource resource) {
        return new FlatFileItemReaderBuilder<Roupa>()
                .name("fashionItemReader")
                .resource(resource)
                .linesToSkip(1)
                .delimited()
                .names("id", "descricao", "tamanho", "preco")
                .targetType(Roupa.class)
                .build();
    }
}