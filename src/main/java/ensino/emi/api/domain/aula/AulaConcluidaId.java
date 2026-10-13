package ensino.emi.api.domain.aula;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AulaConcluidaId implements Serializable {

    private Long idUsuario;
    private Long idAula;
}
