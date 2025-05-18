package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDTO(
    
    @NotBlank(message =  "O nome do usuário deve ser informado!")
    String username,
    
    @Email(message = "O email do usuário deve ser informado!")
    String email,
    
    @NotBlank(message =  "A senha do usuário deve ser informada!")
    String password,
    
    @NotNull(message =  "O perfil do usuário deve ser informado!")
    Long perfilAcessoId) {
    
}
