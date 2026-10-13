package ensino.emi.api.domain.insignia;

import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InsigniaService {

    private final InsigniaRepository insigniaRepository;

    public InsigniaService(InsigniaRepository insigniaRepository) {
        this.insigniaRepository = insigniaRepository;
    }

    public List<InsigniaResponseDTO> listar() {
        return insigniaRepository.findAll().stream().map(InsigniaResponseDTO::de).toList();
    }

    public InsigniaResponseDTO buscar(Long id) {
        return InsigniaResponseDTO.de(buscarEntidade(id));
    }

    @Transactional
    public InsigniaResponseDTO cadastrar(InsigniaDTO dados) {
        Insignia insignia = new Insignia();
        preencher(insignia, dados);
        return InsigniaResponseDTO.de(insigniaRepository.save(insignia));
    }

    @Transactional
    public InsigniaResponseDTO atualizar(Long id, InsigniaDTO dados) {
        Insignia insignia = buscarEntidade(id);
        preencher(insignia, dados);
        return InsigniaResponseDTO.de(insignia);
    }

    @Transactional
    public void deletar(Long id) {
        insigniaRepository.delete(buscarEntidade(id));
    }

    private void preencher(Insignia insignia, InsigniaDTO dados) {
        insignia.setNomeInsignia(dados.nomeInsignia());
        insignia.setDescricao(dados.descricao());
        insignia.setIconeUrl(dados.iconeUrl());
        insignia.setRaridade(dados.raridade());
    }

    private Insignia buscarEntidade(Long id) {
        return insigniaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insígnia", id));
    }
}
