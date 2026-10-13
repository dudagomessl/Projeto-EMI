package ensino.emi.api.domain.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Long> {

    /** Mensagens mais recentes primeiro; use o Pageable para limitar a quantidade. */
    List<MensagemChat> findBySessaoChatIdChatOrderByIdMensagemDesc(Long idChat, Pageable pageable);
}
