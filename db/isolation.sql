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
    Допускает аномалию: Non-repeatable Read (неповторяющееся чтение) - повторный запрос может
    вернуть разные данные, если другая транзакция их изменила и закоммитила (UPDATE).
    Предотвращает: Dirty Read.
*/
-- Транзакция1
BEGIN;
SELECT * FROM products;

-- Транзакция2
BEGIN;
SELECT * FROM products;

-- Транзакция1
INSERT INTO products (name, count, price) VALUES ('product_4', 11, 64);
DELETE FROM products WHERE price = 115;
UPDATE products SET price = 75 WHERE name = 'product_1';

-- Транзакция2 (аномалия dirty read отсутствует)
SELECT * FROM products;

-- Транзакция1
COMMIT;

-- Транзакция2
-- Видно аномалию Non-repeatable Read (неповторяющееся чтение): INSERT и DELETE
-- Видно аномалию Phantom Read (фантомное чтение): INSERT
SELECT * FROM products;

/*
    Repeatable Read (повторяющееся чтение)
    Допускает аномалию: Phantom Read (фантомное чтение) - транзакция читает набор строк,
    а другая транзакция вставляет/удаляет строки, что влияет на набор данных (INSERT / DELETE).
    Предотвращает: Dirty Read, Non-repeatable Read.
*/
-- Транзакция1 начало:
BEGIN ISOLATION LEVEL REPEATABLE READ;
SELECT * FROM products;

-- Транзакция2 начало:
BEGIN ISOLATION LEVEL REPEATABLE READ;
SELECT * FROM products;

-- Транзакция1 INSERT, UPDATE и DELETE:
INSERT INTO products (name, count, price) VALUES ('product_4', 11, 64);
DELETE FROM products WHERE price = 115;
UPDATE products SET price = 75 WHERE name = 'product_1';

-- Транзакция2 UPDATE:
-- Получаем LOCK: вторая транзакция будет ждать,
-- пока первая транзакция не зафиксирует изменения или не откатится.
UPDATE products SET price = 75 WHERE name = 'product_1';

-- Другой вариант.
-- Транзакция1 ROLLBACK:
ROLLBACK;

-- Транзакция2 UPDATE:
-- В данном случае вторая транзакция выполнилась после отката первой.
UPDATE products SET price = 75 WHERE name = 'product_1';

-- В PostgreSQL для уровня изолированности Repeatable Read от Phantom Read избавились.
-- Хотя в классическом представлении этого уровня, мы должны наблюдать этот эффект.


/*
    Serializable (cериализуемый)
    Допускает только выполнение изменений данных, как будто все модифицирующие
    данные транзакции выполняются не параллельно, а последовательно.
*/
INSERT INTO products (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);
INSERT INTO products (name, producer, count, price) VALUES ('product_2', 'producer_2', 15, 32);
INSERT INTO products (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

-- Транзакция1 начало:
BEGIN ISOLATION LEVEL SERIALIZABLE;
SELECT SUM(count) FROM products;

-- Транзакция2 начало:
BEGIN ISOLATION LEVEL SERIALIZABLE;
SELECT SUM(count) FROM products;

-- Транзакция1 UPDATE:
UPDATE products SET count = 26 WHERE name = 'product_1';

-- Транзакция2 UPDATE:
UPDATE products SET count = 26 WHERE name = 'product_2';

-- Транзакция2 COMMIT:
-- изменения зафиксированы.
COMMIT;

-- Транзакция1 COMMIT:
-- Ошибка при фиксации изменений т.к. обе транзакции изменили то, что другая транзакция может прочитать
-- в операторе SELECT. Если бы была возможность зафиксировать оба этих изменения, то это нарушило бы
-- поведение Serializable, поскольку если бы они выполнялись по очереди, то одна из транзакций увидела
-- бы измененную запись, которая изменилась в другой транзакции.
-- Для данного уровня изолированности мы получаем максимальную согласованность данных,
-- никакие лишние данные не зафиксируются.
COMMIT;
