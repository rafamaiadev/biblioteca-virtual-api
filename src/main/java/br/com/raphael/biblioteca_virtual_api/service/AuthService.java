package br.com.raphael.biblioteca_virtual_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.raphael.biblioteca_virtual_api.config.security.JwtUtil;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.AuthRequest;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.AuthResponse;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthService(AuthenticationManager authenticationManager,JwtUtil jwtUtil,UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            final UserDetails userDetails = usuarioService.loadUserByUsername(request.username());
            final String token = jwtUtil.generateToken(userDetails);
            
            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
} 