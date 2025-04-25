CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	name VARCHAR(255)
);

CREATE TABLE pets(
	pet_id SERIAL PRIMARY KEY,
	name VARCHAR(255),
	type VARCHAR(255),
	user_id INT REFERENCES users(user_id)
);

INSERT INTO users (name) VALUES ('Alex');
INSERT INTO users (name) VALUES ('Max');
INSERT INTO users (name) VALUES ('Marina');

INSERT INTO pets(name, type, user_id) VALUES ('Tom', 'cat', 1);
INSERT INTO pets(name, type, user_id) VALUES ('Tak', 'cat', 2);
INSERT INTO pets(name, type, user_id) VALUES ('Tik', 'dog', 3);
INSERT INTO pets(name, type) VALUES ('Tuk', 'duck');

SELECT *
FROM users AS u
JOIN pets AS p
ON u.user_id = p.user_id;

SELECT u.name Владелец, p.name Питомец
FROM users AS u
JOIN pets AS p
ON u.user_id = p.user_id;

SELECT u.name AS "Имя владельца", p.name AS "Кличка питомца", p.type AS "Тип"
FROM users AS u
JOIN pets AS p
ON u.user_id = p.user_id;
