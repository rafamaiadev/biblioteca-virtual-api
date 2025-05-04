package br.com.raphael.biblioteca_virtual_api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.service.PerfilAcessoService;

@RestController
@RequestMapping("/perfis")
public class PerfilAcessoController {

    private final PerfilAcessoService perfilService;

    public PerfilAcessoController(PerfilAcessoService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public List<PerfilAcesso> listarTodos() {
        return perfilService.listarTodos();
    }

    @PostMapping
    public PerfilAcesso salvar(@RequestBody PerfilAcesso perfil) {
        return perfilService.salvar(perfil);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        perfilService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
