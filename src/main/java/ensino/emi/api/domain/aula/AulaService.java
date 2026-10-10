package ensino.emi.api.domain.aula;

import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AulaService {

    private static final int XP_POR_AULA = 250;

    private final AulaRepository aulaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AulaConcluidaRepository aulaConcluidaRepository;

    public AulaService(AulaRepository aulaRepository,
                       UsuarioRepository usuarioRepository,
                       AulaConcluidaRepository aulaConcluidaRepository) {
        this.aulaRepository = aulaRepository;
        this.usuarioRepository = usuarioRepository;
        this.aulaConcluidaRepository = aulaConcluidaRepository;
    }

    @Transactional
    public ResultadoConclusaoDTO concluirAula(Long idUsuario, Long idAula) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        boolean jaConcluiu = aulaConcluidaRepository.existsByUsuarioIdUsuarioAndAulaIdAula(idUsuario, idAula);

        if (jaConcluiu) {
            return new ResultadoConclusaoDTO(
                    "Aula já concluída anteriormente.",
                    0,
                    usuario.getExperiencia(),
                    usuario.getXpNoNivelAtual(),
                    usuario.getNivelAtual()
            );
        }

        AulaConcluida conclusao = new AulaConcluida(
                new AulaConcluidaId(idUsuario, idAula),
                usuario,
                aula,
                null
        );
        aulaConcluidaRepository.save(conclusao);

        usuario.adicionarXp(XP_POR_AULA);
        usuarioRepository.save(usuario);

        return new ResultadoConclusaoDTO(
                "Aula concluída com sucesso!",
                XP_POR_AULA,
                usuario.getExperiencia(),
                usuario.getXpNoNivelAtual(),
                usuario.getNivelAtual()
        );
    }

    public record ResultadoConclusaoDTO(
            String mensagem,
            int xpGanha,
            int xpTotal,
            int xpAtualNivel,
            int nivelAtual
    ) {
    }
}