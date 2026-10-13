package ensino.emi.api.domain.desafio;

import ensino.emi.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "tentativa_desafio")
@Entity(name = "TentativaDesafio")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "idTentativa")
public class TentativaDesafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tentativa")
    private Long idTentativa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_desafio", nullable = false)
    private Desafio desafio;

    @Column(nullable = false)
    private int nota;

    @Column(nullable = false)
    private boolean aprovado;

    @Column(name = "data_tentativa", nullable = false)
    private LocalDateTime dataTentativa = LocalDateTime.now();
}
