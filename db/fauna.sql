CREATE TABLE fauna(
    id SERIAL PRIMARY KEY,
    name TEXT,
    avg_age INT,
    discovery_date DATE
);

INSERT INTO fauna (name, avg_age, discovery_date) VALUES('A1 fish', 100, '2000-09-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('B1', 200, '2010-04-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('C1', 300, '2020-06-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('D1', 2200, '2011-04-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('E1', 2300, '2022-06-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('D2', 52200, '2011-04-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('E2', 52300, '2022-06-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('D2', 12200, '2011-04-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('E2', 12300, '2022-06-01');
INSERT INTO fauna (name, avg_age) VALUES('D3', 15100);
INSERT INTO fauna (name, avg_age) VALUES('E3', 14700);
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('D4', 2200, '1911-04-01');
INSERT INTO fauna (name, avg_age, discovery_date) VALUES('E4', 2300, '1822-06-01');

SELECT *
FROM fauna
WHERE name LIKE '%fish%';

SELECT *
FROM fauna
WHERE avg_age BETWEEN 10000 AND 21000;

SELECT *
FROM fauna
WHERE discovery_date IS NULL;

SELECT *
FROM fauna
WHERE discovery_date < '1950-01-01';
