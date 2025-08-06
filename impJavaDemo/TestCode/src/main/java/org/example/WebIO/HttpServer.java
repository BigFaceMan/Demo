package org.example.WebIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ssc = new ServerSocket(8080);
        while (true) {
            Socket sc = ssc.accept();
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                    OutputStream out = sc.getOutputStream();

                    // 读取请求头
                    String line;
                    while (!(line = reader.readLine()).isEmpty()) {
                        System.out.println(line); // 打印请求头
                    }

                    // 写入 HTTP 响应
                    String responseBody = "Hello, world!";
                    String response =
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: text/plain\r\n" +
                                    "Content-Length: " + responseBody.length() + "\r\n" +
                                    "\r\n" +
                                    responseBody;

                    out.write(response.getBytes());
                    out.flush();
                    sc.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start(); // 每个请求一个线程（BIO模型）
        }
    }
}
