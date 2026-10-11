package ensino.emi.api.domain.modulo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findByTrilhaIdTrilha(Long idTrilha);
}