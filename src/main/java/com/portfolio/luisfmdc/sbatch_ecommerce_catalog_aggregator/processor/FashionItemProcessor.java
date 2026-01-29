package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.processor;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Roupa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@StepScope
@Component
public class FashionItemProcessor implements ItemProcessor<Roupa, Roupa> {

    private final BigDecimal taxaG;
    private final BigDecimal taxaGG;

    public FashionItemProcessor(
            @Value("#{jobParameters['taxaG'] ?: 0.10}") BigDecimal taxaG,
            @Value("#{jobParameters['taxaGG'] ?: 0.15}") BigDecimal taxaGG) {
        if (taxaG == null) log.warn("[FashionItemProcessor] Parâmetro Taxa G não informado, utilizando valor padrão de 10%.");
        else log.info("[FashionItemProcessor] Taxa G: {}%.", taxaG.multiply(BigDecimal.valueOf(100)));
        if (taxaGG == null) log.warn("[FashionItemProcessor] Parâmetro Taxa GG não informado, utilizando valor padrão de 15%.");
        else log.info("[FashionItemProcessor] Taxa GG: {}%.", taxaGG.multiply(BigDecimal.valueOf(100)));
        this.taxaG = taxaG;
        this.taxaGG = taxaGG;
    }

    @Override
    public Roupa process(Roupa item) {
        if (item.getTamanho().equals("G")) {
            item.setPreco(item.getPreco().multiply(BigDecimal.ONE.add(taxaG)));
        } else if (item.getTamanho().equals("GG")) {
            item.setPreco(item.getPreco().multiply(BigDecimal.ONE.add(taxaGG)));
        }
        return item;
    }
}