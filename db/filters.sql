CREATE TABLE product_type (
    type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE product(
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    type_id INT REFERENCES product_type(type_id),
    expired_date DATE,
    price DECIMAL(8, 2)
);

INSERT INTO product_type (name) VALUES('СЫР');
INSERT INTO product_type (name) VALUES('МОЛОКО');
INSERT INTO product_type (name) VALUES('ФРУКТЫ');
INSERT INTO product_type (name) VALUES('ДЕСЕРТ');
INSERT INTO product (name, type_id, expired_date, price) VALUES('Сыр плавленный', 1, '2025-05-10', 200);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Сыр моцарелла', 1, '2025-05-12', 300);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Молоко1', 2, '2025-05-10', 50);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Молоко2', 2, '2025-05-10', 50);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Яблоко', 3, '2025-06-10', 100);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Ананас', 3, '2025-03-30', 300);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Мороженое1', 4, '2025-10-10', 100);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Мороженое2', 4, '2025-10-10', 120);
INSERT INTO product (name, type_id, expired_date, price) VALUES('Мороженое3', 4, '2025-10-10', 300);

-- Все продукты с типом "СЫР".
SELECT *
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id
WHERE pt.name = 'СЫР';

-- Все продукты со словом "мороженое" в имени.
SELECT *
FROM product AS p
WHERE LOWER(p.name) LIKE '%мороженое%';

-- Все просроченные продукты.
SELECT *
FROM product AS p
WHERE expired_date < current_date;

-- Самый дорогой продукт.
SELECT name, price
FROM product
WHERE price = (
    SELECT MAX(price)
    FROM product
);

-- Самый дорогой продукт с типом.
SELECT p.name, pt.name, price
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id
WHERE price = (
    SELECT MAX(price)
    FROM product
);

-- Количество продуктов каждого типа
SELECT pt.name, COUNT(*) AS "Количество"
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id
GROUP BY pt.name;

-- Все продукты с типом "СЫР" и "МОЛОКО"
SELECT *
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id
WHERE pt.name IN('СЫР', 'МОЛОКО');

-- Тип продуктов, которых < 10
SELECT pt.name, COUNT(*)
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id
GROUP BY pt.name
HAVING COUNT(*) < 10;

-- Все продукты с типом
SELECT *
FROM product AS p
JOIN product_type AS pt
ON p.type_id = pt.type_id;
