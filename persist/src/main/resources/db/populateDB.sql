INSERT INTO person (first_name, last_name, email)
VALUES ('Vadim', 'Pechenkin', '1@1.de'),
       ('Dmitrij', 'Gusev', 'a@a.de'),
       ('Ivan', 'Ogurcov', 'b@b.de');

INSERT INTO product(name, description, price, picture)
VALUES ('Pizza Tonno', 'small size', 5.60, 'Tonno.jpg'),
       ('Pizza Tonno', 'middle size', 6.50, 'Tonno.jpg'),
       ('Pizza Tonno', 'big size', 8.60, 'Tonno.jpg'),
       ('Pizza Salami', 'small size', 5.40, 'Salami.jpg'),
       ('Pizza Salami', 'middle size', 6.20, 'Salami.jpg'),
       ('Pizza Salami', 'big size', 7.90, 'Salami.jpg'),
       ('Pizza Mozarella', 'small size', 3.60, 'Mozarella.jpg'),
       ('Pizza Mozarella', 'medium size', 7.60, 'Mozarella.jpg'),
       ('Pizza Mozarella', 'big size', 9.55, 'Mozarella.jpg');

INSERT INTO orders(person_id, created, completed)
VALUES (1000, now() - INTERVAL '1 day', true),
       (1002, now() - INTERVAL '3 hours 20 minutes', true),
       (1001, now() - INTERVAL '1 day 3 hours', true),
       (1000, now(), false);

INSERT INTO order_product(order_id, product_id, quantity)
VALUES (1013, 1010, 1),
       (1013, 1011, 2),
       (1014, 1004, 1),
       (1014, 1007, 4),
       (1015, 1006, 1),
       (1015, 1008, 2),
       (1015, 1011, 2),
       (1012, 1003, 7);


