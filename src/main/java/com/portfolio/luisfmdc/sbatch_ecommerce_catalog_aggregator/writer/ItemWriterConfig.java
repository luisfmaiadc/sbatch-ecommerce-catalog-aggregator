package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.writer;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ItemWriterConfig {

    private static final String INSERT_ELECTRONIC_SQL = "INSERT INTO TbEletronico (id, nome, preco, taxa, dataCriacao) VALUES (:id,:nome, :preco, :taxa, NOW())";
    private static final String INSERT_BOOK_SQL = "INSERT INTO TbLivro (id, titulo, isbn, preco, dataCriacao) VALUES (:id, :titulo, :isbn, :preco, NOW())";
    private static final String INSERT_FASHION_SQL = "INSERT INTO TbRoupa (id, descricao, tamanho, preco, dataCriacao) VALUES (:id, :descricao, :tamanho, :preco, NOW())";

    @Bean
    public JdbcBatchItemWriter<Eletronico> electronicItemWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Eletronico>()
                .dataSource(dataSource)
                .sql(INSERT_ELECTRONIC_SQL)
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Livro> bookItemWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Livro>()
                .dataSource(dataSource)
                .sql(INSERT_BOOK_SQL)
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Roupa> fashionItemWriter(@Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Roupa>()
                .dataSource(dataSource)
                .sql(INSERT_FASHION_SQL)
                .beanMapped()
                .build();
    }
}