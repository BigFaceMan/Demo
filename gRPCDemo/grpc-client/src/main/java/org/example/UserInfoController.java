package org.example;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.rpc.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @GrpcClient("grpc-server")
    TestServiceGrpc.TestServiceBlockingStub testServiceBlockingStub;

    @RequestMapping("getUser")
    public String getUser() {
        log.info("调用远程服务~");
        ApiOut apiOut = testServiceBlockingStub.checkUser(ApiIn.newBuilder().setAge(20).setCode("1024").build());
        String ret = apiOut.getSuccess() ? "success" : "failed";
        return ret;
    }

}

