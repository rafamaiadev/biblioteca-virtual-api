package br.com.raphael.biblioteca_virtual_api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.raphael.biblioteca_virtual_api.exception.ArquivoException;

@Service
public class ArquivoStorageService {

    @Value("${app.arquivo.upload-dir}")
    private String uploadDir;

    public String savePdf(MultipartFile arquivo) {
        try {
            Path diretorio = Paths.get(uploadDir);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String novoNome = UUID.randomUUID().toString() + extensao;

            Path arquivoPath = diretorio.resolve(novoNome);
            Files.copy(arquivo.getInputStream(), arquivoPath);

            return arquivoPath.toString();
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar o arquivo", e);
        }
    }

    public byte[] getPdf(String path) {
        try {
            Path arquivoPath = Paths.get(path);
            return Files.readAllBytes(arquivoPath);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao recuperar o arquivo", e);
        }
    }

    public void deletePdf(String path) {
        try {
            Path arquivoPath = Paths.get(path);
            Files.deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao excluir o arquivo", e);
        }
    }

    public String saveImagem(MultipartFile imagem) {
        try {
            Path diretorio = Paths.get(uploadDir);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            String nomeOriginal = imagem.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String novoNome = UUID.randomUUID().toString() + extensao;

            Path imagemPath = diretorio.resolve(novoNome);
            Files.copy(imagem.getInputStream(), imagemPath);

            return imagemPath.toString();
        } catch (IOException e) {
            throw new ArquivoException("Erro ao salvar a imagem da capa", e);
        }
    }

    public void deleteImagem(String path) {
        try {
            Path imagemPath = Paths.get(path);
            Files.deleteIfExists(imagemPath);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao excluir a imagem da capa", e);
        }
    }

    public byte[] getImagem(String path) {
        try {
            Path imagemPath = Paths.get(path);
            return Files.readAllBytes(imagemPath);
        } catch (IOException e) {
            throw new ArquivoException("Erro ao recuperar a imagem da capa", e);
        }
    }
} 