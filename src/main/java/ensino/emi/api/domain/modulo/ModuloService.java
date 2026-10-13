package ensino.emi.api.domain.modulo;

import ensino.emi.api.domain.aula.AulaConcluidaRepository;
import ensino.emi.api.domain.aula.AulaRepository;
import ensino.emi.api.domain.desafio.Desafio;
import ensino.emi.api.domain.desafio.DesafioRepository;
import ensino.emi.api.domain.desafio.DesafioService;
import ensino.emi.api.domain.desafio.TentativaDesafioRepository;
import ensino.emi.api.domain.trilha.Trilha;
import ensino.emi.api.domain.trilha.TrilhaRepository;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import ensino.emi.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final TrilhaRepository trilhaRepository;
    private final AulaRepository aulaRepository;
    private final AulaConcluidaRepository aulaConcluidaRepository;
    private final DesafioRepository desafioRepository;
    private final TentativaDesafioRepository tentativaDesafioRepository;
    private final UsuarioRepository usuarioRepository;
    private final DesafioService desafioService;

    public ModuloService(ModuloRepository moduloRepository,
                         TrilhaRepository trilhaRepository,
                         AulaRepository aulaRepository,
                         AulaConcluidaRepository aulaConcluidaRepository,
                         DesafioRepository desafioRepository,
                         TentativaDesafioRepository tentativaDesafioRepository,
                         UsuarioRepository usuarioRepository,
                         DesafioService desafioService) {
        this.moduloRepository = moduloRepository;
        this.trilhaRepository = trilhaRepository;
        this.aulaRepository = aulaRepository;
        this.aulaConcluidaRepository = aulaConcluidaRepository;
        this.desafioRepository = desafioRepository;
        this.tentativaDesafioRepository = tentativaDesafioRepository;
        this.usuarioRepository = usuarioRepository;
        this.desafioService = desafioService;
    }

    public List<ModuloResponseDTO> listar(Long idTrilha) {
        List<Modulo> modulos = idTrilha == null
                ? moduloRepository.findAll()
                : moduloRepository.findByTrilhaIdTrilhaOrderByIdModuloAsc(idTrilha);
        return modulos.stream().map(ModuloResponseDTO::de).toList();
    }

    public ModuloResponseDTO buscar(Long id) {
        return ModuloResponseDTO.de(buscarEntidade(id));
    }

    @Transactional
    public ModuloResponseDTO cadastrar(ModuloDTO dados) {
        Modulo modulo = new Modulo();
        preencher(modulo, dados);
        return ModuloResponseDTO.de(moduloRepository.save(modulo));
    }

    @Transactional
    public ModuloResponseDTO atualizar(Long id, ModuloDTO dados) {
        Modulo modulo = buscarEntidade(id);
        preencher(modulo, dados);
        return ModuloResponseDTO.de(modulo);
    }

    @Transactional
    public void deletar(Long id) {
        moduloRepository.delete(buscarEntidade(id));
    }

    /** Linha do módulo (aulas + desafios, em ordem) com o estado de cada item para o usuário. */
    public List<ItemProgressoDTO> progresso(Long idModulo, Long idUsuario) {
        if (!moduloRepository.existsById(idModulo)) {
            throw new RecursoNaoEncontradoException("Módulo", idModulo);
        }
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNaoEncontradoException("Usuário", idUsuario);
        }

        Set<Long> aulasConcluidas = new HashSet<>(
                aulaConcluidaRepository.findIdsAulasConcluidasNoModulo(idUsuario, idModulo));

        List<ItemProgressoDTO> itens = new ArrayList<>();

        aulaRepository.findByModuloIdModuloOrderByOrdemAsc(idModulo).forEach(aula ->
                itens.add(new ItemProgressoDTO(TipoItem.AULA, aula.getIdAula(), aula.getNomeAula(),
                        aula.getOrdem(), aulasConcluidas.contains(aula.getIdAula()), true)));

        for (Desafio desafio : desafioRepository.findByModuloIdModuloOrderByOrdemAsc(idModulo)) {
            boolean aprovado = tentativaDesafioRepository
                    .existsByUsuarioIdUsuarioAndDesafioIdDesafioAndAprovadoTrue(idUsuario, desafio.getIdDesafio());
            boolean liberado = desafioService.estaLiberado(desafio, idUsuario);
            itens.add(new ItemProgressoDTO(TipoItem.DESAFIO, desafio.getIdDesafio(), desafio.getNomeDesafio(),
                    desafio.getOrdem(), aprovado, liberado));
        }

        itens.sort(Comparator.comparing(ItemProgressoDTO::ordem).thenComparing(ItemProgressoDTO::tipo));
        return itens;
    }

    private void preencher(Modulo modulo, ModuloDTO dados) {
        Trilha trilha = trilhaRepository.findById(dados.idTrilha())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Trilha", dados.idTrilha()));
        modulo.setNomeModulo(dados.nomeModulo());
        modulo.setDescricao(dados.descricao());
        modulo.setTrilha(trilha);
    }

    private Modulo buscarEntidade(Long id) {
        return moduloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Módulo", id));
    }
}
