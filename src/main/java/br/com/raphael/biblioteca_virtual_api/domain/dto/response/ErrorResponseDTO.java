package br.com.raphael.biblioteca_virtual_api.domain.dto.response;

import java.time.LocalDateTime;

public record ErrorResponseDTO(Integer status,String message,LocalDateTime timestamp
) {} 