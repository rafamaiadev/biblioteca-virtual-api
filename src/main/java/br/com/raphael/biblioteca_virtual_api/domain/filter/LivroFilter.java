package br.com.raphael.biblioteca_virtual_api.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroFilter {
    private String search;
    private String titulo;
    private String autor;
    private String categoria;
} 