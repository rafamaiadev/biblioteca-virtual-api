CREATE TABLE perfil_acesso (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE permissao (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    perfil_acesso_id BIGINT NOT NULL,
    FOREIGN KEY (perfil_acesso_id) REFERENCES perfil_acesso(id)
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    perfil_acesso_id BIGINT NOT NULL,
    FOREIGN KEY (perfil_acesso_id) REFERENCES perfil_acesso(id)
);

CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    categoria VARCHAR(255),
    caminho_arquivo VARCHAR(255) NOT NULL,
    caminho_capa VARCHAR(255) NOT NULL
);

CREATE TABLE usuario_livro (
    usuario_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, livro_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);  