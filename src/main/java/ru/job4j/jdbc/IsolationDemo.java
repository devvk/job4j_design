package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IsolationDemo {
    private static final String URL = "jdbc:postgresql://localhost:5432/deleteme";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static void main(String[] args) throws Exception {

        Connection tx1 = DriverManager.getConnection(URL, USER, PASSWORD);
        Connection tx2 = DriverManager.getConnection(URL, USER, PASSWORD);

        tx1.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        tx2.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        tx1.setAutoCommit(false);
        tx2.setAutoCommit(false);

        // Транзакция 1 читает баланс
        PreparedStatement s1 = tx1.prepareStatement("SELECT balance FROM accounts WHERE id=1");
        ResultSet r1 = s1.executeQuery();
        r1.next();
        System.out.println("TX1: прочитал баланс = " + r1.getBigDecimal(1));

        // Транзакция 2 обновляет баланс
        PreparedStatement u2 = tx2.prepareStatement("UPDATE accounts SET balance = balance + 100 WHERE id=1");
        u2.executeUpdate();
        tx2.commit();
        System.out.println("TX2: добавил 100 и закоммитил");

        // Транзакция 1 снова читает
        r1 = s1.executeQuery();
        r1.next();
        System.out.println("TX1: повторно прочитал баланс = " + r1.getBigDecimal(1));

        tx1.commit();
        tx1.close();
        tx2.close();
    }

}

