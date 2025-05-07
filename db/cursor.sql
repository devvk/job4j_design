/*
    КУРСОР:
    - используется для обработки больших таблиц.
    - можно объявить только внутри транзакции.
*/
-- Объявить курсор.
-- DECLARE [cursor_name] [[NO] SCROLL] CURSOR FOR [query];
DECLARE имя_курсора;

-- Открыть курсор.
OPEN имя_курсора;

-- Чтение следующей строки.
FETCH имя_курсора;
-- Закрыть курсор.

CLOSE имя_курсора;

-- Удалить курсор из памяти.
DEALLOCATE имя_курсора;


CREATE TABLE products (
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50),
    count INT DEFAULT 0,
    price INT
);
-- ALTER SEQUENCE products_id_seq RESTART WITH 1;"
INSERT INTO products (name, count, price) VALUES ('product_1', 1, 5);
INSERT INTO products (name, count, price) VALUES ('product_2', 2, 10);
INSERT INTO products (name, count, price) VALUES ('product_3', 3, 15);
INSERT INTO products (name, count, price) VALUES ('product_4', 4, 20);
INSERT INTO products (name, count, price) VALUES ('product_5', 5, 25);
INSERT INTO products (name, count, price) VALUES ('product_6', 6, 30);
INSERT INTO products (name, count, price) VALUES ('product_7', 7, 35);
INSERT INTO products (name, count, price) VALUES ('product_8', 8, 40);
INSERT INTO products (name, count, price) VALUES ('product_9', 9, 45);
INSERT INTO products (name, count, price) VALUES ('product_10', 10, 50);
INSERT INTO products (name, count, price) VALUES ('product_11', 11, 55);
INSERT INTO products (name, count, price) VALUES ('product_12', 12, 60);
INSERT INTO products (name, count, price) VALUES ('product_13', 13, 65);
INSERT INTO products (name, count, price) VALUES ('product_14', 14, 70);
INSERT INTO products (name, count, price) VALUES ('product_15', 15, 75);
INSERT INTO products (name, count, price) VALUES ('product_16', 16, 80);
INSERT INTO products (name, count, price) VALUES ('product_17', 17, 85);
INSERT INTO products (name, count, price) VALUES ('product_18', 18, 90);
INSERT INTO products (name, count, price) VALUES ('product_19', 19, 95);
INSERT INTO products (name, count, price) VALUES ('product_20', 20, 100);

-- Чтение следующей строки из курсора.
-- FETCH [FORWARD | BACKWARD]
--    [direction (rows)]    // количество строк, которые будут возвращены
--                          // может быть пустым – в этом случае курсор по умолчанию получает следующую строку.
--    FROM [cursor_name];

-- Направления движения:
-- NEXT (следующий);
-- PRIOR (предыдущий);
-- FIRST (первый);
-- LAST (последний);
-- ABSOLUTE count (абсолютное количество);
-- RELATIVE count (относительное количество);
-- FORWARD (вперед);        // только для курсоров, объявленных с SCROLL параметром.
-- BACKWARD (назад).        // только для курсоров, объявленных с SCROLL параметром.

-- Переместить курсор в новую позицию:
-- MOVE [FORWARD | BACKWARD]
--    [direction (rows)]
--    FROM [cursor_name];

-- Запустить транзакцию и объявить курсор:
BEGIN;
    DECLARE cursor_products CURSOR FOR SELECT * FROM products;

    FETCH 10 FROM cursor_products;
    FETCH NEXT FROM cursor_products;
    MOVE FORWARD 2 FROM cursor_products;
    MOVE FIRST FROM cursor_products;
    FETCH NEXT FROM cursor_products;

    CLOSE cursor_products;
COMMIT;

-- Курсор для движения в оба направления (SCROLL).
BEGIN;
    DECLARE cursor_products SCROLL CURSOR FOR SELECT * FROM products ORDER BY id;
    -- переход на последнюю запись
    MOVE LAST FROM cursor_products;
    -- переход назад с 20 на 15 запись
    MOVE BACKWARD 5 FROM cursor_products;
    -- переход назад с 15 на 7 запись
    MOVE BACKWARD 8 FROM cursor_products;
    -- переход назад с 7 на 2 запись
    MOVE BACKWARD 5 FROM cursor_products;
    -- переход назад с 2 на 1 запись
    MOVE BACKWARD 1 FROM cursor_products;
    CLOSE cursor_products;
COMMIT;

-- Курсор для движения в оба направления (SCROLL).
BEGIN;
    DECLARE cursor_products SCROLL CURSOR FOR SELECT * FROM products ORDER BY id;
    -- прочитать последнюю запись
    FETCH LAST FROM cursor_products;
    -- прочитать с последней до 15 записи
    FETCH BACKWARD 5 FROM cursor_products;
    -- прочитать с 15 до 7 записи
    FETCH BACKWARD 8 FROM cursor_products;
    -- прочитать с 7 до 2 записи
    FETCH BACKWARD 5 FROM cursor_products;
    -- прочитать 1 запись
    FETCH BACKWARD 1 FROM cursor_products;
    CLOSE cursor_products;
COMMIT;
