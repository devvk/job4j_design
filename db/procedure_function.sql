/*
    Процедура — для операций с БД (бизнес-логика, транзакции).
      - можно использовать OUT/INOUT параметры
      - можно использовать транзакции (BEGIN, COMMIT, ROLLBACK)
      - допускаются побочные эффекты (INSERT, UPDATE, DELETE)
      - вызываются через CALL
      - можно вызывать другие хранимые процедуры и функции

    Функция — для вычислений и встраивания в запросы.
      - обязана возвращать одно значение (RETURN)
      - нельзя управлять транзакциями
      - не рекомендуется побочное воздействие (INSERT, UPDATE, DELETE)
      - можно вызывать в SELECT, WHERE, JOIN и т.п.
      - можно вызывать только другие хранимые функции
*/

CREATE TABLE products (
    product_id      SERIAL PRIMARY KEY,
    product_name    VARCHAR(50),
    producer        VARCHAR(50),
    count           INT DEFAULT 0,
    price           INT
);


-- #######################################################################################################

-- Процедура insert_data:
CREATE OR REPLACE PROCEDURE insert_data(i_name varchar, prod varchar, i_count integer, i_price integer)
LANGUAGE 'plpgsql'
AS $$
    BEGIN
        INSERT INTO products (product_name, producer, count, price) VALUES (i_name, prod, i_count, i_price);
    END
$$;

-- Вызов процедуры:
CALL insert_data('product_2', 'producer_2', 15, 32);

-- #######################################################################################################

-- Процедура update_data:
CREATE OR REPLACE PROCEDURE update_data(u_count integer, tax float, u_id integer)
LANGUAGE 'plpgsql'
AS $$
    BEGIN
        IF u_count > 0 THEN
            UPDATE products
            SET count = count - u_count
            WHERE product_id = u_id;
        END IF;
        IF
            tax > 0 THEN
            UPDATE products
            SET price = price + price * tax;
        END IF;
    END
$$;

-- Вызов процедуры:
CALL update_data(10, 0, 1);
CALL insert_data('product_1', 'producer_1', 3, 50);
CALL insert_data('product_3', 'producer_3', 8, 115);
CALL update_data(0, 0.2, 0);

-- Переименовать
ALTER PROCEDURE update_data(u_count integer, tax float, u_id integer) RENAME TO update;
-- Удалить
DROP PROCEDURE update_data(u_count integer, tax float, u_id integer);

-- Зачистка таблицы
DELETE FROM products;
ALTER SEQUENCE products_product_id_seq RESTART WITH 1;

-- #######################################################################################################

-- Процедура delete_data:
CREATE OR REPLACE PROCEDURE delete_data(i_id integer)
LANGUAGE 'plpgsql'
AS $$
    BEGIN
        DELETE FROM products
        WHERE product_id = i_id;
    END
$$;

-- Вызов процедуры:
CALL delete_data(4);

-- #######################################################################################################

-- Функция f_insert_data:
CREATE OR REPLACE FUNCTION f_insert_data(i_name varchar, prod varchar, i_count integer, i_price integer)
RETURNS void
LANGUAGE 'plpgsql'
AS
$$
    BEGIN
        INSERT INTO products (product_name, producer, count, price) VALUES (i_name, prod, i_count, i_price);
    END;
$$;

-- Вызов функции:
SELECT f_insert_data('product_1', 'producer_1', 25, 50);

-- #######################################################################################################

-- Функция f_update_data:
CREATE OR REPLACE FUNCTION f_update_data(u_count integer, tax float, u_id integer)
RETURNS integer
LANGUAGE 'plpgsql'
AS
$$
    DECLARE
        RESULT integer;
    BEGIN
        IF u_count > 0 THEN
            UPDATE products
            SET count = count - u_count
            WHERE product_id = u_id;
            SELECT INTO RESULT count
            FROM products
            WHERE product_id = u_id;
        END IF;
        IF tax > 0 THEN
            UPDATE products
            SET price = price + price * tax;
            SELECT INTO RESULT sum(price)
            from products;
        END IF;
        RETURN RESULT;
    END;
$$;

-- Вызов функции:
SELECT f_update_data(10, 0, 1);

SELECT f_insert_data('product_2', 'producer_2', 15, 32);
SELECT f_insert_data('product_3', 'producer_3', 8, 115);

-- #######################################################################################################

-- Функция f_delete_data:
CREATE OR REPLACE FUNCTION f_delete_data(i_id integer)
RETURNS void
LANGUAGE 'plpgsql'
AS
$$
    BEGIN
        DELETE FROM products
        WHERE product_id = i_id;
    END;
$$;

-- Вызов функции:
SELECT f_delete_data(1);
SELECT f_delete_data(2);
SELECT f_delete_data(3);