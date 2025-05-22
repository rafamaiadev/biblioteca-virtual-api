package br.com.raphael.biblioteca_virtual_api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O título do livro deve ser informado.")
    @Size(max = 150)
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "O nome do autor deve ser informado.")
    @Size(max = 100)
    @Column(nullable = false)
    private String autor;

    @Size(max = 255)
    private String categoria;

    @Column(name = "caminho_arquivo", nullable = false)
    private String caminhoArquivo;

    @Column(name = "caminho_capa", nullable = false)
    private String caminhoCapa;
}
