package ensino.emi.api.domain.missao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioMissaoRepository extends JpaRepository<UsuarioMissao, UsuarioMissaoId> {

    List<UsuarioMissao> findByUsuarioIdUsuario(Long idUsuario);

    @Query("select um.missao.idMissao from UsuarioMissao um where um.usuario.idUsuario = :idUsuario")
    List<Long> findIdsMissoesConcluidas(@Param("idUsuario") Long idUsuario);
}
