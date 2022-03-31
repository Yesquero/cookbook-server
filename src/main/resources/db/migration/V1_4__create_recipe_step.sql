CREATE TABLE recipe_step
(
    uuid        uuid,
    fk_recipe   uuid    NOT NULL,
    fk_image    uuid,
    step_number integer NOT NULL,
    description varchar NOT NULL,
    duration    BIGINT,
    PRIMARY KEY (uuid)
);

ALTER TABLE recipe_step
    ADD CONSTRAINT fk_recipe FOREIGN KEY (fk_recipe) REFERENCES recipe (uuid);

ALTER TABLE recipe_step
    ADD CONSTRAINT fk_image FOREIGN KEY (fk_image) REFERENCES image_catalog (uuid);
