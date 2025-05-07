-- PostgreSQL неявно выполняет COMMIT после каждой команды,
-- которой не предшествует START TRANSACTION (или BEGIN), и поэтому такое поведение
-- часто называется «автофиксацией».
\set AUTOCOMMIT off
-- Согласно стандарту, выполнять START TRANSACTION, чтобы начать блок транзакции,
-- необязательно: блок неявно начинает любая команда SQL.
BEGIN;

-- Установить уровень изоляции на сессию
SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE;

COMMIT;
ROLLBACK;

-- Установить новую точку сохранения в текущей транзакции.
SAVEPOINT имя_точки_сохранения;

-- Удалить точку сохранения, но сохраняет все изменения, сделанные после неё.
RELEASE SAVEPOINT имя_точки_сохранения;

-- Откатить все команды, которые выполнены после установления точки сохранения.
ROLLBACK TO SAVEPOINT имя_точки_сохранения;

-- Режим доступа транзакции только для чтения.
BEGIN TRANSACTION READ ONLY;
-- Режим доступа транзакции чтение и запись (по умолчанию).
BEGIN TRANSACTION READ WRITE;

CREATE TABLE products (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50),
    producer VARCHAR(50),
    count    INT DEFAULT 0,
    price    INT
);
INSERT INTO products (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);
INSERT INTO products (name, producer, count, price) VALUES ('product_2', 'producer_2', 15, 32);
INSERT INTO products (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

BEGIN;
    INSERT INTO products (name, producer, count, price) VALUES ('product_4', 'producer_4', 11, 64);
COMMIT;

BEGIN;
    DELETE FROM products;
    DROP TABLE products;
ROLLBACK;
SELECT * FROM products;

BEGIN;
    INSERT INTO products (name, producer, count, price) VALUES ('product_5', 'producer_5', 17, 45);
SAVEPOINT first_savepoint;
    DELETE FROM products WHERE price = 115;
    UPDATE products SET price = 75 WHERE name = 'product_1';
    SELECT * FROM products;
ROLLBACK TO first_savepoint;
    SELECT * FROM products;
COMMIT;

BEGIN;
    INSERT INTO products (name, producer, count, price) VALUES ('product_6', 'producer_6', 12, 125);
SAVEPOINT s1;
    DELETE FROM products WHERE name LIKE '%_6';
    SELECT * FROM products;
ROLLBACK TO s1;
SAVEPOINT s2;
    SELECT * FROM products;
    INSERT INTO products (name, producer, count, price) VALUES ('product_7', 'producer_7', 22, 25);
    SELECT * FROM products;
COMMIT;

BEGIN;
    UPDATE accounts SET balance = balance - 100.00 WHERE name = 'Alice';
SAVEPOINT my_savepoint;
    UPDATE accounts SET balance = balance + 100.00 WHERE name = 'Bob';
-- ошибочное действие... забыть его и использовать счёт Уолли
ROLLBACK TO my_savepoint;
    UPDATE accounts SET balance = balance + 100.00 WHERE name = 'Wally';
COMMIT;