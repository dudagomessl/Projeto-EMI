package ensino.emi.api.controller;

import ensino.emi.api.domain.aula.Aula;
import ensino.emi.api.domain.aula.AulaRepository;
import ensino.emi.api.domain.desafio.Desafio;
import ensino.emi.api.domain.desafio.DesafioDTO;
import ensino.emi.api.domain.desafio.DesafioRepository;
import ensino.emi.api.domain.desafio.SubmissaoDesafioDTO;
import ensino.emi.api.domain.usuario.Usuario;
import ensino.emi.api.domain.usuario.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/desafios")
@CrossOrigin(origins = "*")
public class DesafioController {

    private final DesafioRepository desafioRepository;
    private final AulaRepository aulaRepository;
    private final UsuarioRepository usuarioRepository;

    public DesafioController(DesafioRepository desafioRepository, AulaRepository aulaRepository, UsuarioRepository usuarioRepository) {
        this.desafioRepository = desafioRepository;
        this.aulaRepository = aulaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Desafio>> listarDesafios() {
        return ResponseEntity.ok(desafioRepository.findAll());
    }

    @GetMapping("/aula/{idAula}")
    public ResponseEntity<Desafio> buscarDesafioPorAula(@PathVariable Long idAula) {
        Desafio desafio = desafioRepository.findByAulaIdAula(idAula)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado para esta aula"));
        return ResponseEntity.ok(desafio);
    }

    @PostMapping
    public ResponseEntity<Desafio> cadastrarDesafio(@RequestBody DesafioDTO dados) {
        Aula aula = aulaRepository.findById(dados.idAula())
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        Desafio desafio = new Desafio();
        desafio.setAula(aula);
        desafio.setNomeDesafio(dados.nomeDesafio());
        desafio.setPerguntaEnunciado(dados.perguntaEnunciado());
        desafio.setRespostaCorreta(dados.respostaCorreta());
        desafio.setRecompensaXp(dados.recompensaXp());
        desafio.setNotaDeCorte(dados.notaDeCorte());
        desafio.setPromptDesafio(dados.promptDesafio()); // <--- Adicione esta linha no metodo cadastrarDesafio// <--- Linha nova

        return ResponseEntity.ok(desafioRepository.save(desafio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDesafio(@PathVariable Long id) {
        if (!desafioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        desafioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Rota onde o front-end envia a resposta do aluno para correção e ganho de XP
    @PostMapping("/{idDesafio}/responder")
    public ResponseEntity<String> responderDesafio(
            @PathVariable Long idDesafio,
            @RequestBody SubmissaoDesafioDTO submissao) {

        Desafio desafio = desafioRepository.findById(idDesafio)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        Usuario usuario = usuarioRepository.findById(submissao.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida se a resposta do aluno confere com o gabarito
        if (desafio.getRespostaCorreta().trim().equalsIgnoreCase(submissao.respostaUsuario().trim())) {
            // Adiciona o XP ao usuário
            usuario.setExperiencia(usuario.getExperiencia() + desafio.getRecompensaXp());
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Resposta correta! Você ganhou " + desafio.getRecompensaXp() + " XP.");
        } else {
            return ResponseEntity.badRequest().body("Resposta incorreta. Tente novamente!");
        }
    }
}