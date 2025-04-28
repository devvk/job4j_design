-- Кузова.
CREATE TABLE car_bodies (
    body_id SERIAL PRIMARY KEY,
    body_name VARCHAR(255) UNIQUE
);

-- Двигатели.
CREATE TABLE car_engines (
    engine_id SERIAL PRIMARY KEY,
    engine_name VARCHAR(255) UNIQUE
);

-- Коробки передач.
CREATE TABLE car_transmissions (
    transmission_id SERIAL PRIMARY KEY,
    transmission_name VARCHAR(255) UNIQUE
);

-- Автомобили.
CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    car_name VARCHAR(255) UNIQUE,
    body_id INT REFERENCES car_bodies(body_id),
    engine_id INT REFERENCES car_engines(engine_id),
    transmission_id INT REFERENCES car_transmissions(transmission_id)
);

INSERT INTO car_bodies (body_name) VALUES ('Body A');
INSERT INTO car_bodies (body_name) VALUES ('Body B');
INSERT INTO car_bodies (body_name) VALUES ('Body C');
INSERT INTO car_bodies (body_name) VALUES ('Body D');

INSERT INTO car_engines (engine_name) VALUES ('Engine 100');
INSERT INTO car_engines (engine_name) VALUES ('Engine 200');
INSERT INTO car_engines (engine_name) VALUES (NULL);
INSERT INTO car_engines (engine_name) VALUES ('Engine 1000');

INSERT INTO car_transmissions (transmission_name) VALUES ('Transmission T1');
INSERT INTO car_transmissions (transmission_name) VALUES ('Transmission T2');
INSERT INTO car_transmissions (transmission_name) VALUES ('Transmission T3');
INSERT INTO car_transmissions (transmission_name) VALUES ('Transmission T4');
INSERT INTO car_transmissions (transmission_name) VALUES ('Transmission S');

INSERT INTO cars (car_name, body_id, engine_id, transmission_id) VALUES
('Super car', 1, 1, 1),
('Sport car', 2, 2, 2),
('Mini car', 3, 3, 3),
('Travel car', 1, 2, 3),
('City car', 2, 3, 2),
('Jeep car', 3, 3, 4);

-- Список всех машин и все привязанные к ним детали.
SELECT car_id, car_name, body_name, engine_name, transmission_name
FROM cars AS c
LEFT JOIN car_bodies AS cb ON c.body_id = cb.body_id
LEFT JOIN car_engines AS ce ON c.engine_id = ce.engine_id
LEFT JOIN car_transmissions AS ct ON c.transmission_id = ct.transmission_id;

-- Кузова, которые не используются НИ в одной машине.
SELECT cb.body_id, cb.body_name
FROM car_bodies AS cb
LEFT JOIN cars AS c ON cb.body_id = c.body_id
WHERE c.body_id IS NULL;

-- Двигатели, которые не используются НИ в одной машине.
SELECT ce.engine_id, ce.engine_name
FROM car_engines AS ce
LEFT JOIN cars AS c ON ce.engine_id = c.engine_id
WHERE c.engine_id IS NULL;

-- Коробки передач, которые не используются НИ в одной машине.
SELECT ct.transmission_id, ct.transmission_name
FROM car_transmissions AS ct
LEFT JOIN cars AS c ON ct.transmission_id = c.transmission_id
WHERE c.transmission_id IS NULL;

COMMIT;
