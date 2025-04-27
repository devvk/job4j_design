create table fauna(
    id serial primary key,
    "name" text,
    avg_age int,
    discovery_date date
);

insert into fauna (name, avg_age, discovery_date) values('A1 fish', 100, '2000-09-01');
insert into fauna (name, avg_age, discovery_date) values('B1', 200, '2010-04-01');
insert into fauna (name, avg_age, discovery_date) values('C1', 300, '2020-06-01');
insert into fauna (name, avg_age, discovery_date) values('D1', 2200, '2011-04-01');
insert into fauna (name, avg_age, discovery_date) values('E1', 2300, '2022-06-01');
insert into fauna (name, avg_age, discovery_date) values('D2', 52200, '2011-04-01');
insert into fauna (name, avg_age, discovery_date) values('E2', 52300, '2022-06-01');
insert into fauna (name, avg_age, discovery_date) values('D2', 12200, '2011-04-01');
insert into fauna (name, avg_age, discovery_date) values('E2', 12300, '2022-06-01');
insert into fauna (name, avg_age) values('D3', 15100);
insert into fauna (name, avg_age) values('E3', 14700);
insert into fauna (name, avg_age, discovery_date) values('D4', 2200, '1911-04-01');
insert into fauna (name, avg_age, discovery_date) values('E4', 2300, '1822-06-01');

select *
from fauna
where name like '%fish%';

select *
from fauna
where avg_age between 10000 and 21000;

select *
from fauna
where discovery_date is null;

select *
from fauna
where discovery_date < '1950-01-01';
