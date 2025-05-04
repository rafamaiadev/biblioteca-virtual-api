package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PerfilAcessoCreateDTO(
    
    @NotBlank(message = "A descriação do perfil de acesso deve ser informada!")
    String descricao
) {}
