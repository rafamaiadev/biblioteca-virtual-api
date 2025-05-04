package br.com.raphael.biblioteca_virtual_api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;
import br.com.raphael.biblioteca_virtual_api.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    private final PermissaoService permissaoService;

    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    @GetMapping
    public List<Permissao> listarTodos() {
        return permissaoService.listarTodos();
    }

    @PostMapping
    public Permissao salvar(@RequestBody Permissao permissao) {
        return permissaoService.salvar(permissao);
    }
}
