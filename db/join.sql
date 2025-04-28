CREATE TABLE departments (
    department_id SERIAL PRIMARY KEY,
    "name" VARCHAR(255) UNIQUE
);

CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    department_id INT REFERENCES departments(department_id)
);

INSERT INTO departments(name) VALUES ('Department 1');
INSERT INTO departments(name) VALUES ('Department 2');
INSERT INTO departments(name) VALUES ('Department 3');
INSERT INTO departments(name) VALUES ('Department 4');

INSERT INTO employees(name, department_id) VALUES ('Alex', 1);
INSERT INTO employees(name, department_id) VALUES ('Bob', 1);
INSERT INTO employees(name, department_id) VALUES ('Marina', 3);
INSERT INTO employees(name, department_id) VALUES ('Dina', null);
INSERT INTO employees(name, department_id) VALUES ('Anton', null);
INSERT INTO employees(name, department_id) VALUES ('Olga', 1);

SELECT *
FROM employees e
LEFT JOIN departments d
ON e.department_id = d.department_id;

SELECT *
FROM employees e
RIGHT JOIN departments d
ON e.department_id = d.department_id;

SELECT *
FROM employees e
FULL JOIN departments d
ON e.department_id = d.department_id;

SELECT *
FROM employees
CROSS JOIN departments;

-- Департаменты, у которых нет работников.
SELECT *
FROM departments d
LEFT JOIN employees e
ON e.department_id = d.department_id
WHERE e.name IS NULL;

-- Одинаковый результат с left и right join (включая порядок колонок).
SELECT d.department_id, d.name, employee_id, e.name, e.department_id
FROM departments d
LEFT JOIN employees e
ON e.department_id = d.department_id;

SELECT d.department_id, d.name, employee_id, e.name, e.department_id
FROM employees e
RIGHT JOIN departments d
ON e.department_id = d.department_id;

-- CREATE TYPE gender_type AS ENUM ('m', 'f', 'o');
CREATE TABLE teens (
    teen_id SERIAL PRIMARY KEY,
    "name" VARCHAR(255),
    gender VARCHAR
);

INSERT INTO teens (name, gender) VALUES ('Вася', 'm');
INSERT INTO teens (name, gender) VALUES ('Коля', 'm');
INSERT INTO teens (name, gender) VALUES ('Петя', 'm');
INSERT INTO teens (name, gender) VALUES ('Марина', 'f');
INSERT INTO teens (name, gender) VALUES ('Маша', 'f');
INSERT INTO teens (name, gender) VALUES ('Света', 'f');

-- Все возможные разнополые пары.
SELECT 
    t1.name AS мальчик, 
    t2.name AS девочка
FROM 
    teens t1
CROSS JOIN 
    teens t2
WHERE 
    t1.gender = 'm' 
    AND t2.gender = 'f';
