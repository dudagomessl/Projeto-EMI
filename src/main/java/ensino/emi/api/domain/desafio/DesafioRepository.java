package ensino.emi.api.domain.desafio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesafioRepository extends JpaRepository<Desafio, Long> {
    Optional<Desafio> findByAulaIdAula(Long idAula);
}