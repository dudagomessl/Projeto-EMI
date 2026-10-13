package ensino.emi.api.domain.chat;

import java.time.LocalDateTime;

public record MensagemChatDTO(Remetente remetente, String mensagem, LocalDateTime dataEnvio) {

    public static MensagemChatDTO de(MensagemChat m) {
        return new MensagemChatDTO(m.getRemetente(), m.getMensagem(), m.getDataEnvio());
    }
}
