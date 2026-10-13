package ensino.emi.api.domain.usuario;

import ensino.emi.api.domain.chat.SessaoChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Query("select u.sessaoChat from Usuario u where u.idUsuario = :idUsuario")
    Optional<SessaoChat> buscarSessaoChat(@Param("idUsuario") Long idUsuario);
}
