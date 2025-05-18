package br.com.raphael.biblioteca_virtual_api.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.repository.PerfilAcessoRepository;
import br.com.raphael.biblioteca_virtual_api.mapper.PerfilAcessoMapper;

@Service
public class PerfilAcessoService {
    
    private final PerfilAcessoRepository perfilAcessoRepository;
    private final PerfilAcessoMapper perfilAcessoMapper;

    public PerfilAcessoService(PerfilAcessoRepository perfilAcessoRepository, PerfilAcessoMapper perfilAcessoMapper) {
        this.perfilAcessoRepository = perfilAcessoRepository;
        this.perfilAcessoMapper = perfilAcessoMapper;
    }

    public List<PerfilAcesso> findAll() {
        return perfilAcessoRepository.findAll();
    }

    public PerfilAcesso findById(Long id) {
        return perfilAcessoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Perfil de acesso não encontrado"));
    }

    @Transactional
    public PerfilAcesso create(PerfilAcessoCreateDTO perfilAcessoDTO) {
        PerfilAcesso perfilAcesso = perfilAcessoMapper.toEntity(perfilAcessoDTO);
        return perfilAcessoRepository.save(perfilAcesso);
    }

    @Transactional
    public PerfilAcesso update(Long id, PerfilAcessoUpdateDTO perfilAcessoDTO) {
        PerfilAcesso perfilAcesso = findById(id);
        perfilAcessoMapper.updateEntityFromDTO(perfilAcessoDTO, perfilAcesso);
        return perfilAcessoRepository.save(perfilAcesso);
    }

    @Transactional
    public void delete(Long id) {
        PerfilAcesso perfilAcesso = findById(id);
        perfilAcessoRepository.delete(perfilAcesso);
    }
}
