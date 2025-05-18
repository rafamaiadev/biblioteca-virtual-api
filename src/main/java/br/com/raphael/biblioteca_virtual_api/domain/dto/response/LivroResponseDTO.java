package br.com.raphael.biblioteca_virtual_api.domain.dto.response;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String autor,
    String categoria
) {}
