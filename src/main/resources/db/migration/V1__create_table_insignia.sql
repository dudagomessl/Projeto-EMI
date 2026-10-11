CREATE TABLE insignia (
    id_insignia BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_insignia VARCHAR(100) NOT NULL,
    descricao TEXT,
    descricao_insignia TEXT,
    raridade VARCHAR(50) NOT NULL,
    icone_url VARCHAR(255)
);