CREATE TABLE aula_concluida (
    id_usuario BIGINT NOT NULL,
    id_aula BIGINT NOT NULL,
    data_conclusao DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario, id_aula),
    CONSTRAINT fk_aula_concluida_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_aula_concluida_aula FOREIGN KEY (id_aula) REFERENCES aula (id_aula) ON DELETE CASCADE
);