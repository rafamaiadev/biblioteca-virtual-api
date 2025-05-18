package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.Email;

public record UsuarioUpdateDTO(
    String username,
    
    @Email
    String email,
    
    Long perfilAcessoId) {

}
