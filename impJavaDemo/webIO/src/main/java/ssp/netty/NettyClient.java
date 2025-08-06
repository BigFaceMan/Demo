package ssp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                        System.out.println("channel handle msg : " + s);
                                    }
                                });
                    }
                });
        ChannelFuture connect = bootstrap.connect("localhost", 8080);
        connect.addListener((f) -> {
            if (f.isSuccess()) {
                System.out.println("成功连接到了8080服务器");
                EventLoop eventLoop = connect.channel().eventLoop();
                eventLoop.scheduleAtFixedRate(() -> {
                    connect.channel().writeAndFlush("hello " + System.currentTimeMillis() + "\n");
                    System.out.println("client send");
                }, 0, 1, TimeUnit.SECONDS);
            }
        });
        while (true) {
            System.out.println("main Thread here");
            Thread.sleep(1000);
        }
    }
}
