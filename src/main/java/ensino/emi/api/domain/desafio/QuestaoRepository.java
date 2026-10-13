package ensino.emi.api.domain.desafio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    List<Questao> findByDesafioIdDesafioOrderByIdQuestaoAsc(Long idDesafio);
}
