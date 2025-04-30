/*
    Типы триггеров для операций INSERT/UPDATE/DELETE:
    1. BEFORE (до выполнения операции);
    2. INSTEAD OF (вместо выполнения операции);
    3. AFTER (после выполнения операции).

    Как триггеры выполняются и обрабатывают данные:
    1. Триггер уровня строки (FOR EACH ROW) - запускается заново для каждой строки SQL-запроса;
    2. Триггер уровня запроса (FOR EACH STATEMENT) - запускается один раз для всего SQL-запроса.

    В PostgreSQL всегда требуется функция, которую вызывает триггер.

    В триггере INSERT можно использовать только NEW.column_name;
    В триггере UPDATE можно использовать:
	    OLD.column_name для ссылки на столбцы строки перед ее обновлением
	    NEW.column_name для ссылки на столбцы строки после ее обновления;
    В триггере DELETE можно использовать только OLD.column_name.
*/

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(50),
    producer VARCHAR(50),
    count INTEGER DEFAULT 0,
    price INTEGER
);

CREATE TABLE history_of_price (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(50),
    price INTEGER,
    "date" TIMESTAMP
);

INSERT INTO products (product_name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);
INSERT INTO products (product_name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);

-- ##############################################################

-- FUNCTION tax: увеличивает цену на 19%.
CREATE OR REPLACE FUNCTION tax()
    RETURNS TRIGGER AS
$$
    BEGIN
        UPDATE products
        SET price = price * 1.19
        WHERE product_id = (SELECT product_id FROM inserted);
        RETURN NEW;
    END;
$$
LANGUAGE 'plpgsql';

-- TRIGGER FOR EACH STATEMENT AFTER INSERT.
CREATE TRIGGER tax_trigger
AFTER INSERT
ON products
REFERENCING NEW TABLE AS inserted
FOR EACH STATEMENT
EXECUTE FUNCTION tax();

-- ##############################################################

-- FUNCTION tax_row: увеличивает цену на 19%.
CREATE OR REPLACE FUNCTION tax_row()
    RETURNS TRIGGER AS
$$
    BEGIN
        NEW.price := NEW.price * 1.19;
        RETURN NEW;
    END;
$$
LANGUAGE 'plpgsql';

-- TRIGGER FOR EACH ROW BEFORE INSERT.
CREATE TRIGGER tax_row_trigger
BEFORE INSERT
ON products
FOR EACH ROW
EXECUTE FUNCTION tax_row();

-- ##############################################################

-- FUNCTION insert_history_of_price: заносит данные в таблицу при вставке в products.
CREATE OR REPLACE FUNCTION insert_history_of_price()
    RETURNS TRIGGER AS
$$
    BEGIN
        INSERT INTO history_of_price (product_name, price, "date")
        VALUES (NEW.product_name, NEW.price, CURRENT_DATE);
        RETURN NEW;
    END;
$$
LANGUAGE 'plpgsql';

-- TRIGGER FOR EACH ROW AFTER INSERT.
CREATE TRIGGER history_of_price_trigger
AFTER INSERT
ON products
FOR EACH ROW
EXECUTE FUNCTION insert_history_of_price();

-- ##############################################################

-- Отключить триггер.
ALTER TABLE products DISABLE TRIGGER tax_trigger;
-- Включить триггер.
ALTER TABLE products ENABLE TRIGGER tax_trigger;
-- Удалить триггер.
DROP TRIGGER history_of_price_trigger ON products;
