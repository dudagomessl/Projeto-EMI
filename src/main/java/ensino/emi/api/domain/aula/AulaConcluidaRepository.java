package ensino.emi.api.domain.aula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AulaConcluidaRepository extends JpaRepository<AulaConcluida, AulaConcluidaId> {

    boolean existsByUsuarioIdUsuarioAndAulaIdAula(Long idUsuario, Long idAula);

    long countByUsuarioIdUsuario(Long idUsuario);

    long countByUsuarioIdUsuarioAndAulaModuloIdModulo(Long idUsuario, Long idModulo);

    @Query("select ac.aula.idAula from AulaConcluida ac "
            + "where ac.usuario.idUsuario = :idUsuario and ac.aula.modulo.idModulo = :idModulo")
    List<Long> findIdsAulasConcluidasNoModulo(@Param("idUsuario") Long idUsuario,
                                              @Param("idModulo") Long idModulo);

    /** Quantas aulas do módulo com ordem menor que a informada o usuário já concluiu. */
    @Query("select count(ac) from AulaConcluida ac "
            + "where ac.usuario.idUsuario = :idUsuario "
            + "and ac.aula.modulo.idModulo = :idModulo and ac.aula.ordem < :ordem")
    long contarConcluidasAntesDaOrdem(@Param("idUsuario") Long idUsuario,
                                      @Param("idModulo") Long idModulo,
                                      @Param("ordem") Integer ordem);
}
