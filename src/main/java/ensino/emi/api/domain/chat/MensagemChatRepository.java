package ensino.emi.api.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {
    List<MensagemChat> findBySessaoChatOrderByIdMensagemAsc(SessaoChat sessaoChat);
}