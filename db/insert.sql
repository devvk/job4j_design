-- Роли
insert into roles (name) values('user');
insert into roles (name) values('admin');

-- Пользователи
insert into users (name, email, role_id) values('Alex', 'alex@mail.com', 1);
insert into users (name, email, role_id) values('Max', 'max@mail.com', 2);

-- Права
insert into rules (name) values('can_create');
insert into rules (name) values('can_edit');
insert into rules (name) values('can_delete');

-- Права ролей
-- user
insert into role_rules (role_id, rule_id) values(1, 1);
-- admin
insert into role_rules (role_id, rule_id) values(2, 1);
insert into role_rules (role_id, rule_id) values(2, 2);
insert into role_rules (role_id, rule_id) values(2, 3);

-- Состояние заявки
insert into states (name) values('new');
insert into states (name) values('check');
insert into states (name) values('done');

-- Категории заявки
insert into categories (name) values('Категория1');
insert into categories (name) values('Категория2');
insert into categories (name) values('Категория3');
insert into categories (name) values('Категория4');

-- Заявки
insert into items (name, user_id, category_id, state_id) values('Заявка1', 1, 1, 1);
insert into items (name, user_id, category_id, state_id) values('Заявка2', 1, 2, 2);
insert into items (name, user_id, category_id, state_id) values('Заявка3', 2, 3, 3);
insert into items (name, user_id, category_id, state_id) values('Заявка4', 2, 4, 1);

-- Комментарии заявок
insert into comments ("comment", item_id) values('Комментарий1 к заявке1', 1);
insert into comments ("comment", item_id) values('Комментарий2 к заявке1', 1);
insert into comments ("comment", item_id) values('Комментарий1 к заявке2', 2);
insert into comments ("comment", item_id) values('Комментарий2 к заявке2', 2);
insert into comments ("comment", item_id) values('Комментарий1 к заявке3', 3);
insert into comments ("comment", item_id) values('Комментарий2 к заявке3', 3);
insert into comments ("comment", item_id) values('Комментарий1 к заявке4', 4);

-- Приложенные файлы
insert into attachs (attach, item_id) values('Attachment1 к заявке1', 1);
insert into attachs (attach, item_id) values('Attachment2 к заявке1', 1);
