package br.com.raphael.biblioteca_virtual_api.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário deve ser informado.")
    @Size(max = 100)
    private String username;

    @NotBlank(message = "O e-mail deve ser informado.")
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha deve ser informada.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_acesso_id", nullable = false)
    private PerfilAcesso perfilAcesso;
    
    @ManyToMany
    @JoinTable(
        name = "usuario_livro",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private Set<Livro> livros = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(new SimpleGrantedAuthority(perfilAcesso.getDescricao()));
        
        if (perfilAcesso.getPermissoes() != null) {
            authorities.addAll(
                perfilAcesso.getPermissoes().stream()
                    .map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao()))
                    .collect(Collectors.toList())
            );
        }
        return authorities;
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
