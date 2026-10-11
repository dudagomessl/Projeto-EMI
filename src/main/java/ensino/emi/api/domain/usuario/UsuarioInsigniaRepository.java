package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.insignia.Insignia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioInsigniaRepository extends JpaRepository<UsuarioInsignia, UsuarioInsigniaId> {

    @Query("SELECT ui.insignia FROM UsuarioInsignia ui WHERE ui.usuario.idUsuario = :idUsuario")
    List<Insignia> findInsigniasPorUsuario(@Param("idUsuario") Long idUsuario);
}