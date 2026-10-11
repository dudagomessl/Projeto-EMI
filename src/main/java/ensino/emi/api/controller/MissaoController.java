package ensino.emi.api.controller;

import ensino.emi.api.domain.insignia.Insignia;
import ensino.emi.api.domain.insignia.InsigniaRepository;
import ensino.emi.api.domain.missao.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@CrossOrigin(origins = "*")
public class MissaoController {

    private final MissaoRepository missaoRepository;
    private final UsuarioMissaoRepository usuarioMissaoRepository;
    private final InsigniaRepository insigniaRepository;

    public MissaoController(
            MissaoRepository missaoRepository,
            UsuarioMissaoRepository usuarioMissaoRepository,
            InsigniaRepository insigniaRepository) {
        this.missaoRepository = missaoRepository;
        this.usuarioMissaoRepository = usuarioMissaoRepository;
        this.insigniaRepository = insigniaRepository;
    }

    // GET /missoes -> Listar todas as missões cadastradas
    @GetMapping
    public ResponseEntity<List<Missao>> listarMissoes() {
        return ResponseEntity.ok(missaoRepository.findAll());
    }

    // POST /missoes -> Criar uma nova missão
    @PostMapping
    public ResponseEntity<Missao> cadastrarMissao(@RequestBody MissaoDTO dados) {
        Missao missao = new Missao();
        missao.setTitulo(dados.titulo());
        missao.setDescricao(dados.descricao());
        missao.setRecompensaXp(dados.recompensaXp());

        if (dados.idInsigniaRecompensa() != null) {
            Insignia insignia = insigniaRepository.findById(dados.idInsigniaRecompensa())
                    .orElseThrow(() -> new RuntimeException("Insígnia não encontrada"));
            missao.setInsigniaRecompensa(insignia);
        }

        return ResponseEntity.ok(missaoRepository.save(missao));
    }

    // DELETE /missoes/{id} -> Excluir uma missão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMissao(@PathVariable Long id) {
        if (!missaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        missaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET /missoes/usuario/{idUsuario} -> Listar missões e status de progresso do usuário
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioMissao>> listarMissoesDoUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioMissaoRepository.findByUsuarioIdUsuario(idUsuario));
    }
}