package org.example;

import lombok.extern.slf4j.Slf4j;
import io.grpc.stub.StreamObserver;
import org.example.rpc.*;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void checkUser(ApiIn request, StreamObserver<ApiOut> responseObserver) {
        ApiOut rep = ApiOut.newBuilder().setAuth("admin").setSuccess(true).build();
        // 向客户端输出数据
        responseObserver.onNext(rep);
        // 结束输出
        responseObserver.onCompleted();
        log.info("服务启动成功~");
    }

}

