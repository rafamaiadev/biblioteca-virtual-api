package br.com.raphael.biblioteca_virtual_api.mapper;

import org.mapstruct.*;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.PermissaoUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.PermissaoResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Permissao;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PermissaoMapper {
    
    @Mapping(target = "id", ignore = true)
    Permissao toEntity(PermissaoCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "perfilAcesso", ignore = true)
    void updateEntityFromDTO(PermissaoUpdateDTO dto, @MappingTarget Permissao entity);

    @Mapping(target = "perfilDescricao", source = "perfilAcesso.descricao")
    PermissaoResponseDTO toResponseDTO(Permissao entity);
} 