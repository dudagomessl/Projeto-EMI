CREATE TABLE missao (
    id_missao BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_insignia_recompensa BIGINT,
    nome_missao VARCHAR(100) NOT NULL,
    titulo VARCHAR(150),
    descricao TEXT,
    descricao_missao TEXT NOT NULL,
    recompensa_xp INT DEFAULT 0,
    CONSTRAINT fk_missao_insignia FOREIGN KEY (id_insignia_recompensa) REFERENCES insignia (id_insignia) ON DELETE SET NULL
);