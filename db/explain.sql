/*
    EXPLAIN возвращает план выполнения запроса, который генерируется планировщиком запросов PostgreSQL

     Чтение 1 страницы (8 КБ):
        последовательное: seq_page_cost = 1.0
        случайное (random access): random_page_cost = 4.0

    Стоимость операций CPU:
        cpu_tuple_cost — обработка одного кортежа (по умолчанию 0.01)
        cpu_index_tuple_cost — обработка кортежа в индексе (по умолчанию 0.005)
        cpu_operator_cost — стоимость одного оператора сравнения/функции (по умолчанию 0.0025)

    cost= стартовая стоимость (время для получения первой строки)
    ..    сколько времени потребуется для возврата всех строк.

*/
CREATE TABLE users (
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username text NOT NULL
);

INSERT INTO users (username)
SELECT 'person' || n
FROM generate_series(1, 1000) AS n;

EXPLAIN SELECT * FROM users ORDER BY username;
/*
    EXPLAIN порядок выполнения — снизу вверх.
   "1. Sort  (cost=66.83..69.33 rows=1000 width=17)
    2. Sort Key: username
    3. ->  Seq Scan on users  (cost=0.00..17.00 rows=1000 width=17)"

    1. Seq Scan on users  (cost=0.00..17.00 rows=1000 width=17)
        Seq Scan — последовательное чтение таблицы users.
        0.00 — начальная стоимость.
        17.00 — полная стоимость чтения таблицы.
        rows=1000 — планировщик ожидает 1000 строк в таблице.
        width=17 — средняя длина одной строки в байтах.

    2. Sort Key: username — Из каждой строки достаёт поле username, по которому сортируем.

    3. Sort (cost=66.83..69.33 rows=1000 width=17):
        После того как ВСЕ строки получены, PostgreSQL делает сортировку по username.
        66.83 — затраты на подготовку к сортировке (включают чтение всех строк и их буферизацию).
        69.33 — полные затраты на сортировку всех 1000 строк и передачу результата.

    ANALYZE — собирает статистику о содержимом таблиц.
    https://postgrespro.ru/docs/postgresql/14/using-explain#USING-EXPLAIN-ANALYZE
*/

EXPLAIN SELECT count(*) FROM users;
-- Данные для таблицы хранятся в пределах 7 страниц
SELECT reltuples, relpages FROM pg_class WHERE relname = 'users';

-- Общая стоимость для Seq Scan:
-- = (relpages * seq_page_cost) + (кол-во_возвращенных_строк * cpu_tuple_cost)
-- = (7 * 1) + (1000 * 0.01)
-- = 7 + 10.00
-- = 17.00

-- Общая стоимость для Aggregate
-- = (стоимость Seq Scan) + (кол-во_обрабат_строк * cpu_operator_cost) + (кол-во_возвр_строк * cpu_tuple_cost)
-- = (17.00) + (1000 * 0.0025) + (1 * 0.01)
-- = 17.00 + 2.50 + 0.01
-- = 19.51

EXPLAIN ANALYZE SELECT * FROM users ORDER BY username;

CREATE INDEX people_username_index ON users(username);

EXPLAIN ANALYZE SELECT * FROM users ORDER BY username;

ANALYZE users;


