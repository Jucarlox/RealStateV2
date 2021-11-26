




INSERT INTO USER (id,nombre,apellidos,direccion,email,telefono,avatar,password,roles) VALUES ('45bcc33a4e9111ec81d30242ac130006','Jaime','Jimenez','Calle manzana','jaime@gmail.com','654328796','ccc.jpg','$2a$12$TmMjpqHaHkPCAPWUBckD0.WNqOHP13h.8YU9YKSWP6eOVgtbwN07q','ADMIN');
INSERT INTO USER (id,nombre,apellidos,direccion,email,telefono,avatar,password,roles) VALUES ('45bcc33a4e9111ec81d30242ac130003','Juan Carlos','Ardana Murillo','Calle Banana','jucarloxx@gmail.com','654328796','ccc.jpg','$2a$12$kv2o7pTNeHxkVhvKJ9xz7uPORycto6pMpkNpbeSF8kJflzuv2.ae2','PROPIETARIO');
INSERT INTO USER (id,nombre,apellidos,direccion,email,telefono,avatar,password,roles) VALUES ('45bcc33a4e9111ec81d30242ac130007','Pepe','Garcia','Calle pera','pepe@gmail.com','654328796','ccc.jpg','$2a$12$eZmG6n0zkR7mxroVMQqgSed9bjtBIIf1fW7pUSHh6eiGIoR9akry.','PROPIETARIO');
INSERT INTO USER (id,nombre,apellidos,direccion,email,telefono,avatar,password,roles) VALUES ('45bcc33a4e9111ec81d30242ac130009','Moi','Perez','Calle patata','moi@gmail.com','654328796','ccc.jpg','$2a$12$vvcVaYrxL9mTCh2ZaD/L7OcuKXXLqN2.X7IvOz5XPytYr9PGPql5.','PROPIETARIO');

insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Mosciski-Volkman', 'cdom@feedburner.com', '222-440-9350');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Inmobiliarias paco', 'cdome@feedburner.com', '222-440-9350');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Inmobiliarias paqui', 'cdomenici3@burner.com', '222-440-9350');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Homenick LLC', 'sinde1@plala.or.jp', '327-465-6976');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Denesik, Koch and Cassin', 'gbrailey2@senate.gov', '798-528-6921');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Mosciski-Volkman', 'cdomenici3@feedburner.com', '222-440-9350');
insert into INMOBILIARIA (id, nombre, email, telefono) values (NEXTVAL('hibernate_sequence'), 'Boehm Inc', 'awinchurst3@bing.com', '510-109-9616');

insert into Vivienda (id, avatar, codigo_postal, descripcion, direccion, latlng, metros_cuadrados, num_banios, num_habitaciones, poblacion, precio, provincia, tiene_ascensor, tiene_garaje, tiene_piscina, tipo_vivienda, titulo, inmobiliaria_id, user_id) values (NEXTVAL('hibernate_sequence'), 'https://i.pinimg.com/originals/12/de/c0/12dec07702f57144f57a6e7ecc98da02.jpg', 41010, 'Vivienda a estrenar después de una reforma integral en la mejor zona del casco histórico. En pleno barrio del arenal.Lo convierte es una magnifica opción tanto para vivir como para invertir se encuentra en una primera planta y es exterior con dos balcones a la calle.', '868 Talmadge Court', '-58.7226377,-34.4765431', 290, 3, 1, 'Espartinas', 504754, 'Sevilla', false, true, false, 'ALQUILER', 'Arenal', 1, '45bcc33a4e9111ec81d30242ac130003');
insert into Vivienda (id, avatar, codigo_postal, descripcion, direccion, latlng, metros_cuadrados, num_banios, num_habitaciones, poblacion, precio, provincia, tiene_ascensor, tiene_garaje, tiene_piscina, tipo_vivienda, titulo, inmobiliaria_id, user_id) values (NEXTVAL('hibernate_sequence'), 'https://i.pinimg.com/originals/12/de/c0/12dec07702f57144f57a6e7ecc98da02.jpg', 41010, 'Vivienda a estrenar después de una reforma integral en la mejor zona del casco histórico. En pleno barrio del arenal.Lo convierte es una magnifica opción tanto para vivir como para invertir se encuentra en una primera planta y es exterior con dos balcones a la calle.', '868 Talmadge Court', '-58.7226377,-34.4765431', 290, 3, 1, 'Espartinas', 504754, 'Sevilla', false, true, false, 'VENTA', 'Arenal', 1, '45bcc33a4e9111ec81d30242ac130007');

insert into Vivienda (id, avatar, codigo_postal, descripcion, direccion, latlng, metros_cuadrados, num_banios, num_habitaciones, poblacion, precio, provincia, tiene_ascensor, tiene_garaje, tiene_piscina, tipo_vivienda, titulo, inmobiliaria_id, user_id) values (NEXTVAL('hibernate_sequence'), 'https://i.pinimg.com/originals/12/de/c0/12dec07702f57144f57a6e7ecc98da02.jpg', 41010, 'Vivienda a estrenar después de una reforma integral en la mejor zona del casco histórico. En pleno barrio del arenal.Lo convierte es una magnifica opción tanto para vivir como para invertir se encuentra en una primera planta y es exterior con dos balcones a la calle.', '868 Talmadge Court', '-58.7226377,-34.4765431', 290, 3, 1, 'Espartinas', 504754, 'Sevilla', false, true, false, 'VENTA', 'Arenal', 1, '45bcc33a4e9111ec81d30242ac130007');

INSERT INTO USER (id,nombre,apellidos,direccion,email,telefono,avatar,password,roles, inmobiliaria_id) VALUES ('45bcc33a4e9111ec81d30242ac130004','Alfonso','Benito','Calle platano','alfonso@gmail.com','654328796','ccc.jpg','$2a$12$w5aaYY2tc/83revTG0/beuYAOwL8tyMljd4E/vj9RtKf7TNP6b0Qq','GESTOR',1);

