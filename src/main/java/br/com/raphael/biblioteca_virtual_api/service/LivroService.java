package br.com.raphael.biblioteca_virtual_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.filter.LivroFilter;
import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;
import br.com.raphael.biblioteca_virtual_api.exception.ArquivoException;
import br.com.raphael.biblioteca_virtual_api.mapper.LivroMapper;
import br.com.raphael.biblioteca_virtual_api.repository.LivroRepository;
import br.com.raphael.biblioteca_virtual_api.repository.specification.LivroSpecification;

@Service
public class LivroService {
    
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final ArquivoStorageService arquivoStorageService;

    @Value("${app.upload.max-file-size:10485760}")
    private long maxFileSize;

    public LivroService(LivroRepository livroRepository, LivroMapper livroMapper, ArquivoStorageService arquivoStorageService) {
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
        this.arquivoStorageService = arquivoStorageService;
    }

    @Transactional(readOnly = true)
    public List<Livro> findAll(LivroFilter filter) {
        return livroRepository.findAll(LivroSpecification.filterBy(filter));
    }

    @Transactional(readOnly = true)
    public Page<Livro> findAll(LivroFilter filter, Pageable pageable) {
        return livroRepository.findAll(LivroSpecification.filterBy(filter), pageable);
    }

    public Livro findById(Long id) {
        return livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado."));
    }

    @Transactional
    public void delete(Long id) {
        Livro livro = findById(id);
        arquivoStorageService.deletePdf(livro.getCaminhoArquivo());
        livroRepository.delete(livro);
    }

    @Transactional
    public Livro create(LivroCreateDTO dto, MultipartFile arquivo, MultipartFile capa) throws IOException {
        validarArquivo(arquivo);
        validarArquivoImagem(capa);

        String caminhoArquivo = arquivoStorageService.savePdf(arquivo);
        String caminhoCapa = arquivoStorageService.saveImagem(capa);

        Livro livro = livroMapper.toEntity(dto);
        livro.setCaminhoArquivo(caminhoArquivo);
        livro.setCaminhoCapa(caminhoCapa);

        return livroRepository.save(livro);
    }

    @Transactional
    public Livro update(Long id, LivroUpdateDTO dto, MultipartFile novoArquivo, MultipartFile novaCapa) throws IOException {
        Livro livro = findById(id);

        if (novoArquivo != null && !novoArquivo.isEmpty()) {
            validarArquivo(novoArquivo);
            arquivoStorageService.deletePdf(livro.getCaminhoArquivo());
            String novoCaminhoArquivo = arquivoStorageService.savePdf(novoArquivo);
            livro.setCaminhoArquivo(novoCaminhoArquivo);
        }

        if (novaCapa != null && !novaCapa.isEmpty()) {
            validarArquivoImagem(novaCapa);
            arquivoStorageService.deleteImagem(livro.getCaminhoCapa());
            String novoCaminhoCapa = arquivoStorageService.saveImagem(novaCapa);
            livro.setCaminhoCapa(novoCaminhoCapa);
        }

        livroMapper.updateEntityFromDTO(dto, livro);
        return livroRepository.save(livro);
    }

    public byte[] getPdf(Long id) {
        Livro livro = findById(id);
        return arquivoStorageService.getPdf(livro.getCaminhoArquivo());
    }

    public byte[] getCapa(Long id) {
        Livro livro = findById(id);
        return arquivoStorageService.getImagem(livro.getCaminhoCapa());
    }

    private void validarArquivo(MultipartFile arquivo) throws ArquivoException {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new ArquivoException("O arquivo PDF é obrigatório");
        }

        if (!arquivo.getContentType().equals("application/pdf")) {
            throw new ArquivoException("Apenas arquivos PDF são permitidos");
        }

        if (arquivo.getSize() > maxFileSize) {
            throw new ArquivoException("O arquivo excede o tamanho máximo permitido de " + (maxFileSize / 1024 / 1024) + "MB");
        }
    }

    private void validarArquivoImagem(MultipartFile imagem) throws ArquivoException {
        if (imagem == null || imagem.isEmpty()) {
            throw new ArquivoException("A imagem da capa é obrigatória");
        }
        String contentType = imagem.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new ArquivoException("Apenas imagens JPG ou PNG são permitidas para a capa");
        }
        if (imagem.getSize() > maxFileSize) {
            throw new ArquivoException("A imagem excede o tamanho máximo permitido de " + (maxFileSize / 1024 / 1024) + "MB");
        }
    }
}
