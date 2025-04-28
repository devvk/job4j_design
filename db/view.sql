CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    student_name VARCHAR(50)
);
INSERT INTO students (student_name) VALUES ('Иван Иванов');
INSERT INTO students (student_name) VALUES ('Петр Петров');

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    author_name VARCHAR(50)
);
INSERT INTO authors (author_name) VALUES ('Александр Пушкин');
INSERT INTO authors (author_name) VALUES ('Николай Гоголь');

CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    book_name VARCHAR(50),
    author_id INT REFERENCES authors(author_id)
);
INSERT INTO books (book_name, author_id) VALUES ('Евгений Онегин', 1);
INSERT INTO books (book_name, author_id) VALUES ('Капитанская дочка', 1);
INSERT INTO books (book_name, author_id) VALUES ('Дубровский', 1);
INSERT INTO books (book_name, author_id) VALUES ('Мертвые души', 2);
INSERT INTO books (book_name, author_id) VALUES ('Вий', 2);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    active BOOLEAN DEFAULT TRUE,
    book_id INT REFERENCES books(book_id),
    student_id INT REFERENCES students(student_id)
);
INSERT INTO orders (book_id, student_id) VALUES (1, 1);
INSERT INTO orders (book_id, student_id) VALUES (3, 1);
INSERT INTO orders (book_id, student_id) VALUES (5, 2);
INSERT INTO orders (book_id, student_id) VALUES (4, 1);
INSERT INTO orders (book_id, student_id) VALUES (2, 2);

-- CREATE VIEW - студенты у которых 2 или больше книг одного автора
CREATE VIEW students_with_2_or_more_books
AS
SELECT s.student_name, COUNT(a.author_name), a.author_name
FROM students AS s
JOIN orders o ON s.student_id = o.student_id
JOIN books b ON o.book_id = b.book_id
JOIN authors a ON b.author_id = a.author_id
GROUP BY (s.student_name, a.author_name)
HAVING COUNT(a.author_name) >= 2;

SELECT *
FROM show_students_with_2_or_more_books;

-- ALTER VIEW
-- ALTER VIEW show_students_with_2_or_more_books RENAME TO students_books;

SELECT *
FROM students_books;

-- DROP VIEW
DROP VIEW students_books;

-- CREATE VIEW анализ заказов студентов по авторам.
CREATE VIEW students_orders
AS
SELECT s.student_name, COUNT(o.order_id) AS "Заказано (шт.)", a.author_name
FROM students AS s
JOIN orders o ON s.student_id = o.student_id
JOIN books b ON o.book_id = b.book_id
JOIN authors a ON b.author_id = a.author_id
GROUP BY (s.student_name, a.author_name)
ORDER BY 2 DESC;

SELECT *
FROM students_orders;

DROP VIEW students_orders;

COMMIT;