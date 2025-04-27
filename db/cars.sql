CREATE TABLE owners (
	owner_id SERIAL PRIMARY KEY,
	"name" VARCHAR(255)
);

CREATE TABLE cars (
	car_id SERIAL PRIMARY KEY,
	model VARCHAR(255)
);

/* many-to-many
   https://prnt.sc/jW3goTXD3NxZ у одной машины может быть много владельцев,
   а у одного владельца может быть много машин (даже одинаковых).
*/
CREATE TABLE car_owners (
	car_owner_id SERIAL PRIMARY KEY,
	owner_id INT REFERENCES owners(owner_id),
	car_id INT REFERENCES cars(car_id)
);

/* many-to-one
   https://prnt.sc/84ZFM9mAb7Wr у одной машины может быть много ключей.
*/
CREATE TABLE car_keys (
	car_key_id SERIAL PRIMARY KEY,
	car_id INT REFERENCES cars(car_id)
);

/* one-to-one
   https://prnt.sc/xjmXS80lrDf5 у одного телефона только один владелец.
*/
CREATE TABLE owner_phone (
	phone VARCHAR(15) PRIMARY KEY,
	owner_id INT REFERENCES owners(owner_id) UNIQUE
);
