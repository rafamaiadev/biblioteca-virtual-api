package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

public record UsuarioUpdateDTO(Long id,String nome,String email,Long perfilAcessoId) {

}
