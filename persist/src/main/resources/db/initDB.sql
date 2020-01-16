DROP TABLE IF EXISTS ORDER_PRODUCT;
DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS PERSON;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1000;


CREATE TABLE PERSON
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name VARCHAR(40) NOT NULL,
    last_name  VARCHAR(40) NOT NULL,
    email      VARCHAR(50) NOT NULL
);

CREATE UNIQUE INDEX person_unique_email_index ON PERSON (email);

CREATE TABLE PRODUCT
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price       NUMERIC(5, 2)       DEFAULT 0,
    discount    INTEGER             DEFAULT 0,
    picture     VARCHAR(100)
);

CREATE TABLE ORDERS
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    person_id INTEGER NOT NULL,
    created   TIMESTAMP,
    completed BOOLEAN NOT NULL    DEFAULT FALSE,
    FOREIGN KEY (person_id) REFERENCES PERSON (id) ON DELETE CASCADE
);

CREATE TABLE ORDER_PRODUCT
(
    order_id     INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity     INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES PRODUCT (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES ORDERS (id) ON DELETE CASCADE,
    PRIMARY KEY (order_id, product_id)
);


