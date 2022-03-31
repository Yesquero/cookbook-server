CREATE TABLE step_ingredient
(
    fk_recipe_step uuid,
    fk_ingredient uuid,
    amount numeric (10, 3) NOT NULL ,
    PRIMARY KEY (fk_ingredient, fk_recipe_step)
);

ALTER TABLE step_ingredient
    ADD CONSTRAINT fk_recipe_step FOREIGN KEY (fk_recipe_step) REFERENCES recipe_step (uuid);

ALTER TABLE step_ingredient
    ADD CONSTRAINT fk_ingredient FOREIGN KEY (fk_ingredient) REFERENCES ingredient (uuid);
