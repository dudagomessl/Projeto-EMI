package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.insignia.Insignia;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "usuario_insignia")
@Entity(name = "UsuarioInsignia")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioInsignia {

    @EmbeddedId
    private UsuarioInsigniaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInsignia")
    @JoinColumn(name = "id_insignia")
    private Insignia insignia;

    @Column(name = "data_conquista", nullable = false)
    private LocalDateTime dataConquista = LocalDateTime.now();
}
