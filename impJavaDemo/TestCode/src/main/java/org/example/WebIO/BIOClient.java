package org.example.WebIO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        BIOClient bioClient = new BIOClient();
        Thread tom = new Thread(() -> {
            try {
                bioClient.sayHello();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "tom");

        Thread jerry = new Thread(() -> {
            try {
                bioClient.sayHello();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "jerry");
        tom.start();
        jerry.start();
        tom.join();
        jerry.join();
    }
    public void sayHello() throws IOException, InterruptedException {
        Socket sc = new Socket();
        sc.connect(new InetSocketAddress("localhost", 8080));
        OutputStream outputStream = sc.getOutputStream();
        for (int i = 0; i < 10; i ++) {
            outputStream.write((Thread.currentThread().getName() + " say : hello " + i).getBytes());
            outputStream.flush();
            Thread.sleep(1000);
        }
        sc.close();
    }
}
