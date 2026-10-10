package ensino.emi.api.domain.aula;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaConcluidaRepository extends JpaRepository<AulaConcluida, AulaConcluidaId> {
    boolean existsByUsuarioIdUsuarioAndAulaIdAula(Long idUsuario, Long idAula);
}