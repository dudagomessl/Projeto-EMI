package ensino.emi.api.domain.aula;

import ensino.emi.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "aula_concluida")
@Entity(name = "AulaConcluida")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AulaConcluida {

    @EmbeddedId
    private AulaConcluidaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAula")
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @Column(name = "data_conclusao", nullable = false)
    private LocalDateTime dataConclusao = LocalDateTime.now();
}
