CREATE TABLE missao (
    id_missao BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_insignia BIGINT,
    nome_missao VARCHAR(100) NOT NULL,
    descricao_missao TEXT NOT NULL,
    CONSTRAINT fk_missao_insignia FOREIGN KEY (id_insignia) REFERENCES insignia (id_insignia) ON DELETE SET NULL
);