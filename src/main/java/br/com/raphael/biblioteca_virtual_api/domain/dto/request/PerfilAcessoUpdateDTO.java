package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

public record PerfilAcessoUpdateDTO(
    String descricao,
    Boolean enabled
) {}
