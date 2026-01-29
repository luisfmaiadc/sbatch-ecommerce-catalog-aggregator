package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.writer;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
public class ItemWriterConfig {

    private static final String INSERT_ELECTRONIC_SQL = "INSERT INTO TbEletronico (id, nome, preco, dataCriacao) VALUES (:id,:nome, :preco, NOW())";
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
    public ClassifierCompositeItemWriter<Livro> bookCompositeWriter(ItemWriter<Livro> bookItemWriter, FlatFileItemWriter<Livro> bookFlatFileItemWriter) {
        ClassifierCompositeItemWriter<Livro> writer = new ClassifierCompositeItemWriter<>();
        writer.setClassifier(livro -> livro.isInvalidBook() ? bookItemWriter : bookFlatFileItemWriter);
        return writer;
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
    public FlatFileItemWriter<Livro> bookFlatFileItemWriter() {
        return new FlatFileItemWriterBuilder<Livro>()
                .name("bookFlatFileItemWriter")
                .resource(new FileSystemResource("./output/invalid_isbn_books.txt"))
                .delimited()
                .delimiter(",")
                .names("id", "isbn")
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