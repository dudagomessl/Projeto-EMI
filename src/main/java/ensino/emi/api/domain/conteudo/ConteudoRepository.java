package ensino.emi.api.domain.conteudo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {
    List<Conteudo> findByAulaIdAula(Long idAula);
}