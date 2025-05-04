-- Уровень изоляции по умолчанию
SET default_transaction_isolation = 'repeatable read';
SHOW default_transaction_isolation;

-- Уровень изоляции для текущей сессии
SET transaction_isolation = 'serializable';
SHOW transaction_isolation;

-- Уровень изоляции для конкретной транзакции
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN;
-- другие операции в рамках транзакции
COMMIT;
