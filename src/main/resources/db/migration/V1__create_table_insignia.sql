CREATE TABLE insignia (
    id_insignia BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_insignia VARCHAR(100) NOT NULL,
    descricao_insignia TEXT NOT NULL,
    raridade VARCHAR(50) NOT NULL
);