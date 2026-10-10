CREATE TABLE mensagem_chat (
    id_mensagem BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_chat BIGINT NOT NULL,
    remetente VARCHAR(20) NOT NULL,
    mensagem TEXT NOT NULL,
    data_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mensagem_sessao FOREIGN KEY (id_chat) REFERENCES sessao_chat (id_chat) ON DELETE CASCADE
);