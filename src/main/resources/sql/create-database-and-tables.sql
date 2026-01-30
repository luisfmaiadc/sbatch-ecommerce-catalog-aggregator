CREATE DATABASE sbatch_catalog;
USE sbatch_catalog;

CREATE TABLE TbEletronico (
    id INT UNSIGNED PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    dataCriacao DATETIME NOT NULL
);

CREATE TABLE TbLivro (
    id INT UNSIGNED PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(17) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    dataCriacao DATETIME NOT NULL
);

CREATE TABLE TbRoupa (
    id INT UNSIGNED PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    tamanho VARCHAR(3) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    dataCriacao DATETIME NOT NULL
);