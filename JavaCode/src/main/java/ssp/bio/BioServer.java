package ssp.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) throws Exception {
                    ServerSocket socketListen = new ServerSocket(8080);
                    while (true) {
                        Socket socket = socketListen.accept();
                        InputStream inputStream = socket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            String msg = new String(buffer, 0, len);
                            System.out.println("msg : " + msg);
            }
            System.out.println("客户端断开连接");
        }
    }

}
