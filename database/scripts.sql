USE piardb;

INSERT INTO profiles VALUES
(1, "Colaborador"),
(2, "Aprovador"),
(3, "Administrador");

INSERT INTO company_units VALUES
(1, "Drogaria Catarinense"),
(2, "Drogaria Catarinense Manipulacao"),
(3, "Farmacia Preco Popular"),
(4, "Proformula"),
(5, "Farmagora");

INSERT INTO departments VALUES 
(1, "Recursos Humanos", 1),
(2, "Vendas", 1),
(3, "Administracao", 1),
(4, "Recursos Humanos", 2),
(5, "Vendas", 2);

INSERT INTO users VALUES
(1, 12345678901, 1, "Equipe PIARERS", "contato.piarers@gmail.com", "ED5A62730D49D6D343CB55BB3C4263C9", 0, 0, 3, 1);

INSERT INTO rewards VALUES 
(1, 100, "Squeeze"),
(2, 160, "Vale Compras"),
(3, 200, "Camiseta"),
(4, 300, "Jaqueta / Guarda-Sol"),
(5, 600, "Aparelho de Som JBL Flip");