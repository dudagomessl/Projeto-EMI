package ensino.emi.api.domain.missao;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMissaoId implements Serializable {
    private Long idUsuario;
    private Long idMissao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioMissaoId that = (UsuarioMissaoId) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idMissao, that.idMissao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idMissao);
    }
}