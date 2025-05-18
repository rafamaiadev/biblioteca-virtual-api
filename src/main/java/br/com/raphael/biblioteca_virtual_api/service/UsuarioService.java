package br.com.raphael.biblioteca_virtual_api.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.domain.model.Usuario;
import br.com.raphael.biblioteca_virtual_api.mapper.UsuarioMapper;
import br.com.raphael.biblioteca_virtual_api.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    private final PerfilAcessoService perfilAcessoService;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,PerfilAcessoService perfilAcessoService,UsuarioMapper usuarioMapper,PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilAcessoService = perfilAcessoService;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario save(UsuarioCreateDTO dto) {
        PerfilAcesso perfilAcesso = perfilAcessoService.findById(dto.perfilAcessoId());

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setPerfilAcesso(perfilAcesso);
        
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = findById(id);
        
        if (dto.perfilAcessoId() != null) {
            PerfilAcesso perfilAcesso = perfilAcessoService.findById(dto.perfilAcessoId());
            usuario.setPerfilAcesso(perfilAcesso);
        }
        
        usuarioMapper.updateEntityFromDTO(dto, usuario);
        
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
