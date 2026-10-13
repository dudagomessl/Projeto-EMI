package ensino.emi.api.domain.desafio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TentativaDesafioRepository extends JpaRepository<TentativaDesafio, Long> {

    boolean existsByUsuarioIdUsuarioAndDesafioIdDesafioAndAprovadoTrue(Long idUsuario, Long idDesafio);

    /** Desafios distintos aprovados pelo usuário (várias tentativas do mesmo desafio contam uma vez). */
    @Query("select count(distinct t.desafio.idDesafio) from TentativaDesafio t "
            + "where t.usuario.idUsuario = :idUsuario and t.aprovado = true")
    long contarDesafiosAprovados(@Param("idUsuario") Long idUsuario);

    @Query("select count(distinct t.desafio.idDesafio) from TentativaDesafio t "
            + "where t.usuario.idUsuario = :idUsuario and t.aprovado = true "
            + "and t.desafio.modulo.idModulo = :idModulo")
    long contarDesafiosAprovadosNoModulo(@Param("idUsuario") Long idUsuario,
                                         @Param("idModulo") Long idModulo);
}
