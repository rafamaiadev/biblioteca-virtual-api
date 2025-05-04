package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

public record LivroUpdateDTO(
    Long id,
    String titulo,
    String autor,
    String categoria,
    byte[] arquivoPdf,
    Boolean ativo
) {}
