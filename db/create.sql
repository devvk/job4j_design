/*
Связи между таблицами:
    users - roles = many-to-one
    roles - rules = many-to-many
    items - users = many-to-one
    items - comments = one-to-many
    items - attachs = one-to-many
    items - categories = many-to-one
    items - states = many-to-one
*/

-- Роли
create table roles (
    role_id serial primary key,
    "name" varchar(100) unique
);

-- Пользователи
create table users (
    user_id serial primary key,
    "name" varchar(100),
    email varchar(100) unique,
    role_id int references roles(role_id)
);

-- Права
create table "rules" (
    rule_id serial primary key,
    "name" varchar(100) unique
);

-- Права ролей
create table role_rules (
    role_id int references roles(role_id),
    rule_id int references rules(rule_id),
    primary key(role_id, rule_id)
);

-- Состояние заявки
create table states (
    state_id serial primary key,
    "name" varchar(100)
);

-- Категории заявки
create table categories (
    category_id serial primary key,
    "name" varchar(100)
);

-- Заявки
create table items (
    item_id serial primary key,
    "name" varchar(255),
    user_id int references users(user_id),
    category_id int references categories(category_id),
    state_id int references states(state_id)
);

-- Комментарии заявок
create table comments (
    comment_id serial primary key,
    comment text,
    item_id int references items(item_id)
);

-- Приложенные файлы
create table attachs (
    attach_id serial primary key,
    attach text,
    item_id int references items(item_id)
);

