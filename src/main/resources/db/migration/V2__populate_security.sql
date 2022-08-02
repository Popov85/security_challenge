INSERT INTO company (name) VALUES ('IBM');
INSERT INTO company (name) VALUES ('Samsung');

INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_API');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (name, company_id, date_of_birth, active) VALUES ('Mr. White', 1, '1982-09-01', true);
INSERT INTO users (name, company_id, date_of_birth, active) VALUES ('Mr. Black', 2, '1989-04-11', true);

INSERT INTO users_role (user_id, role_id) VALUES (1,1);
INSERT INTO users_role (user_id, role_id) VALUES (1,2);
INSERT INTO users_role (user_id, role_id) VALUES (2,3);

INSERT INTO credentials (user_id, username, password) VALUES (1, 'white', '$2a$12$bc.MlRuqenoSfc99Hrsaqea1CBM/fYVpI3th/K.pIlrPa3bj6l8p2');
INSERT INTO credentials (user_id, username, password) VALUES (2, 'black', '$2a$12$.7rfGw0EQWNBMHZ9w8WzIuDWdJ8ZeKWvCHT9i4p6oO1ErtDkUj9HW');