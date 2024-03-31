package main;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Servlet {

    public Servlet(int port){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port),0);
            HttpContext context = server.createContext("/");
            context.setHandler(Servlet::handleRequest);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String filePathString = "src/webpage" + exchange.getRequestURI().getPath();
        Path filePath = Paths.get(filePathString);
        System.out.println(filePathString);
        if (!Files.exists(filePath)) {
            String response = "404 (Not Found)\n";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {

            String mimeType = Files.probeContentType(filePath);

            // Если MIME-тип не определен, установите его в "applicaввtion/octet-stream"
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            // Установка заголовка Content-Type с кодировкой UTF-8
            exchange.getResponseHeaders().set("Content-Type", mimeType + "; charset=UTF-8");

            exchange.sendResponseHeaders(200, Files.size(filePath));
            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(filePath, os);
            }
        }
    }

}
