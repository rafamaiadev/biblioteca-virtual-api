package br.com.raphael.biblioteca_virtual_api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.PerfilAcessoResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.mapper.PerfilAcessoMapper;
import br.com.raphael.biblioteca_virtual_api.service.PerfilAcessoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/perfis-acesso")
@Tag(name = "Perfis de Acesso", description = "Rotas para gerenciamento de perfis de acesso")
public class PerfilAcessoController {

    private final PerfilAcessoService perfilAcessoService;
    private final PerfilAcessoMapper perfilAcessoMapper;

    public PerfilAcessoController(PerfilAcessoService perfilAcessoService, PerfilAcessoMapper perfilAcessoMapper) {
        this.perfilAcessoService = perfilAcessoService;
        this.perfilAcessoMapper = perfilAcessoMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VISUALIZAR_PERFIL')")
    @Operation(summary = "Lista todos os perfis de acesso")
    public ResponseEntity<List<PerfilAcessoResponseDTO>> findAll() {
        return ResponseEntity.ok(perfilAcessoService.findAll()
            .stream()
            .map(perfilAcessoMapper::toResponseDTO)
            .collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CADASTRAR_PERFIL')")
    @Operation(summary = "Cadastra um novo perfil de acesso")
    public ResponseEntity<PerfilAcessoResponseDTO> create(@RequestBody PerfilAcessoCreateDTO perfilAcessoDTO) {
        PerfilAcesso perfilAcesso = perfilAcessoService.create(perfilAcessoDTO);
        return ResponseEntity
            .created(URI.create("/perfis-acesso/" + perfilAcesso.getId()))
            .body(perfilAcessoMapper.toResponseDTO(perfilAcesso));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITAR_PERFIL')")
    @Operation(summary = "Atualiza um perfil de acesso")
    public ResponseEntity<PerfilAcessoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PerfilAcessoUpdateDTO perfilAcessoDTO) {
        PerfilAcesso perfilAcesso = perfilAcessoService.update(id, perfilAcessoDTO);
        return ResponseEntity.ok(perfilAcessoMapper.toResponseDTO(perfilAcesso));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXCLUIR_PERFIL')")
    @Operation(summary = "Exclui um perfil de acesso")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        perfilAcessoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
