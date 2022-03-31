CREATE TABLE favorite_recipe
(
    fk_recipe uuid,
    fk_user   uuid,
    PRIMARY KEY (fk_recipe, fk_user)
);

ALTER TABLE favorite_recipe
    ADD CONSTRAINT fk_user FOREIGN KEY (fk_user) REFERENCES user_entity (uuid);
ALTER TABLE favorite_recipe
    ADD CONSTRAINT fk_recipe FOREIGN KEY (fk_recipe) REFERENCES recipe (uuid);

CREATE TABLE recipe_rating
(
    fk_recipe uuid,
    fk_user   uuid,
    rating    integer NOT NULL ,
    PRIMARY KEY (fk_recipe, fk_user)
);

ALTER TABLE recipe_rating
    ADD CONSTRAINT fk_user FOREIGN KEY (fk_user) REFERENCES user_entity (uuid);
ALTER TABLE recipe_rating
    ADD CONSTRAINT fk_recipe FOREIGN KEY (fk_recipe) REFERENCES recipe (uuid);
