package ensino.emi.api.domain.missao;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioMissaoId implements Serializable {

    private Long idUsuario;
    private Long idMissao;
}
