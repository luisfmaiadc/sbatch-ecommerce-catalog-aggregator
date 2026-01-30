package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.processor;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Livro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class BookItemProcessor implements ItemProcessor<Livro, Livro> {
    
    private int qtdLivrosInvalidos = 0;

    @Override
    public Livro process(Livro item) {
        if (item.getIsbn() == null || item.getIsbn().length() != 13 || !item.getIsbn().matches("\\d+")) {
            item.setInvalidBook(true);
            qtdLivrosInvalidos++;
            return item;
        }

        String isbn = item.getIsbn();
        String isbnFormatado = String.format("%s-%s-%s-%s-%s",
                isbn.substring(0, 3), isbn.substring(3, 5), isbn.substring(5, 10), isbn.substring(10, 12), isbn.charAt(12));
        item.setIsbn(isbnFormatado);
        return item;
    }
    
    @AfterStep
    public void afterStep() {
        if (qtdLivrosInvalidos > 0) log.info("[BookItemProcessor] Quantidade de livros inválidos: {}", qtdLivrosInvalidos);
    }
}