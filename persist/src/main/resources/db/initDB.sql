DROP TABLE IF EXISTS ORDER_PRODUCT_COST;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS PRODUCT_COST;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS ADDRESS;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 997;

CREATE TABLE ADDRESS
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    street  VARCHAR(255),
    zip     VARCHAR(10),
    city    VARCHAR(255),
    country VARCHAR(255)
);


CREATE TABLE PERSON
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name VARCHAR(40),
    last_name  VARCHAR(40),
    email      VARCHAR(50),
    keycloak   VARCHAR(255) NOT NULL,
    address_id INTEGER NOT NULL,
    FOREIGN KEY (address_id) REFERENCES ADDRESS (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX person_unique_keycloak_index ON PERSON (keycloak);

CREATE TABLE PRODUCT
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    picture     VARCHAR(100)
);

CREATE UNIQUE INDEX product_unique_name_description_index ON PRODUCT (name, description);

CREATE TABLE PRODUCT_COST
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    product_id INTEGER      NOT NULL,
    property   VARCHAR(100) NOT NULL,
    price      NUMERIC(5, 2)       DEFAULT 0,
    discount   INTEGER             DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX product_unique_product_id_property_index ON PRODUCT_COST (product_id, property);

CREATE TABLE ORDERS
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    person_id INTEGER NOT NULL,
    created   TIMESTAMP NOT NULL,
    completed TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES PERSON (id) ON DELETE CASCADE
);

CREATE TABLE ORDER_PRODUCT_COST
(
    order_id        INTEGER NOT NULL,
    product_cost_id INTEGER NOT NULL,
    quantity        INTEGER NOT NULL,
    price      NUMERIC(5, 2)       DEFAULT 0,
    discount   INTEGER             DEFAULT 0,
    FOREIGN KEY (order_id) REFERENCES ORDERS (id) ON DELETE CASCADE,
    FOREIGN KEY (product_cost_id) REFERENCES PRODUCT_COST (id) ON DELETE CASCADE,
    PRIMARY KEY (order_id, product_cost_id)
);


