-- Уровень изоляции по умолчанию.
SET default_transaction_isolation = 'repeatable read';
SHOW default_transaction_isolation;

-- Уровень изоляции для текущей сессии.
SET transaction_isolation = 'serializable';
SHOW transaction_isolation;

-- Уровень изоляции для текущей транзакции.
-- BEGIN ISOLATION LEVEL READ UNCOMMITTED;
-- BEGIN ISOLATION LEVEL READ COMMITTED;
-- BEGIN ISOLATION LEVEL REPEATABLE READ;
-- BEGIN ISOLATION LEVEL SERIALIZABLE;
BEGIN;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- другие операции в рамках транзакции
COMMIT;

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

/*
    Read Committed (чтение фиксированных данных)
    Аномалия Non-repeatable Read (неповторяющееся чтение):
    Транзакция читает данные, и в процессе выполнения транзакции
    эти данные изменяются другой транзакцией.
*/
-- Транзакция 1
BEGIN;
SELECT * FROM products;

-- Транзакция 2
BEGIN;
SELECT * FROM products;

-- Транзакция 1
INSERT INTO products (name, count, price) VALUES ('product_4', 11, 64);
DELETE FROM products WHERE price = 115;
UPDATE products SET price = 75 WHERE name = 'product_1';

-- Транзакция 2 (аномалия dirty read отсутствует)
SELECT * FROM products;

-- Транзакция 1
COMMIT;

-- Транзакция 2
-- Видно аномалию Non-repeatable Read (неповторяющееся чтение): INSERT и DELETE
-- Видно аномалию Phantom Read (фантомное чтение): INSERT
SELECT * FROM products;

/*
    Repeatable Read (повторяющееся чтение)
    Аномалия Phantom Read (фантомное чтение):
    Транзакция читает набор строк, и в процессе выполнения транзакции
    другая транзакция вставляет/удаляет строки, что влияет на набор данных.
*/

-- Транзакция 1


-- Транзакция 2