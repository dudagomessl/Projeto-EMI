package ensino.emi.api.domain.trilha;

import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;

    public TrilhaService(TrilhaRepository trilhaRepository) {
        this.trilhaRepository = trilhaRepository;
    }

    public List<TrilhaResponseDTO> listar() {
        return trilhaRepository.findAll().stream().map(TrilhaResponseDTO::de).toList();
    }

    public TrilhaResponseDTO buscar(Long id) {
        return TrilhaResponseDTO.de(buscarEntidade(id));
    }

    @Transactional
    public TrilhaResponseDTO cadastrar(TrilhaDTO dados) {
        Trilha trilha = new Trilha();
        trilha.setNomeTrilha(dados.nomeTrilha());
        return TrilhaResponseDTO.de(trilhaRepository.save(trilha));
    }

    @Transactional
    public TrilhaResponseDTO atualizar(Long id, TrilhaDTO dados) {
        Trilha trilha = buscarEntidade(id);
        trilha.setNomeTrilha(dados.nomeTrilha());
        return TrilhaResponseDTO.de(trilha);
    }

    /** Módulos, aulas, desafios etc. são removidos em cascata pelo próprio banco (ON DELETE CASCADE). */
    @Transactional
    public void deletar(Long id) {
        trilhaRepository.delete(buscarEntidade(id));
    }

    private Trilha buscarEntidade(Long id) {
        return trilhaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Trilha", id));
    }
}
