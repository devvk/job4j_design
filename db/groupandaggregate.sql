create table devices(
    device_id serial primary key,
    "name" varchar(255),
    price FLOAT
);

create table people(
    people_id serial primary key,
    "name" varchar(255)
);

create table devices_people(
    device_people_id serial primary key,
    device_id int references devices(device_id),
    people_id int references people(people_id)
);

insert into devices (name, price) values ('Nokia', 100.10), ('IPhone', 1500.50), ('Sony', 750.20);
insert into people (name) values ('Alex'), ('Max'), ('Marina');
insert into devices_people (device_id, people_id) values (1, 1), (1, 2), (1, 3), (2, 1), (2, 3), (3, 2);

-- Средняя цена всех устройств
select avg(price)
from devices

-- Средняя цена всех устройств для каждого человека
select dp.people_id, avg(d.price)
from devices_people as dp
join devices as d
on dp.device_id = d.device_id
group by dp.people_id;

-- Средняя цена всех устройств > 5000 для каждого человека
select dp.people_id, avg(d.price)
from devices_people as dp
join devices as d
on dp.device_id = d.device_id
group by dp.people_id
having avg(d.price) > 5000;
