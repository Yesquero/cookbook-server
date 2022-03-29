CREATE TYPE USER_ENTITY_ROLE AS ENUM ('ADMIN', 'MANAGER', 'USER');

CREATE CAST (character varying as USER_ENTITY_ROLE) WITH INOUT AS IMPLICIT;

CREATE TABLE user_entity
(
    uuid       uuid               NOT NULL,
    login      varchar(50) UNIQUE NOT NULL,
    password   varchar            NOT NULL,
    user_role  USER_ENTITY_ROLE   NOT NULL,
    first_name varchar(50),
    last_name  varchar(50),
    PRIMARY KEY (uuid)
);