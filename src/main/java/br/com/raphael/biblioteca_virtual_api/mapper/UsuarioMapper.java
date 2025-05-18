package br.com.raphael.biblioteca_virtual_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioCreateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.request.UsuarioUpdateDTO;
import br.com.raphael.biblioteca_virtual_api.domain.dto.response.UsuarioResponseDTO;
import br.com.raphael.biblioteca_virtual_api.domain.model.Usuario;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "perfilAcesso", ignore = true)
    Usuario toEntity(UsuarioCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "perfilAcesso", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);

    @Mapping(target = "perfilAcessoDescricao", source = "perfilAcesso.descricao")
    UsuarioResponseDTO toResponseDTO(Usuario entity);
} 