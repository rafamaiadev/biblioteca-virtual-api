package br.com.raphael.biblioteca_virtual_api.domain.dto.response;

public record PerfilAcessoResponseDTO(
    Long id,
    String descricao,
    Boolean enabled
) {}
