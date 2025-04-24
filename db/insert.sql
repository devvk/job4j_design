-- Роли
INSERT INTO roles (name) VALUES('user');
INSERT INTO roles (name) VALUES('admin');

-- Пользователи
INSERT INTO users (name, email, role_id) VALUES('Alex', 'alex@mail.com', 1);
INSERT INTO users (name, email, role_id) VALUES('Max', 'max@mail.com', 2);

-- Права
INSERT INTO rules (name) VALUES('can_create');
INSERT INTO rules (name) VALUES('can_edit');
INSERT INTO rules (name) VALUES('can_delete');

-- Права ролей
-- user
INSERT INTO role_rules (role_id, rule_id) VALUES(1, 1);
-- admin
INSERT INTO role_rules (role_id, rule_id) VALUES(2, 1);
INSERT INTO role_rules (role_id, rule_id) VALUES(2, 2);
INSERT INTO role_rules (role_id, rule_id) VALUES(2, 3);

-- Состояние заявки
INSERT INTO states (name) VALUES('new');
INSERT INTO states (name) VALUES('check');
INSERT INTO states (name) VALUES('done');

-- Категории заявки
INSERT INTO categories (name) VALUES('Категория1');
INSERT INTO categories (name) VALUES('Категория2');
INSERT INTO categories (name) VALUES('Категория3');
INSERT INTO categories (name) VALUES('Категория4');

-- Заявки
INSERT INTO items (name, user_id, category_id, state_id) VALUES('Заявка1', 1, 1, 1);
INSERT INTO items (name, user_id, category_id, state_id) VALUES('Заявка2', 1, 2, 2);
INSERT INTO items (name, user_id, category_id, state_id) VALUES('Заявка3', 2, 3, 3);
INSERT INTO items (name, user_id, category_id, state_id) VALUES('Заявка4', 2, 4, 1);

-- Комментарии заявок
INSERT INTO comments (comment, item_id) VALUES('Комментарий1 к заявке1', 1);
INSERT INTO comments (comment, item_id) VALUES('Комментарий2 к заявке1', 1);
INSERT INTO comments (comment, item_id) VALUES('Комментарий1 к заявке2', 2);
INSERT INTO comments (comment, item_id) VALUES('Комментарий2 к заявке2', 2);
INSERT INTO comments (comment, item_id) VALUES('Комментарий1 к заявке3', 3);
INSERT INTO comments (comment, item_id) VALUES('Комментарий2 к заявке3', 3);
INSERT INTO comments (comment, item_id) VALUES('Комментарий1 к заявке4', 4);

-- Приложенные файлы
INSERT INTO attachs (attach, item_id) VALUES('Attachment1 к заявке1', 1);
INSERT INTO attachs (attach, item_id) VALUES('Attachment2 к заявке1', 1);


