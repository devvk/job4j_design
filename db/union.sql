/*
    Операторы множества – объединяют результаты двух и более запросов в один.
        UNION - объединяет строки с одинаковыми столбцами исключая дубликаты (DISTINCT).
        UNION ALL - объединяет и не удаляет дубликаты.
        INTERSECT - возвращает только те строки, которые присутствуют в обоих запросах (пересечение).
        EXCEPT - возвращает строки из первого запроса, которых нет во втором (кроме).

    Важно!
    UNION и EXCEPT выполняются по порядку,
    INTERSECT выполняться первым!

    SELECT first_select_query
    set_operator
    SELECT second_select_query;

    - количество столбцов должно совпадать
    - типы данных каждого из столбцов должны быть совместимыми
    - порядок столбцов в каждом SELECT должны быть одинаковыми

    Чем отличается UNION от JOIN?
        UNION - объединяет строки с одинаковыми столбцами исключая дубликаты.
        JOIN - добавляет данные из присоединяемой таблицы в новые столбцы.
*/
CREATE TABLE customer (
    first_name TEXT,
    last_name  TEXT,
    status TEXT
);

CREATE TABLE employee (
    first_name TEXT,
    last_name  TEXT,
    emp_status TEXT
);
INSERT INTO customer
VALUES ('Иван', 'Иванов', 'Active'),
       ('Петр', 'Сергеев', 'Active'),
       ('Ирина', 'Бросова', 'Active'),
       ('Анна', 'Опушкина', 'Active'),
       ('Потап', 'Потапов', 'Passive');

INSERT INTO employee
VALUES ('Кристина', 'Позова', 'Current'),
       ('Михаил', 'Кругов', 'Current'),
       ('Анна', 'Опушкина', 'Current'),
       ('Иван', 'Иванов', 'Current'),
       ('Сергей', 'Петров', 'Current');

-- UNION - объединяет и удаляет все повторяющиеся строки.
SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee;

-- UNION ALL - объединяет и НЕ удаляет дубликаты.
SELECT first_name, last_name
FROM customer
UNION ALL
SELECT first_name, last_name
FROM employee;

--  EXCEPT – возвращает строки из первого запроса, которых нет во втором.
SELECT first_name, last_name
FROM customer
EXCEPT
SELECT first_name, last_name
FROM employee;

-- INTERSECT – возвращает общие строки.
SELECT first_name, last_name
FROM customer
INTERSECT
SELECT first_name, last_name
FROM employee;

CREATE TABLE referrer (
    first_name TEXT,
    last_name  TEXT
);
INSERT INTO referrer
VALUES ('Евгений', 'Онегин'),
       ('Петр', 'Сергеев'),
       ('Александр', 'Ожегов'),
       ('Анна', 'Опушкина'),
       ('Михаил', 'Кругов');

-- Объединение трёх таблиц
SELECT first_name, last_name
FROM customer
UNION
SELECT first_name, last_name
FROM employee
UNION
SELECT first_name, last_name
FROM referrer
ORDER BY first_name, last_name;

-- Клиенты и сотрудники, но которых нет в таблице рефералов
SELECT first_name, last_name
FROM customer
UNION ALL
SELECT first_name, last_name
FROM employee
EXCEPT
SELECT first_name, last_name
FROM referrer
ORDER BY first_name, last_name;

-- Важно!
-- UNION и EXCEPT выполняются по порядку,
-- INTERSECT выполняться первым!

-- ###################################################################################
CREATE TABLE movie  (
    id       SERIAL PRIMARY KEY,
    "name"   TEXT,
    director TEXT
);
CREATE TABLE book (
    id     SERIAL PRIMARY KEY,
    title  TEXT,
    author TEXT
);
INSERT INTO movie (name, director)
VALUES ('Марсианин', 'Ридли Скотт'),
       ('Матрица', 'Братья Вачовски'),
       ('Властелин колец', 'Питер Джексон'),
       ('Гарри Поттер и узник Азкабана', 'Альфонсо Куарон'),
       ('Железный человек', 'Джон Фавро');

INSERT INTO book (title, author)
VALUES ('Гарри Поттер и узник Азкабана', 'Джоан Роулинг'),
       ('Властелин колец', 'Джон Толкин'),
       ('1984', 'Джордж Оруэлл'),
       ('Марсианин', 'Энди Уир'),
       ('Божественная комедия', 'Данте Алигьери');

-- Названия всех фильмов, которые сняты по книге:
SELECT NAME FROM movie
INTERSECT
SELECT title FROM book;

-- Все названия книг, у которых нет экранизации:
SELECT title FROM book
EXCEPT
SELECT NAME FROM movie;

-- Все уникальные названия произведений из таблиц movie и book
SELECT NAME FROM movie
WHERE NAME NOT IN (SELECT title FROM book)
UNION
SELECT title FROM book
WHERE title NOT IN (SELECT NAME FROM movie);
