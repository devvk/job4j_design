-- Уровень изоляции по умолчанию.
SET default_transaction_isolation = 'repeatable read';
SHOW default_transaction_isolation;

-- Уровень изоляции для текущей сессии.
SET transaction_isolation = 'serializable';
SHOW transaction_isolation;

-- Уровень изоляции для текущей транзакции.
-- BEGIN ISOLATION LEVER READ UNCOMMITTED;
-- BEGIN ISOLATION LEVER READ COMMITTED;
-- BEGIN ISOLATION LEVER REPEATABLE READ;
-- BEGIN ISOLATION LEVER READ SERIALIZABLE;
BEGIN;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- другие операции в рамках транзакции
COMMIT;
