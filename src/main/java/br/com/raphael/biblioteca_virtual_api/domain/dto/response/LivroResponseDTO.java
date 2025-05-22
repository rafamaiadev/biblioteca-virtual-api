package br.com.raphael.biblioteca_virtual_api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroResponseDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String categoria;
    private String urlCapa;
    private String urlPdf;
}