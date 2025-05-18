package br.com.raphael.biblioteca_virtual_api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.UsuarioResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Usuario;
import br.com.raphael.biblioteca_virtual_api.mapper.UsuarioMapper;
import br.com.raphael.biblioteca_virtual_api.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Rotas para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VISUALIZAR_USUARIO')")
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll()
            .stream()
            .map(usuarioMapper::toResponseDTO)
            .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = usuarioService.findByUsername(auth.getName());
        
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("VISUALIZAR_USUARIO")) ||
            (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("VISUALIZAR_PROPRIO_USUARIO")) && 
             usuarioLogado.getId().equals(id))) {
            
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuarioMapper.toResponseDTO(usuario));
        }
        
        return ResponseEntity.status(403).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CADASTRAR_USUARIO')")
    @Operation(summary = "Cadastra um novo usuário")
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioCreateDTO usuarioDTO) {
        Usuario usuario = usuarioService.save(usuarioDTO);
        return ResponseEntity
            .created(URI.create("/usuarios/" + usuario.getId()))
            .body(usuarioMapper.toResponseDTO(usuario));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDITAR_USUARIO')")
    @Operation(summary = "Atualiza um usuário")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateDTO usuarioDTO) {
        Usuario usuario = usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(usuarioMapper.toResponseDTO(usuario));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXCLUIR_USUARIO')")
    @Operation(summary = "Exclui um usuário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
