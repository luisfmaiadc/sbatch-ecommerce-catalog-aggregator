package com.portfolio.luisfmdc.sbatch_ecommerce_catalog_aggregator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Eletronico {

    private int id;
    private String nome;
    private BigDecimal preco;
    private double taxa;
}