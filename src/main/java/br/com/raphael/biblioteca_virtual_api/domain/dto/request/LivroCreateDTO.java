package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LivroCreateDTO(
    
    @NotBlank(message =  "O título do livro deve ser informado!")
    String titulo,

    @NotBlank(message =  "O autor do livro deve ser informado!")
    String autor,

    @NotBlank(message =  "A categoria do livro deve ser informada!")
    String categoria) {}
