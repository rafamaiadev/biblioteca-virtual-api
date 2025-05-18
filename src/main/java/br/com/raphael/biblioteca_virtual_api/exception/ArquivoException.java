package br.com.raphael.biblioteca_virtual_api.exception;

public class ArquivoException extends RuntimeException {
    
    public ArquivoException(String message) {
        super(message);
    }

    public ArquivoException(String message, Throwable cause) {
        super(message, cause);
    }
} 