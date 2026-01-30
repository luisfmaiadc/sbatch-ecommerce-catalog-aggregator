<h1 align="center">sbatch-ecommerce-catalog-aggregator</h1>

<p align="center" style="margin-bottom: 20;">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot 3.5.9" />
  <img src="https://img.shields.io/badge/Spring%20Batch-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Batch 5.2.4" />
  <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/apache%20maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
</p>

<p align="center">
O <b>sbatch-ecommerce-catalog-aggregator</b> é uma aplicação de processamento em lote desenvolvida com <b>Java 21</b>, <b>Spring Boot 3</b> e <b>Spring Batch 5</b>, responsável por importar, processar e persistir dados de múltiplas categorias de produtos a partir de arquivos <code>.csv</code>, utilizando <b>execução paralela de Steps</b>.
</p>

---

<h2>📌 Visão Geral</h2>

<p align="justify">
O projeto simula um pipeline de ingestão de dados para um catálogo de e-commerce, consumindo planilhas segmentadas por categoria — como <b>eletrônicos</b>, <b>livros</b> e <b>moda</b> — aplicando regras de negócio específicas para cada tipo de produto e persistindo os registros válidos em um banco <b>MySQL</b>.
</p>

<p align="justify">
Além da importação, o sistema valida ISBNs de livros e redireciona registros inválidos para um <b>arquivo de rejeição (<code>.txt</code>)</b>, demonstrando o uso de <b>roteamento condicional de escrita</b> com <b>ClassifierCompositeItemWriter</b>.
</p>

<p align="justify">
O principal foco técnico do projeto é a prática de <b>execução paralela de Steps no Spring Batch</b>, permitindo o processamento simultâneo de diferentes categorias de produtos, aumentando desempenho e escalabilidade.
</p>

---

<h2>🚀 Tecnologias Utilizadas</h2>

* **Java 21**
* **Spring Boot 3.5.9**
* **Spring Batch 5.2.4**
* **Spring JDBC**
* **MySQL**
* **Apache Maven**

---

<h2>⚙️ Fluxo de Processamento (ETL)</h2>

O Job é estruturado em múltiplos Steps executados em paralelo, cada um responsável por uma categoria de produto.

<h3>🔹 Eletrônicos</h3>

1. **Reader (`FlatFileItemReader`)**  
   Lê registros do arquivo `eletronicos.csv`.

2. **Processor (`ElectronicItemProcessor`)**  
   Aplica acréscimo no preço com base na taxa do produto.

3. **Writer (`JdbcBatchItemWriter`)**  
   Persiste os registros na tabela `TbEletronico`.

---

<h3>🔹 Livros</h3>

1. **Reader (`FlatFileItemReader`)**  
   Lê registros do arquivo `livros.csv`.

2. **Processor (`BookItemProcessor`)**
    * Valida ISBN
    * Formata ISBN válido
    * Marca registros inválidos
    * Conta total de livros inválidos via `@AfterStep`

3. **Writer (`ClassifierCompositeItemWriter`)**
    * Livros válidos → banco de dados
    * Livros com ISBN inválido → arquivo `output/invalid_isbn_books.txt`

---

<h3>🔹 Moda</h3>

1. **Reader (`FlatFileItemReader`)**  
   Lê registros do arquivo `moda.csv`.

2. **Processor (`FashionItemProcessor`)**  
   Aplica acréscimos condicionais conforme tamanho:
    * **G** → taxa configurável (default 10%)
    * **GG** → taxa configurável (default 15%)

3. **Writer (`JdbcBatchItemWriter`)**  
   Persiste registros na tabela `TbRoupa`.

---

<h2>🏗️ Estrutura do Projeto</h2>

```bash
sbatch-ecommerce-catalog-aggregator
│-- src/main/java/com/portfolio/luisfmdc/sbatch_ecommerce_catalog_aggregator
│   ├── config/               # Configurações do DataSources
│   ├── domain/               # Modelos (Livro, Eletronico, Roupa)
│   ├── job/                  # Definição do Job e paralelismo
│   ├── step/                 # Steps por categoria
│   ├── reader/               # FlatFileItemReaders
│   ├── processor/            # Regras de negócio por produto
│   └── writer/               # Writers JDBC e File Writers
│-- src/main/resources
│   ├── input/                # Arquivos CSV de entrada
│   ├── output/               # Arquivos gerados (livros inválidos)
│   ├── sql/                  # Scripts SQL
│   └── application.properties
```

---

<h2>🛠️ Configuração e Execução</h2>

<h3>📌 Pré-requisitos</h3>

- Java 21
- Apache Maven
- MySQL Server rodando na porta 3306

<h3>🗄️ Configuração do Banco de Dados</h3>

Execute os scripts localizados em `src/main/resources/sql/create-database-and-tables.sql`.

<h3>📜 Configuração da Aplicação</h3>

```properties
spring.application.name=sbatch-ecommerce-catalog-aggregator

spring.datasource.jdbcUrl=jdbc:mysql://localhost:3306/sbatch_execution
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.batch.jdbc.initialize-schema=always

app.datasource.jdbcUrl=jdbc:mysql://localhost:3306/sbatch_catalog?rewriteBatchedStatements=true
app.datasource.username=${APP_DATASOURCE_USERNAME}
app.datasource.password=${APP_DATASOURCE_PASSWORD}
```

<h3>🚀 Executando o Job</h3>

```bash
git clone https://github.com/luisfmaiadc/sbatch-ecommerce-catalog-aggregator.git
cd sbatch-ecommerce-catalog-aggregator

mvn clean install
mvn spring-boot:run
```

---

<h2>📚 Aprendizados</h2>

<ul>
  <li><b>Execução paralela de Steps:</b> Processamento simultâneo de múltiplas categorias usando Spring Batch.</li> 
  <li><b>Leitura multi-origem:</b> Uso de múltiplos <code>FlatFileItemReader</code> com <code>@StepScope</code> e parâmetros dinâmicos.</li> 
  <li><b>Roteamento condicional de escrita:</b> Implementação de <code>ClassifierCompositeItemWriter</code> para separar registros válidos e inválidos.</li> 
  <li><b>Validação e enriquecimento de dados:</b> ISBN formatting, regras de precificação e validação de integridade.</li> 
  <li><b>Geração de arquivos de rejeição:</b> Persistência de registros inválidos para auditoria e correção futura.</li> 
</ul>

---

<h2>🎯 Objetivo do Projeto</h2>

Este projeto foi desenvolvido com foco em consolidar conhecimentos avançados em Spring Batch, especialmente:

- Paralelismo em Jobs
- Estruturação de pipelines ETL
- Boas práticas de Readers, Processors e Writers
- Processamento escalável e orientado a domínio

<hr/>

<p align="center">Desenvolvido por <b>Luis Felipe Maia da Costa</b></p>