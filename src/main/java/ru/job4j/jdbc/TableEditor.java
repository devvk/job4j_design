package ru.job4j.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private final Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    /**
     * Инициализирует соединение с базой данных.
     */
    private void initConnection() {
        String url = properties.getProperty("jdbc.url");
        String login = properties.getProperty("jdbc.login");
        String password = properties.getProperty("jdbc.password");
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выполняет DDL-запрос.
     *
     * @param sql SQL-запрос для выполнения
     * @throws RuntimeException если произошла ошибка при выполнении запроса
     */
    private void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создаёт пустую таблицу без столбцов с указанным именем.
     *
     * @param tableName имя создаваемой таблицы
     */
    public void createTable(String tableName) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s ();", tableName);
        execute(sql);
    }

    /**
     * Удаляет таблицу по указанному имени.
     *
     * @param tableName имя таблицы, которую нужно удалить
     */
    public void dropTable(String tableName) {
        String sql = String.format("DROP TABLE IF EXISTS %s;", tableName);
        execute(sql);
    }

    /**
     * Добавляет столбец в указанную таблицу.
     *
     * @param tableName  имя таблицы
     * @param columnName имя добавляемого столбца
     * @param type       тип данных столбца (например, TEXT, INT)
     */
    public void addColumn(String tableName, String columnName, String type) {
        String sql = String.format("ALTER TABLE %s ADD COLUMN %s %s;", tableName, columnName, type);
        execute(sql);
    }

    /**
     * Удаляет столбец из указанной таблицы.
     *
     * @param tableName  имя таблицы
     * @param columnName имя удаляемого столбца
     */
    public void dropColumn(String tableName, String columnName) {
        String sql = String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, columnName);
        execute(sql);
    }

    /**
     * Переименовывает столбец в указанной таблице.
     *
     * @param tableName     имя таблицы
     * @param columnName    текущее имя столбца
     * @param newColumnName новое имя столбца
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) {
        String sql = String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;", tableName, columnName, newColumnName);
        execute(sql);
    }

    /**
     * Возвращает схему таблицы (имена и типы столбцов) в виде отформатированной строки.
     *
     * @param tableName имя таблицы
     * @return строка с именами и типами столбцов таблицы
     * @throws Exception если произошла ошибка при выполнении запроса
     */
    public String getTableScheme(String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s| %-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM %s LIMIT 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s| %-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    /**
     * Закрывает соединение с базой данных, если оно было открыто.
     *
     * @throws Exception если произошла ошибка при закрытии соединения
     */
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Загружает файл с настройками.
     *
     * @return настройки из файла.
     */
    private static Properties loadProperties() {
        try (InputStream in = TableEditor.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл с настройками.", e);
        }
    }

    public static void main(String[] args) {
        Properties config = loadProperties();
        try (TableEditor tableEditor = new TableEditor(config)) {
            String tableName = "demo_table";
            String columnName = "age";
            String type = "INT";
            String newColumnName = "id";

            tableEditor.dropTable(tableName);
            tableEditor.createTable(tableName);
            System.out.println(tableEditor.getTableScheme(tableName));

            tableEditor.addColumn(tableName, columnName, type);
            System.out.println(tableEditor.getTableScheme(tableName));

            tableEditor.renameColumn(tableName, columnName, newColumnName);
            System.out.println(tableEditor.getTableScheme(tableName));

            tableEditor.dropColumn(tableName, newColumnName);
            System.out.println(tableEditor.getTableScheme(tableName));

            tableEditor.dropTable(tableName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
