package ensino.emi.api.controller;

import ensino.emi.api.usuario.DadosCadastroUsuario;
import ensino.emi.api.usuario.DadosListagemUsuario;
import ensino.emi.api.usuario.Usuario;
import ensino.emi.api.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        repository.save(new Usuario(dados));
    }

    // Versão do get sem usar o page de organização
    @GetMapping("/padrao")
    public List<DadosListagemUsuario> listarPadrao() {
        return repository.findAll().stream().map(DadosListagemUsuario::new).toList();
    }

    @GetMapping
    public Page<DadosListagemUsuario> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemUsuario::new);
    }
}