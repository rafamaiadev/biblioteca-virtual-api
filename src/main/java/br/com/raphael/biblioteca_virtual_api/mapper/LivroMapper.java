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
public interface LivroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhoArquivo", ignore = true)
    Livro toEntity(LivroCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caminhoArquivo", ignore = true)
    void updateEntityFromDTO(LivroUpdateDTO dto, @MappingTarget Livro entity);

    LivroResponseDTO toResponseDTO(Livro entity);
} 