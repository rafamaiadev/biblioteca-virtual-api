package br.com.raphael.biblioteca_virtual_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.mapper.PermissaoMapper;
import br.com.raphael.biblioteca_virtual_api.repository.PermissaoRepository;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final PerfilAcessoService perfilAcessoService;
    private final PermissaoMapper permissaoMapper;

    public PermissaoService(PermissaoRepository permissaoRepository, PerfilAcessoService perfilAcessoService, PermissaoMapper permissaoMapper) {
        this.permissaoRepository = permissaoRepository;
        this.perfilAcessoService = perfilAcessoService;
        this.permissaoMapper = permissaoMapper;
    }

    public Permissao create(PermissaoCreateDTO dto) {
        PerfilAcesso perfil = perfilAcessoService.findById(dto.perfilAcessoId());
        Permissao permissao = permissaoMapper.toEntity(dto);
        permissao.setPerfilAcesso(perfil);
        return permissaoRepository.save(permissao);
    }

    public Permissao update(Long id, PermissaoUpdateDTO dto) {
        Permissao permissao = permissaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));
        permissaoMapper.updateEntityFromDTO(dto, permissao);
        return permissaoRepository.save(permissao);
    }

    public List<Permissao> findAll() {
        return permissaoRepository.findAll();
    }

    public Permissao findById(Long id) {
        return permissaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));
    }

    public void delete(Long id) {
        permissaoRepository.deleteById(id);
    }
}
