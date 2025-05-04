package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissaoCreateDTO(

    @NotBlank(message = "A descrição da permissão deve ser informada!")
    String descricao,

    @NotBlank(message = "O perfil de acesso da permissão deve ser informado!")
    Long perfilAcessoId
) {}
