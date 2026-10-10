CREATE TABLE usuario (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_sessao_chat BIGINT UNIQUE,
    nome_usuario VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    experiencia INT DEFAULT 0,
    data_nascimento DATE NOT NULL,
    CONSTRAINT fk_usuario_sessao FOREIGN KEY (id_sessao_chat) REFERENCES sessao_chat (id_chat) ON DELETE SET NULL
);