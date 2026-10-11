CREATE TABLE progride (
    id_desafio BIGINT NOT NULL,
    id_missao BIGINT NOT NULL,
    PRIMARY KEY (id_desafio, id_missao),
    CONSTRAINT fk_progride_desafio FOREIGN KEY (id_desafio) REFERENCES desafio (id_desafio) ON DELETE CASCADE,
    CONSTRAINT fk_progride_missao FOREIGN KEY (id_missao) REFERENCES missao (id_missao) ON DELETE CASCADE
);

CREATE TABLE completa (
    id_missao BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    data_conclusao DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_missao, id_usuario),
    CONSTRAINT fk_completa_missao FOREIGN KEY (id_missao) REFERENCES missao (id_missao) ON DELETE CASCADE,
    CONSTRAINT fk_completa_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

CREATE TABLE conquista (
    id_insignia BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    data_conquista DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_insignia, id_usuario),
    CONSTRAINT fk_conquista_insignia FOREIGN KEY (id_insignia) REFERENCES insignia (id_insignia) ON DELETE CASCADE,
    CONSTRAINT fk_conquista_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

CREATE TABLE usuario_insignia (
    id_usuario BIGINT NOT NULL,
    id_insignia BIGINT NOT NULL,
    data_conquista DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_insignia),
    CONSTRAINT fk_usuario_insignia_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_insignia_insignia FOREIGN KEY (id_insignia) REFERENCES insignia (id_insignia) ON DELETE CASCADE
);

CREATE TABLE usuario_missao (
    id_usuario BIGINT NOT NULL,
    id_missao BIGINT NOT NULL,
    concluida BOOLEAN NOT NULL DEFAULT 0,
    status VARCHAR(50),
    data_atribuicao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_conquista DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_missao),
    CONSTRAINT fk_usuario_missao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_missao_missao FOREIGN KEY (id_missao) REFERENCES missao (id_missao) ON DELETE CASCADE
);

CREATE TABLE cursa (
    id_usuario BIGINT NOT NULL,
    id_trilha BIGINT NOT NULL,
    PRIMARY KEY (id_usuario, id_trilha),
    CONSTRAINT fk_cursa_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_cursa_trilha FOREIGN KEY (id_trilha) REFERENCES trilha (id_trilha) ON DELETE CASCADE
);