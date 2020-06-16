
DELETE FROM order_product_cost;

INSERT INTO order_product_cost(order_id, product_cost_id, quantity)
VALUES (1001, 1006, 1),
       (1001, 1014, 2),
       (1002, 1009, 1),
       (1002, 1010, 4),
       (1003, 1008, 1),
       (1003, 1011, 2),
       (1003, 1013, 2),
       (1000, 1006, 7);



