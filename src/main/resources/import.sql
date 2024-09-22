INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');
INSERT INTO clients(name, lastname, email, create_at, photo) values ('Joan', 'Vasquez', 'j@g.com', '2023-03-03', '');

INSERT INTO products (name, price, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

INSERT INTO invoices (description, observation, client_id, create_at) VALUES ('Factura de equipos de oficina', null, 1, NOW());
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1,1,1);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(2,2,2);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1,2,2);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1,1,1);

INSERT INTO invoices (description, observation, client_id, create_at) VALUES ('Factura de equipos de oficina', 'Alguna nota', 1, NOW());
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(3, 2, 6);