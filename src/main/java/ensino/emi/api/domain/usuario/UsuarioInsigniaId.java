package ensino.emi.api.domain.usuario;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInsigniaId implements Serializable {
    private Long idUsuario;
    private Long idInsignia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioInsigniaId that = (UsuarioInsigniaId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idInsignia, that.idInsignia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idInsignia);
    }
}