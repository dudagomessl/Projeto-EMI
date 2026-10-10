package ensino.emi.api.domain.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "sessao_chat")
@Entity(name = "SessaoChat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idChat")
public class SessaoChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long idChat;

    @Column(name = "prompt_chat", nullable = false, columnDefinition = "TEXT")
    private String promptChat;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "sessaoChat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MensagemChat> mensagens = new ArrayList<>();
}