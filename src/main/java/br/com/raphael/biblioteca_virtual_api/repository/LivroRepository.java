package br.com.raphael.biblioteca_virtual_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {
    
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
}
