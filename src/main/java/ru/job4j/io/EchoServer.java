package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class EchoServer {
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) {
        new EchoServer().startServer();
    }

    public void startServer() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.printf("Start server on port %s: [OK]\n", SERVER_PORT);
            while (!server.isClosed()) {
                handleClient(server);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Start server on port %s: [FAILURE]\n", SERVER_PORT), e);
        }
    }

    private void handleClient(ServerSocket server) {
        try (Socket socket = server.accept();
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream output = socket.getOutputStream()
        ) {
            String request = input.readLine();
            System.out.println("Request: " + request);

            if (request != null && request.startsWith("GET")) {
                processRequest(request, output, server);
            }
            output.flush();
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    /**
     * Process HTTP-request.
     *
     * @param request request line.
     * @param output  output.
     * @param server  server.
     * @throws IOException exception.
     */
    private void processRequest(String request, OutputStream output, ServerSocket server) throws IOException {
        Map<String, String> params = extractRequestParams(request);

        if (params.containsKey("msg") && params.get("msg").equalsIgnoreCase("hello")) {
            output.write(getResponse("Hello, dear friend."));
        } else if (params.containsKey("msg") && params.get("msg").equalsIgnoreCase("exit")) {
            output.write(getResponse("Stop server."));
            System.out.printf("Stop server on port %s: [OK]\n", SERVER_PORT);
            server.close();
        } else if (params.containsKey("msg") && params.get("msg").equalsIgnoreCase("what")) {
            output.write(getResponse("What?"));
        } else {
            output.write(getResponse("What"));
        }
    }

    private byte[] getResponse(String msg) {
        return String.format("HTTP/1.1 200 OK\r\n\r\n %s", msg).getBytes();
    }

    /**
     * Extract parameters from request.
     *
     * @param request request.
     * @return map with parameters.
     */
    private Map<String, String> extractRequestParams(String request) {
        Map<String, String> result = new HashMap<>();
        String[] parts = request.split(" ");
        if (parts.length > 1) {
            String query = parts[1];
            if (query.contains("?")) {
                String[] params = query.substring(query.indexOf("?") + 1).split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        result.put(keyValue[0], keyValue[1]);
                    } else {
                        System.err.println("Incorrect parameter: " + param);
                    }
                }
            }
        }
        return result;
    }

}
