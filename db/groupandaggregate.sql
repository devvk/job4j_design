CREATE TABLE devices(
    device_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT
);

CREATE TABLE people(
    people_id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE devices_people(
    device_people_id SERIAL PRIMARY KEY,
    device_id INT REFERENCES devices(device_id),
    people_id INT REFERENCES people(people_id)
);

INSERT INTO devices (name, price) VALUES ('Nokia', 100.10), ('IPhone', 1500.50), ('Sony', 750.20);
INSERT INTO people (name) VALUES ('Alex'), ('Max'), ('Marina');
INSERT INTO devices_people (device_id, people_id) VALUES (1, 1), (1, 2), (1, 3), (2, 1), (2, 3), (3, 2);

-- Средняя цена всех устройств
SELECT avg(price)
FROM devices

-- Средняя цена всех устройств для каждого человека
SELECT dp.people_id, avg(d.price)
FROM devices_people AS dp
JOIN devices AS d
ON dp.device_id = d.device_id
GROUP BY dp.people_id;

-- Средняя цена всех устройств > 5000 для каждого человека
SELECT dp.people_id, avg(d.price)
FROM devices_people AS dp
JOIN devices AS d
ON dp.device_id = d.device_id
GROUP BY dp.people_id
HAVING avg(d.price) > 5000;
