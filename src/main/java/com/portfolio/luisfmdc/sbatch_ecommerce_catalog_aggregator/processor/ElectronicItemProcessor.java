package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.processor;

import com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain.Eletronico;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ElectronicItemProcessor implements ItemProcessor<Eletronico, Eletronico> {

    @Override
    public Eletronico process(Eletronico item) {
        BigDecimal acrescimoProduto = item.getPreco().multiply(BigDecimal.valueOf(item.getTaxa()));
        item.setPreco(item.getPreco().add(acrescimoProduto));
        return item;
    }
}