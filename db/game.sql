create table users(
    user_id serial primary key,
    email varchar(255),
    password varchar(255),
    about text,
    created_at timestamp default now()
);

insert into users(email, password, about) values('email@mail.com', 'password', 'my about text');

update users set password = 'newpassword';

delete from users;

select * from users;

