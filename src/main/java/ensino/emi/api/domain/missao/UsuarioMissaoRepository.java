package ensino.emi.api.domain.missao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioMissaoRepository extends JpaRepository<UsuarioMissao, UsuarioMissaoId> {
    List<UsuarioMissao> findByUsuarioIdUsuario(Long idUsuario);
}