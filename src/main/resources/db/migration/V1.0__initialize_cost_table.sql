CREATE TABLE COST
(
    id          SERIAL PRIMARY KEY,
    date        DATE,
    description VARCHAR(256),
    category    VARCHAR(128),
    subcategory VARCHAR(128),
    comment     VARCHAR(512),
    amount      DECIMAL
)