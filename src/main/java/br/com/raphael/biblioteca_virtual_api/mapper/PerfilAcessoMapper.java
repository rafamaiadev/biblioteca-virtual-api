package br.com.raphael.biblioteca_virtual_api.mapper;

import org.mapstruct.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PerfilAcessoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.PerfilAcessoResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.PerfilAcesso;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PerfilAcessoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "permissoes", ignore = true)
    PerfilAcesso toEntity(PerfilAcessoCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissoes", ignore = true)
    void updateEntityFromDTO(PerfilAcessoUpdateDTO dto, @MappingTarget PerfilAcesso entity);

    PerfilAcessoResponseDTO toResponseDTO(PerfilAcesso entity);
} 