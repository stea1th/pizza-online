
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS ADDRESS;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1000;

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
    address_id INTEGER      NOT NULL,
    FOREIGN KEY (address_id) REFERENCES ADDRESS (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX person_unique_keycloak_index ON PERSON (keycloak);



