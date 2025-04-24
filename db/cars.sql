create table owners(
	owner_id serial primary key,
	name varchar(255)
);

create table cars(
	car_id serial primary key,
	model varchar(255)
);

/* many-to-many
   https://prnt.sc/jW3goTXD3NxZ у одной машины может быть много владельцев,
   а у одного владельца может быть много машин (даже одинаковых).
*/
create table car_owners(
	car_owner_id serial primary key,
	owner_id int references owners(owner_id),
	car_id int references cars(car_id)
);

/* many-to-one
   https://prnt.sc/84ZFM9mAb7Wr у одной машины может быть много ключей.
*/
create table car_keys(
	car_key_id serial primary key,
	car_id int references cars(car_id)
);

/* one-to-one
   https://prnt.sc/xjmXS80lrDf5 у одного телефона только один владелец.
*/
create table owner_phone(
	phone varchar(15) primary key,
	owner_id int references owners(owner_id) unique
);
