DROP TABLE IF EXISTS ORDER_PRODUCT_COST;

CREATE TABLE ORDER_PRODUCT_COST
(
    order_id        INTEGER NOT NULL,
    product_cost_id INTEGER NOT NULL,
    quantity        INTEGER NOT NULL,
    price           NUMERIC(5, 2) DEFAULT 0,
    discount        INTEGER       DEFAULT 0,
    PRIMARY KEY (order_id, product_cost_id)
);


