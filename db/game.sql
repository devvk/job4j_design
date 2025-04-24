create TABLE users(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    about TEXT, 
    created_at TIMESTAMP DEFAULT NOW()
);

insert into users(email, password, about) values('email@mail.com', 'password', 'my about text');

update users set password = 'newpassword';

delete from users;

select * from users;

