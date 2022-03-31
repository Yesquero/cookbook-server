CREATE TABLE ingredient
(
    uuid                     uuid,
    name                     varchar(50) NOT NULL,
    protein_percentage       numeric(6, 3),
    fats_percentage          numeric(6, 3),
    carbohydrates_percentage numeric(6, 3),
    PRIMARY KEY (uuid)
);