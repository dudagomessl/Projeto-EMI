package ensino.emi.api.domain.chat;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "mensagem_chat")
@Entity(name = "MensagemChat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "idMensagem")
public class MensagemChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long idMensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat")
    private SessaoChat sessaoChat;

    @Column(nullable = false, length = 20)
    private String remetente;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio = LocalDateTime.now();
}