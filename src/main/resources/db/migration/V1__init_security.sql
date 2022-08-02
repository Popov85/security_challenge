CREATE SCHEMA security_challenge;

CREATE TABLE users(
    id IDENTITY PRIMARY KEY,
    name          VARCHAR(500),
    date_of_birth DATE,
    active        BOOLEAN DEFAULT true
);

ALTER TABLE users
    ADD CONSTRAINT USERNAME_UNIQUE UNIQUE (name);

CREATE TABLE role(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(500)
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
    username varchar(255),
    password varchar(255)
);