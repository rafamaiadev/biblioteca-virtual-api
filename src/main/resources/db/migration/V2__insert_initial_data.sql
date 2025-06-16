INSERT INTO perfil_acesso (descricao, enabled) VALUES
    ('ROLE_ADMIN', true),
    ('ROLE_ALUNO', true);

INSERT INTO usuario (username, nome, email, password, perfil_acesso_id) VALUES
    ('admin', 'Raphael','admin@biblioteca.com', '$2a$12$3F5Kopl3B2dEIApNvyGNu.PEimiyFSbAj7FBvV0KaV6148geEU7vW', 1);
    