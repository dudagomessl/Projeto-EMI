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

CREATE TABLE aula (
    id_aula BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_modulo BIGINT NOT NULL,
    id_desafio BIGINT,
    id_conteudo BIGINT,
    nome_aula VARCHAR(100) NOT NULL,
    status_de_conclusao INT NOT NULL DEFAULT 0,
    previa_do_conteudo TEXT NOT NULL,
    CONSTRAINT fk_aula_modulo FOREIGN KEY (id_modulo) REFERENCES modulo (id_modulo) ON DELETE CASCADE
);

CREATE TABLE desafio (
    id_desafio BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_aula BIGINT,
    nome_desafio VARCHAR(100) NOT NULL,
    pergunta_enunciado TEXT,
    resposta_correta VARCHAR(255),
    recompensa_xp INT DEFAULT 0,
    nota_de_corte INT NOT NULL,
    prompt_desafio TEXT NOT NULL,
    CONSTRAINT fk_desafio_aula FOREIGN KEY (id_aula) REFERENCES aula (id_aula) ON DELETE SET NULL
);

CREATE TABLE conteudo (
    id_conteudo BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_aula BIGINT,
    nome_conteudo VARCHAR(100) NOT NULL,
    titulo_secao VARCHAR(255),
    ordem INT,
    texto TEXT NOT NULL,
    corpo_texto TEXT,
    video VARCHAR(255) NOT NULL,
    CONSTRAINT fk_conteudo_aula FOREIGN KEY (id_aula) REFERENCES aula (id_aula) ON DELETE SET NULL
);

-- Adicionando as foreign keys circulares pendentes na tabela aula
ALTER TABLE aula ADD CONSTRAINT fk_aula_desafio FOREIGN KEY (id_desafio) REFERENCES desafio (id_desafio) ON DELETE SET NULL;
ALTER TABLE aula ADD CONSTRAINT fk_aula_conteudo FOREIGN KEY (id_conteudo) REFERENCES conteudo (id_conteudo) ON DELETE SET NULL;