create TABLE users (
	user_id SERIAL PRIMARY KEY,
	"name" VARCHAR(255)
);

create TABLE pets (
	pet_id SERIAL PRIMARY KEY,
	"name" VARCHAR(255),
	"type" VARCHAR(255),
	user_id INT REFERENCES users(user_id)
);

insert into users (name) values ('Alex');
insert into users (name) values ('Max');
insert into users (name) values ('Marina');

insert into pets(name, type, user_id) values ('Tom', 'cat', 1);
insert into pets(name, type, user_id) values ('Tak', 'cat', 2);
insert into pets(name, type, user_id) values ('Tik', 'dog', 3);
insert into pets(name, type) values ('Tuk', 'duck');

select *
from users as u
join pets as p
on u.user_id = p.user_id;

select u.name Владелец, p.name Питомец
from users as u
join pets as p
on u.user_id = p.user_id;

select u.name as "Имя владельца", p.name as "Кличка питомца", p.type as "Тип"
from users as u
join pets as p
on u.user_id = p.user_id;
