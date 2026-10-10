CREATE TABLE trilha (
    id_trilha BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_trilha VARCHAR(100) NOT NULL
);

CREATE TABLE modulo (
    id_modulo BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_trilha BIGINT NOT NULL,
    nome_modulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    CONSTRAINT fk_modulo_trilha FOREIGN KEY (id_trilha) REFERENCES trilha (id_trilha) ON DELETE CASCADE
);

CREATE TABLE desafio (
    id_desafio BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_desafio VARCHAR(100) NOT NULL,
    nota_de_corte INT NOT NULL,
    prompt_desafio TEXT NOT NULL
);

CREATE TABLE conteudo (
    id_conteudo BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_conteudo VARCHAR(100) NOT NULL,
    texto TEXT NOT NULL,
    video VARCHAR(255) NOT NULL
);

CREATE TABLE aula (
    id_aula BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_modulo BIGINT NOT NULL,
    id_desafio BIGINT,
    id_conteudo BIGINT,
    nome_aula VARCHAR(100) NOT NULL,
    status_de_conclusao INT NOT NULL DEFAULT 0,
    previa_do_conteudo TEXT NOT NULL,
    CONSTRAINT fk_aula_modulo FOREIGN KEY (id_modulo) REFERENCES modulo (id_modulo) ON DELETE CASCADE,
    CONSTRAINT fk_aula_desafio FOREIGN KEY (id_desafio) REFERENCES desafio (id_desafio) ON DELETE SET NULL,
    CONSTRAINT fk_aula_conteudo FOREIGN KEY (id_conteudo) REFERENCES conteudo (id_conteudo) ON DELETE SET NULL
);