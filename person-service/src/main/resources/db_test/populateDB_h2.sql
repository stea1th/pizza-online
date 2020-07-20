
-- ALTER SEQUENCE global_seq
--     RESTART WITH 997;

INSERT INTO address(street, zip, city, country)
VALUES ('Hauptstrasse 6', '12345', 'Essen', 'Deutschland'),
       ('Schobertweg 3', '45678', 'München', 'Deutschland'),
       ('Sedulinos 7', '23452', 'Ignalina', 'Litauen');

INSERT INTO person(keycloak, address_id)
VALUES ('"b04bf0fe-135e-4dc5-a130-48a0109543a6"', 998);

INSERT INTO person (first_name, last_name, email, keycloak, address_id)
VALUES ('Dmitrij', 'Gusev', 'a@a.de', '"273719a1-2df0-46c9-a214-debeef8d3165"', 997),
       ('Ivan', 'Ogurcov', 'b@b.de', '"cd55a854-1b96-44d0-ba30-73176a0708f5"', 999);



