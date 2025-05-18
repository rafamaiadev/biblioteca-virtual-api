package br.com.raphael.biblioteca_virtual_api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.PermissaoResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;
import br.com.raphael.biblioteca_virtual_api.mapper.PermissaoMapper;
import br.com.raphael.biblioteca_virtual_api.service.PermissaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/permissoes")
@Tag(name = "Permissões", description = "Rotas para gerenciamento de permissões")
public class PermissaoController {

    private final PermissaoService permissaoService;
    private final PermissaoMapper permissaoMapper;

    public PermissaoController(PermissaoService permissaoService, PermissaoMapper permissaoMapper) {
        this.permissaoService = permissaoService;
        this.permissaoMapper = permissaoMapper;
    }

    @GetMapping
    @Operation(summary = "Lista todas as permissões")
    public ResponseEntity<List<PermissaoResponseDTO>> findAll() {
        return ResponseEntity.ok(permissaoService.findAll()
            .stream()
            .map(permissaoMapper::toResponseDTO)
            .collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CADASTRAR_PERMISSAO')")
    @Operation(summary = "Cadastra uma nova permissão")
    public ResponseEntity<PermissaoResponseDTO> create(@RequestBody PermissaoCreateDTO permissaoDTO) {
        Permissao permissao = permissaoService.create(permissaoDTO);
        return ResponseEntity
            .created(URI.create("/permissoes/" + permissao.getId()))
            .body(permissaoMapper.toResponseDTO(permissao));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITAR_PERMISSAO')")
    @Operation(summary = "Atualiza uma permissão")
    public ResponseEntity<PermissaoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PermissaoUpdateDTO permissaoDTO) {
        Permissao permissao = permissaoService.update(id, permissaoDTO);
        return ResponseEntity.ok(permissaoMapper.toResponseDTO(permissao));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXCLUIR_PERMISSAO')")
    @Operation(summary = "Exclui uma permissão")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
