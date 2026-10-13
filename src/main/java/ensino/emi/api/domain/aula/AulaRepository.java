package ensino.emi.api.domain.aula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByModuloIdModuloOrderByOrdemAsc(Long idModulo);

    long countByModuloIdModuloAndOrdemLessThan(Long idModulo, Integer ordem);

    boolean existsByModuloIdModuloAndOrdem(Long idModulo, Integer ordem);

    boolean existsByModuloIdModuloAndOrdemAndIdAulaNot(Long idModulo, Integer ordem, Long idAula);
}
