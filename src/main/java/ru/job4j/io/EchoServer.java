package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     OutputStream output = socket.getOutputStream()) {
                    output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    String requestLine = input.readLine();
                    if (requestLine != null && requestLine.startsWith("GET")) {
                        System.out.println(requestLine);
                        String[] params = requestLine.split(" ");
                        if (params.length > 1) {
                            String[] query = params[1].split("=");
                            if ("Bye".equals(query[1])) {
                                server.close();
                            }
                        }
                    }
                    output.flush();
                } catch (IOException e) {
                    System.err.println("Ошибка обработки соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка запуска сервера!", e);
        }
    }
}
