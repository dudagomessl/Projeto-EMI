
CREATE TABLE sessao_chat (
    id_chat       BIGINT PRIMARY KEY AUTO_INCREMENT,
    prompt_chat   TEXT NOT NULL,
    data_criacao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario (
    id_usuario       BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_sessao_chat   BIGINT UNIQUE,
    nome_usuario     VARCHAR(100) NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    senha            VARCHAR(100) NOT NULL,
    experiencia      INT NOT NULL DEFAULT 0,
    data_nascimento  DATE NOT NULL,
    versao           BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_usuario_sessao FOREIGN KEY (id_sessao_chat)
        REFERENCES sessao_chat (id_chat) ON DELETE SET NULL
);

CREATE TABLE mensagem_chat (
    id_mensagem  BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_chat      BIGINT NOT NULL,
    remetente    VARCHAR(20) NOT NULL,
    mensagem     TEXT NOT NULL,
    data_envio   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mensagem_sessao FOREIGN KEY (id_chat)
        REFERENCES sessao_chat (id_chat) ON DELETE CASCADE,
    INDEX idx_mensagem_chat (id_chat, id_mensagem)
);

-- ---------------------------------------------------------------------
-- Conteúdo: trilha > módulo > (aulas + desafios), ordenados por "ordem"
-- ---------------------------------------------------------------------
CREATE TABLE trilha (
    id_trilha    BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_trilha  VARCHAR(100) NOT NULL
);

CREATE TABLE modulo (
    id_modulo    BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_trilha    BIGINT NOT NULL,
    nome_modulo  VARCHAR(100) NOT NULL,
    descricao    TEXT NOT NULL,
    CONSTRAINT fk_modulo_trilha FOREIGN KEY (id_trilha)
        REFERENCES trilha (id_trilha) ON DELETE CASCADE
);

CREATE TABLE aula (
    id_aula             BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_modulo           BIGINT NOT NULL,
    nome_aula           VARCHAR(100) NOT NULL,
    previa_do_conteudo  VARCHAR(500) NOT NULL,
    texto               TEXT NOT NULL,
    video_url           VARCHAR(255),
    ordem               INT NOT NULL,
    CONSTRAINT fk_aula_modulo FOREIGN KEY (id_modulo)
        REFERENCES modulo (id_modulo) ON DELETE CASCADE,
    INDEX idx_aula_modulo_ordem (id_modulo, ordem)
);

CREATE TABLE desafio (
    id_desafio     BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_modulo      BIGINT NOT NULL,
    nome_desafio   VARCHAR(100) NOT NULL,
    nota_de_corte  INT NOT NULL,
    recompensa_xp  INT NOT NULL DEFAULT 0,
    ordem          INT NOT NULL,
    CONSTRAINT fk_desafio_modulo FOREIGN KEY (id_modulo)
        REFERENCES modulo (id_modulo) ON DELETE CASCADE,
    INDEX idx_desafio_modulo_ordem (id_modulo, ordem)
);

CREATE TABLE questao (
    id_questao        BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_desafio        BIGINT NOT NULL,
    enunciado         TEXT NOT NULL,
    alternativa_a     VARCHAR(500) NOT NULL,
    alternativa_b     VARCHAR(500) NOT NULL,
    alternativa_c     VARCHAR(500) NOT NULL,
    alternativa_d     VARCHAR(500) NOT NULL,
    resposta_correta  VARCHAR(1) NOT NULL,
    CONSTRAINT fk_questao_desafio FOREIGN KEY (id_desafio)
        REFERENCES desafio (id_desafio) ON DELETE CASCADE
);

-- ---------------------------------------------------------------------
-- Gamificação
-- ---------------------------------------------------------------------
CREATE TABLE insignia (
    id_insignia    BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_insignia  VARCHAR(100) NOT NULL,
    descricao      TEXT,
    raridade       VARCHAR(20) NOT NULL,
    icone_url      VARCHAR(255)
);

CREATE TABLE missao (
    id_missao               BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo                  VARCHAR(100) NOT NULL,
    descricao               TEXT NOT NULL,
    tipo                    VARCHAR(30) NOT NULL,
    meta                    INT NOT NULL,
    id_modulo               BIGINT,
    recompensa_xp           INT NOT NULL DEFAULT 0,
    id_insignia_recompensa  BIGINT,
    CONSTRAINT fk_missao_modulo FOREIGN KEY (id_modulo)
        REFERENCES modulo (id_modulo) ON DELETE CASCADE,
    CONSTRAINT fk_missao_insignia FOREIGN KEY (id_insignia_recompensa)
        REFERENCES insignia (id_insignia) ON DELETE SET NULL
);

-- ---------------------------------------------------------------------
-- Progresso do usuário
-- ---------------------------------------------------------------------
CREATE TABLE aula_concluida (
    id_usuario      BIGINT NOT NULL,
    id_aula         BIGINT NOT NULL,
    data_conclusao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_aula),
    CONSTRAINT fk_aula_concluida_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_aula_concluida_aula FOREIGN KEY (id_aula)
        REFERENCES aula (id_aula) ON DELETE CASCADE
);

CREATE TABLE tentativa_desafio (
    id_tentativa    BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario      BIGINT NOT NULL,
    id_desafio      BIGINT NOT NULL,
    nota            INT NOT NULL,
    aprovado        BOOLEAN NOT NULL,
    data_tentativa  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tentativa_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_tentativa_desafio FOREIGN KEY (id_desafio)
        REFERENCES desafio (id_desafio) ON DELETE CASCADE,
    INDEX idx_tentativa_usuario_desafio (id_usuario, id_desafio)
);

CREATE TABLE usuario_missao (
    id_usuario      BIGINT NOT NULL,
    id_missao       BIGINT NOT NULL,
    data_conquista  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_missao),
    CONSTRAINT fk_usuario_missao_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_missao_missao FOREIGN KEY (id_missao)
        REFERENCES missao (id_missao) ON DELETE CASCADE
);

CREATE TABLE usuario_insignia (
    id_usuario      BIGINT NOT NULL,
    id_insignia     BIGINT NOT NULL,
    data_conquista  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_insignia),
    CONSTRAINT fk_usuario_insignia_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_insignia_insignia FOREIGN KEY (id_insignia)
        REFERENCES insignia (id_insignia) ON DELETE CASCADE
);