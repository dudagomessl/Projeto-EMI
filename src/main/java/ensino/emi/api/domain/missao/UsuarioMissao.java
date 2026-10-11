package ensino.emi.api.domain.missao;

import ensino.emi.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "usuario_missao")
@Entity(name = "UsuarioMissao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMissao {

    @EmbeddedId
    private ensino.emi.api.domain.missao.UsuarioMissaoId id = new ensino.emi.api.domain.missao.UsuarioMissaoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMissao")
    @JoinColumn(name = "id_missao")
    private Missao missao;

    @Column(name = "concluida", nullable = false)
    private Boolean concluida = false;

    @Column(name = "data_conquista")
    private LocalDateTime dataConquista;
}