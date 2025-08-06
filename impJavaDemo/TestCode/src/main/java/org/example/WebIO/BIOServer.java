package org.example.WebIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ssc = new ServerSocket(8080);
        while (true) {
            System.out.println("server listen...");
            Socket accept = ssc.accept();
            System.out.println("客户端连接 : " + accept.getInetAddress() + " : " + accept.getPort());
            byte[] buf = new byte[1024];
            int len = 0;
            InputStream inputStream = accept.getInputStream();
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                System.out.println("收到消息 : " + (new String(buf, 0, len)));
            }
            System.out.println("客户端断开连接");
        }
    }
}
