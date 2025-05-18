package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

public record LivroUpdateDTO(
    String titulo,
    String autor,
    String categoria
) {}