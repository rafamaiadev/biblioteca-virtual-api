package br.com.raphael.biblioteca_virtual_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;


@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
    
}
