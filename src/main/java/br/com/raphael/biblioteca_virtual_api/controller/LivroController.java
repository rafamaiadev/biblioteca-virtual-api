package br.com.raphael.biblioteca_virtual_api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.LivroResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.filter.LivroFilter;
import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;
import br.com.raphael.biblioteca_virtual_api.mapper.LivroMapper;
import br.com.raphael.biblioteca_virtual_api.service.LivroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Rotas para gerenciamento de livros")
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @GetMapping
    @Operation(summary = "Lista todos os livros baseado em algum filtro")
    public ResponseEntity<List<LivroResponseDTO>> findAll(LivroFilter filter) {
        return ResponseEntity.ok(livroService.findAll(filter)
            .stream()
            .map(livroMapper::toResponseDTO)
            .collect(Collectors.toList()));
    }

    @GetMapping("/pageable")
    @Operation(summary = "Lista todos os livros de forma paginada baseado em algum filtro")
    public ResponseEntity<Page<LivroResponseDTO>> findAll(LivroFilter filter, Pageable pageable) {
        return ResponseEntity.ok(livroService.findAll(filter, pageable)
            .map(livroMapper::toResponseDTO));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('CADASTRAR_LIVRO')")
    @Operation(summary = "Cadastra um novo livro com arquivo PDF")
    public ResponseEntity<LivroResponseDTO> create(
            @RequestPart(value = "livroDTO") LivroCreateDTO livroDTO,
            @RequestPart(value = "pdf") MultipartFile pdf) throws IOException {
        Livro livro = livroService.create(livroDTO, pdf);
        return ResponseEntity
            .created(URI.create("/livros/" + livro.getId()))
            .body(livroMapper.toResponseDTO(livro));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('EDITAR_LIVRO')")
    @Operation(summary = "Atualiza um livro")
    public ResponseEntity<LivroResponseDTO> update(
            @PathVariable Long id,
            @RequestPart(value = "livroDTO") LivroUpdateDTO livroDTO,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf) throws IOException {
        Livro livro = livroService.update(id, livroDTO, pdf);
        return ResponseEntity.ok(livroMapper.toResponseDTO(livro));
    }

    @GetMapping("/{id}/pdf")
    @Operation(summary = "Visualiza o PDF do livro")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long id) {
        Livro livro = livroService.findById(id);
        byte[] pdfBytes = livroService.getPdf(id);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + livro.getTitulo() + ".pdf\"")
            .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, proxy-revalidate")
            .header(HttpHeaders.PRAGMA, "no-cache")
            .header(HttpHeaders.EXPIRES, "0")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXCLUIR_LIVRO')")
    @Operation(summary = "Exclui um livro")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}