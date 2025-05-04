package br.com.raphael.biblioteca_virtual_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;
import br.com.raphael.biblioteca_virtual_api.repository.PerfilAcessoRepository;

@Service
public class PerfilAcessoService {
    
    @Autowired
    private PerfilAcessoRepository perfilAcessoRepository;

    public PerfilAcesso salvar(PerfilAcesso perfil) {
        return perfilAcessoRepository.save(perfil);
    }

    public List<PerfilAcesso> listarTodos() {
        return perfilAcessoRepository.findAll();
    }

    public void excluir(Long id) {
        perfilAcessoRepository.deleteById(id);
    }
}
