package ensino.emi.api.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoChatRepository extends JpaRepository<SessaoChat, Long> {
}