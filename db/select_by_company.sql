-- Компании
CREATE TABLE companies (
    "id" SERIAL PRIMARY KEY,
    "name" CHARACTER VARYING
);

-- Сотрудники
CREATE TABLE people (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    company_id INTEGER REFERENCES companies(id)
);

-- Добавляем компании
INSERT INTO companies ("name") VALUES
('Google'),
('Amazon'),
('Microsoft'),
('Tesla'),
('OpenAI');

-- Добавляем сотрудников
INSERT INTO people ("name", company_id) VALUES
('Alice Johnson', 1),
('Bob Smith', 1),
('Charlie Brown', 2),
('Diana Evans', 2),
('Ethan White', 3),
('Fiona Green', 3),
('George Black', 4),
('Hannah Adams', 4),
('Ian Clark', 5),
('Julia Roberts', 5);

-- Имена всех person, которые не состоят в компании с id = 5;
-- Название компании для каждого человека.
SELECT p."name", c."name"
FROM people AS p
JOIN companies AS c ON p.company_id = c.id
WHERE c.id != 5;

-- Название компании с максимальным количеством человек + количество человек в этой компании.
-- Нужно учесть, что таких компаний может быть несколько.
SELECT c."name",
	(SELECT COUNT(*)
	FROM people AS P
	WHERE p.company_id = c.id) AS people_count
FROM companies AS c

-- Альтернатива
SELECT c."name", COUNT(p.id) AS people_count
FROM companies AS c
LEFT JOIN people AS p ON p.company_id = c.id
GROUP BY c."name";