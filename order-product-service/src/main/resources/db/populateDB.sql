
DELETE FROM order_product_cost;
DELETE FROM orders;
DELETE FROM product_cost;
DELETE FROM product;
DELETE FROM person;
DELETE FROM address;

ALTER SEQUENCE global_seq
    RESTART WITH 997;

INSERT INTO address(street, zip, city, country)
VALUES ('Hauptstrasse 6', '12345', 'Essen', 'Deutschland'),
       ('Schobertweg 3', '45678', 'München', 'Deutschland'),
       ('Sedulinos 7', '23452', 'Ignalina', 'Litauen');

INSERT INTO person(keycloak, address_id)
VALUES ('"b04bf0fe-135e-4dc5-a130-48a0109543a6"', 998);

INSERT INTO person (first_name, last_name, email, keycloak, address_id)
VALUES ('Dmitrij', 'Gusev', 'a@a.de', '"273719a1-2df0-46c9-a214-debeef8d3165"', 997),
       ('Ivan', 'Ogurcov', 'b@b.de', '"cd55a854-1b96-44d0-ba30-73176a0708f5"', 999);

INSERT INTO product(name, description, picture)
VALUES ('Pizza Tonno', 'Fish, Tomato, Cheese', 'Tonno.jpg'),
       ('Pizza Salami', 'Salami, Tomato, Cheese', 'Salami.jpg'),
       ('Pizza Mozzarella', 'Different cheese arts, Tomato, Potato', 'Mozzarella.jpg');

INSERT INTO product_cost(product_id, property, price)
VALUES (1003, 'small size', 5.60),
       (1003, 'middle size', 6.50),
       (1003, 'big size', 8.60),
       (1004, 'small size', 5.40),
       (1004, 'middle size', 6.20),
       (1004, 'big size', 7.90),
       (1005, 'small size', 3.60),
       (1005, 'medium size', 7.60),
       (1005, 'big size', 9.55);


INSERT INTO orders(person_id, created, completed)
VALUES (1000, now() - INTERVAL '1 day', now()),
       (1002, now() - INTERVAL '3 hours 20 minutes', now()),
       (1001, now() - INTERVAL '1 day 3 hours', now()),
       (1000, now(), NULL);

INSERT INTO order_product_cost(order_id, product_cost_id, quantity)
VALUES (1016, 1006, 1),
       (1016, 1014, 2),
       (1017, 1009, 1),
       (1017, 1010, 4),
       (1018, 1008, 1),
       (1018, 1011, 2),
       (1018, 1013, 2),
       (1015, 1006, 7);

INSERT INTO product(name, description, picture)
VALUES ('Pizza Diavolo', 'Meat, Tomato, Pepperoni, Cheese', 'Diavolo.jpg'),
       ('Pizza Piccolo Salami', 'Piccolo Salami, Tomato, Cheese', 'Piccolo.jpg'),
       ('Pizza 4 Season', 'Different cheese arts, Tomato, Noodle, Potato', '4Season.jpg');

INSERT INTO product_cost(product_id, property, price, discount)
VALUES (1005, 'family size', 20.59, 12);

INSERT INTO product_cost(product_id, property, price)
VALUES (1019, 'small size', 6.65),
       (1019, 'middle size', 6.95),
       (1019, 'big size', 8.65),
       (1020, 'small size', 5.20),
       (1020, 'middle size', 6.35),
--        (1004, 'big size', 7.90),
       (1021, 'small size', 3.60),
       (1021, 'medium size', 7.60),
       (1021, 'big size', 9.55);


