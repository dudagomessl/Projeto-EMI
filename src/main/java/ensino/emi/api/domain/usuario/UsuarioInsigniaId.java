package ensino.emi.api.domain.usuario;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioInsigniaId implements Serializable {

    private Long idUsuario;
    private Long idInsignia;
}
