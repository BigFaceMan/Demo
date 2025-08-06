package ssp.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class AioServer {
    public static void main(String[] args) throws Exception {
        // 创建异步服务器通道
        AsynchronousServerSocketChannel serverChannel =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress("localhost", 8080));

        System.out.println("AIO Server started at port 8080...");

        // 使用 CountDownLatch 保证主线程不退出
        CountDownLatch latch = new CountDownLatch(1);

        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Void attachment) {
                try {
                    System.out.println("客户端连接成功：" + client.getRemoteAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 再次接收其他客户端连接
                serverChannel.accept(null, this);

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        if (result == -1) {
                            try {
                                client.close();
                                System.out.println("客户端断开连接");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        attachment.flip();
                        String msg = StandardCharsets.UTF_8.decode(attachment).toString();
                        System.out.println("收到消息：" + msg);

                        // 回显写回客户端
                        ByteBuffer echoBuffer = ByteBuffer.wrap(("Echo: " + msg).getBytes(StandardCharsets.UTF_8));
                        client.write(echoBuffer, echoBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer buffer) {
                                if (buffer.hasRemaining()) {
                                    client.write(buffer, buffer, this); // 继续写
                                } else {
                                    // 再次准备读取
                                    ByteBuffer newBuffer = ByteBuffer.allocate(1024);
                                    client.read(newBuffer, newBuffer, this);
                                }
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer buffer) {
                                try {
                                    client.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            client.close();
                            System.out.println("读取失败，客户端断开连接");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("接受连接失败：" + exc.getMessage());
                latch.countDown();
            }
        });

        latch.await(); // 阻塞主线程
    }
}
