
DELETE FROM orders;

ALTER SEQUENCE global_seq
    RESTART WITH 1000;

INSERT INTO orders(person_id, created, completed)
VALUES (1000, now() - INTERVAL '1 day', now()),
       (1002, now() - INTERVAL '3 hours 20 minutes', now()),
       (1001, now() - INTERVAL '1 day 3 hours', now()),
       (1000, now(), NULL);



