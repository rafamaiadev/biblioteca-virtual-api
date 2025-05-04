package br.com.raphael.biblioteca_virtual_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroCreateDTO(
    
    @NotBlank(message =  "O título do livro deve ser informado!")
    String titulo,

    @NotBlank(message =  "O autor do livro deve ser informado!")
    String autor,

    @NotBlank(message =  "A categoria do livro deve ser informada!")
    String categoria,

    @NotNull(message = "O arquivo não pode ser nulo!")
    byte[] arquivoPdf) {

}
