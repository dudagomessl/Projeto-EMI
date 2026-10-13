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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_chat", nullable = false)
    private SessaoChat sessaoChat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Remetente remetente;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    // @Builder.Default é obrigatório: sem ele o builder ignora o valor inicial e grava NULL
    @Builder.Default
    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio = LocalDateTime.now();
}
