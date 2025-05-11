/*
    Подзапрос – это запрос внутри другого запроса.
    - Должен быть заключен в круглые скобки
    - Выполняется первым
    - Можно использовать в WHERE, HAVING,  FROM
    - Можно использовать с SELECT, UPDATE, INSERT, DELETE
    - Нельзя использовать в подзапросе ORDER BY
*/

CREATE TABLE companies (
    id   INT PRIMARY KEY,
    city TEXT
);

CREATE TABLE goods (
    id         INT PRIMARY KEY,
    "name"     TEXT,
    company_id INT REFERENCES companies (id),
    price      INT
);

CREATE TABLE sales_managers (
    id          INT PRIMARY KEY,
    last_name   TEXT,
    first_name  TEXT,
    company_id  INT REFERENCES companies (id),
    manager_fee INT
);

CREATE TABLE managers (
    id         INT PRIMARY KEY,
    company_id INT REFERENCES companies (id)
);

INSERT INTO companies
VALUES (1, 'Москва'),
       (2, 'Нью-Йорк'),
       (3, 'Мюнхен');

INSERT INTO goods
VALUES (1, 'Небольшая квартира', 3, 5000),
       (2, 'Квартира в центре', 1, 4500),
       (3, 'Квартира у метро', 1, 3200),
       (4, 'Лофт', 2, 6700),
       (5, 'Загородный дом', 2, 9800);

INSERT INTO sales_managers
VALUES (1, 'Доу', 'Джон', 2, 2250),
       (2, 'Грубер', 'Ганс', 3, 3120),
       (3, 'Смит', 'Сара', 2, 1640),
       (4, 'Иванов', 'Иван', 1, 4500),
       (5, 'Купер', 'Грета', 3, 2130);

INSERT INTO managers
VALUES (1, 2),
       (2, 3),
       (4, 1);

-- 1. СКАЛЯРНЫЕ ПОДЗАПРОСЫ:

-- Менеджеры с вознаграждением выше среднего.
SELECT *
FROM sales_managers
WHERE manager_fee > (SELECT AVG(manager_fee) FROM sales_managers);

-- В операторе SELECT: рядом с ценой товара отражается средняя цена всех товаров
SELECT "name" AS real_estate, price, (SELECT AVG(price) FROM goods) AS avg_price
FROM goods;

-- 2. МНОГОСТРОЧНЫЕ ПОДЗАПРОСЫ (IN, NOT IN, ANY, ALL, EXISTS или NOT EXISTS):

-- Среднее вознаграждение для менеджеров, которые нет в таблице managers
SELECT AVG(manager_fee)
FROM sales_managers
WHERE sales_managers.id NOT IN (SELECT managers.id FROM managers);

-- 3. КОРРЕЛИРОВАННЫЕ ПОДЗАПРОСЫ (внутренний запрос опирается на информацию внешнего запроса):

-- Количество товаров в каждой компании
SELECT city,
       (SELECT COUNT(*)
        FROM goods AS g
        WHERE c.id = g.company_id) AS total_goods
FROM companies c;

-- аналог с join
SELECT c.city, COUNT(g.name) AS total_goods
FROM companies c
JOIN goods g ON c.id = g.company_id
GROUP BY c.city;

-- Информацию о менеджерах, чье вознаграждение было >= среднего вознаграждения в их компании
SELECT last_name, first_name, manager_fee
FROM sales_managers sm1
WHERE sm1.manager_fee >= (SELECT AVG(manager_fee)
                          FROM sales_managers sm2
                          WHERE sm2.company_id = sm1.company_id);

-- id компаний средняя цена товаров в которой выше, чем половина максимальной цены среди цен всех товаров
SELECT company_id, AVG(price) AS average_price
FROM goods
GROUP BY company_id
HAVING AVG(price) > (SELECT MAX(price) FROM goods) / 2;

-- ##########################################################################################
CREATE TABLE customers (
    id         SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    age        INT,
    country    TEXT
);
INSERT INTO customers (first_name, last_name, age, country) VALUES ('Доу', 'Джон', 32, 'USA');
INSERT INTO customers (first_name, last_name, age, country) VALUES ('Грубер', 'Ганс', 33, 'Germany');
INSERT INTO customers (first_name, last_name, age, country) VALUES ('Смит', 'Сара', 2, 'Australia');
INSERT INTO customers (first_name, last_name, age, country) VALUES ('Иванов', 'Иван', 1, 'Russia');

-- Клиенты с минимальным возрастом
SELECT *
FROM customers
WHERE AGE = (SELECT MIN(AGE) FROM customers);

CREATE TABLE orders(
    id          SERIAL PRIMARY KEY,
    amount      INT,
    customer_id INT REFERENCES customers (id)
);
INSERT INTO orders (amount, customer_id) VALUES (100, 1);
INSERT INTO orders (amount, customer_id) VALUES (200, 2);

-- Клиенты не сделавшие ни одного заказа
SELECT *
FROM customers
WHERE id NOT IN (SELECT customer_id FROM orders);
