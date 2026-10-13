package ensino.emi.api.domain.desafio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesafioRepository extends JpaRepository<Desafio, Long> {

    List<Desafio> findByModuloIdModuloOrderByOrdemAsc(Long idModulo);

    boolean existsByModuloIdModuloAndOrdem(Long idModulo, Integer ordem);

    boolean existsByModuloIdModuloAndOrdemAndIdDesafioNot(Long idModulo, Integer ordem, Long idDesafio);
}
