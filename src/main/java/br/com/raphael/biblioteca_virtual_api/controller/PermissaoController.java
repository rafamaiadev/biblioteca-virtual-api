package br.com.raphael.biblioteca_virtual_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;
import br.com.raphael.biblioteca_virtual_api.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping
    public List<Permissao> listarTodos() {
        return permissaoService.listarTodos();
    }

    @PostMapping
    public Permissao salvar(@RequestBody Permissao permissao) {
        return permissaoService.salvar(permissao);
    }
}
