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
create TABLE roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

-- Пользователи
create TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    role_id INT REFERENCES roles(role_id)
);

-- Права
create TABLE rules (
    rule_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

-- Права ролей
create TABLE role_rules (
    role_id INT REFERENCES roles(role_id),
    rule_id INT REFERENCES rules(rule_id),
    PRIMARY KEY(role_id, rule_id)
);

-- Состояние заявки
create TABLE states (
    state_id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

-- Категории заявки
create TABLE categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

-- Заявки
create TABLE items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_id INT REFERENCES users(user_id),
    category_id INT REFERENCES categories(category_id),
    state_id INT REFERENCES states(state_id)
);

-- Комментарии заявок
create TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    comment TEXT,
    item_id INT REFERENCES items(item_id)
);

-- Приложенные файлы
create TABLE attachs (
    attach_id SERIAL PRIMARY KEY,
    attach TEXT,
    item_id INT REFERENCES items(item_id)
);

