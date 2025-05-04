package br.com.raphael.biblioteca_virtual_api.domain.dto.response;

public record PermissaoResponseDTO(
    Long id,
    String descricao,
    String perfilDescricao
) {}
