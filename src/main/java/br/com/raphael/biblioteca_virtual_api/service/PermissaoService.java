package br.com.raphael.biblioteca_virtual_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;
import br.com.raphael.biblioteca_virtual_api.repository.PermissaoRepository;

@Service
public class PermissaoService {
    
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    public List<Permissao> listarTodos() {
        return permissaoRepository.findAll();
    }
}
