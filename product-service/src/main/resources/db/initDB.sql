
DROP TABLE IF EXISTS PRODUCT_COST;
DROP TABLE IF EXISTS PRODUCT;


DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1000;


CREATE TABLE PRODUCT
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    picture     VARCHAR(100),
    frozen      BOOLEAN             DEFAULT FALSE
);

CREATE UNIQUE INDEX product_unique_name_description_index ON PRODUCT (name, description);

CREATE TABLE PRODUCT_COST
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    product_id INTEGER      NOT NULL,
    property   VARCHAR(100) NOT NULL,
    price      NUMERIC(5, 2)       DEFAULT 0,
    discount   INTEGER             DEFAULT 0,
    frozen     BOOLEAN             DEFAULT FALSE,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX product_unique_product_id_property_index ON PRODUCT_COST (product_id, property);



