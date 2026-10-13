package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** Existir uma linha aqui significa que o usuário CONCLUIU a missão. O progresso é calculado na hora. */
@Table(name = "usuario_missao")
@Entity(name = "UsuarioMissao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioMissao {

    @EmbeddedId
    private UsuarioMissaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMissao")
    @JoinColumn(name = "id_missao")
    private Missao missao;

    @Column(name = "data_conquista", nullable = false)
    private LocalDateTime dataConquista = LocalDateTime.now();
}
