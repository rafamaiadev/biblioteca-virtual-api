package br.com.raphael.biblioteca_virtual_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.raphael.biblioteca_virtual_api.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query("SELECT DISTINCT u FROM Usuario u " +
           "LEFT JOIN FETCH u.perfilAcesso p " +
           "LEFT JOIN FETCH p.permissoes " +
           "WHERE u.username = :username")
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT DISTINCT u FROM Usuario u " +
           "LEFT JOIN FETCH u.perfilAcesso p " +
           "LEFT JOIN FETCH p.permissoes")
    List<Usuario> findAll();
}
