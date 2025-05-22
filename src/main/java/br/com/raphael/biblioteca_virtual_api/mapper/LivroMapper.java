package br.com.raphael.biblioteca_virtual_api.mapper;

import org.mapstruct.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.LivroUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.LivroResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Livro;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class LivroMapper {

    @Mapping(target = "urlCapa", ignore = true)
    @Mapping(target = "urlPdf", ignore = true)
    public abstract LivroResponseDTO toResponseDTO(Livro entity);

    @AfterMapping
    void afterMapping(Livro entity, @MappingTarget LivroResponseDTO dto) {
        if (entity.getId() != null) {
            dto.setUrlCapa("/livros/" + entity.getId() + "/capa");
            dto.setUrlPdf("/livros/" + entity.getId() + "/pdf");
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhoArquivo", ignore = true)
    @Mapping(target = "caminhoCapa", ignore = true)
    public abstract Livro toEntity(LivroCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhoArquivo", ignore = true)
    @Mapping(target = "caminhoCapa", ignore = true)
    public abstract void updateEntityFromDTO(LivroUpdateDTO dto, @MappingTarget Livro entity);
} 