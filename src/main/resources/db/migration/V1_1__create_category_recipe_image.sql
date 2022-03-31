CREATE TABLE recipe_category
(
    uuid uuid,
    name varchar(50) UNIQUE NOT NULL,
    PRIMARY KEY (uuid)
);

CREATE TABLE image_catalog
(
    uuid    uuid,
    name    varchar(50),
    content bytea NOT NULL,
    PRIMARY KEY (uuid)
);

CREATE TYPE RECIPE_STATUS AS ENUM ('APPROVED', 'PENDING');

CREATE CAST (character varying as RECIPE_STATUS) WITH INOUT AS IMPLICIT;

CREATE TABLE recipe
(
    uuid               uuid,
    fk_recipe_category uuid        NOT NULL,
    fk_image           uuid,
    name               varchar(50) NOT NULL,
    complexity         integer     NOT NULL,
    description        varchar(500),
    status             RECIPE_STATUS,
    PRIMARY KEY (uuid)
);

ALTER TABLE recipe
    ADD CONSTRAINT fk_category FOREIGN KEY (fk_recipe_category) REFERENCES recipe_category (uuid);
ALTER TABLE recipe
    ADD CONSTRAINT fk_image FOREIGN KEY (fk_image) REFERENCES image_catalog (uuid);
