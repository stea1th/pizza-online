INSERT INTO person(keycloak)
VALUES ('"8e0b12eb-1063-48b2-aaab-9dc0b8852453"');

INSERT INTO person (first_name, last_name, email, keycloak)
VALUES ('Dmitrij', 'Gusev', 'a@a.de', '"b27c839b-0e47-40c8-8574-1cd45273a8ab"'),
       ('Ivan', 'Ogurcov', 'b@b.de', '"4562d862-1ce1-474d-bbd1-eba52def4b2e"');

INSERT INTO product(name, description, picture)
VALUES ('Pizza Tonno', 'Fish, Tomato, Cheese', 'Tonno.jpg'),
       ('Pizza Salami', 'Salami, Tomato, Cheese', 'Salami.jpg'),
       ('Pizza Mozarella', 'Different cheese arts, Tomato, Potato', 'Mozarella.jpg');

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
VALUES (1000, now() - INTERVAL '1 day', true),
       (1002, now() - INTERVAL '3 hours 20 minutes', true),
       (1001, now() - INTERVAL '1 day 3 hours', true),
       (1000, now(), false);

INSERT INTO order_product_cost(order_id, product_cost_id, quantity)
VALUES (1016, 1006, 1),
       (1016, 1014, 2),
       (1017, 1009, 1),
       (1017, 1010, 4),
       (1018, 1008, 1),
       (1018, 1011, 2),
       (1018, 1013, 2),
       (1015, 1006, 7);

INSERT INTO product_cost(product_id, property, price, discount)
VALUES (1005, 'family size', 20.59, 12);


