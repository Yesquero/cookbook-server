CREATE TYPE USER_ENTITY_ROLE AS ENUM ('ADMIN', 'MANAGER', 'USER');

CREATE CAST (character varying as USER_ENTITY_ROLE) WITH INOUT AS IMPLICIT;

CREATE TYPE USER_STATUS AS ENUM ('ACTIVE', 'INACTIVE');

CREATE CAST (character varying as USER_STATUS) WITH INOUT AS IMPLICIT;

CREATE TABLE user_entity
(
    uuid       uuid                NOT NULL,
    login      varchar(100) UNIQUE NOT NULL,
    nickname   varchar(50) UNIQUE  NOT NULL,
    password   varchar             NOT NULL,
    status     USER_STATUS         NOT NULL,
    user_role  USER_ENTITY_ROLE    NOT NULL,
    first_name varchar(50),
    last_name  varchar(50),
    PRIMARY KEY (uuid)
);