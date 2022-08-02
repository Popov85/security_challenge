CREATE SCHEMA security_challenge;

CREATE TABLE company(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(500) NOT NULL
);

ALTER TABLE company
    ADD CONSTRAINT COMPANY_UNIQUE UNIQUE (name);

CREATE TABLE users(
    id IDENTITY PRIMARY KEY,
    company_id BIGINT,
    name          VARCHAR(500) NOT NULL,
    date_of_birth DATE NOT NULL,
    active        BOOLEAN DEFAULT true
);

ALTER TABLE users
    ADD CONSTRAINT USERSNAME_UNIQUE UNIQUE (name);

CREATE TABLE role(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(500) NOT NULL
);

ALTER TABLE role
    ADD CONSTRAINT ROLE_UNIQUE UNIQUE (name);

CREATE TABLE users_role(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_role
    ADD FOREIGN KEY (user_id)
        REFERENCES users (id);

ALTER TABLE users_role
    ADD FOREIGN KEY (role_id)
        REFERENCES role (id);

CREATE TABLE credentials (
    user_id  integer NOT NULL
        CONSTRAINT credentials_pk
            PRIMARY KEY
        CONSTRAINT credentials_user_id_fk
            REFERENCES users,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

ALTER TABLE credentials
    ADD CONSTRAINT USERNAME_UNIQUE UNIQUE (username);