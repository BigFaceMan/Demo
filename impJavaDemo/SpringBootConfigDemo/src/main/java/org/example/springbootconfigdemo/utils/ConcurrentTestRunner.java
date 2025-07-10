package org.example.springbootconfigdemo.utils;

import org.example.springbootconfigdemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class ConcurrentTestRunner implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
//        MVCC  update加锁
//
//        List<Runnable> taskList = new ArrayList<>();
//        int num = 5;
//        for (int i = 0; i < num; i ++) {
//            taskList.add(() -> {
//                try {
//                    accountService.deductBalance(1L, 1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//        for (Runnable task : taskList) {
//            executor.submit(task);
//        }
////        executor.submit(task1);
//////        Thread.sleep(500);
////        executor.submit(task2);
//

//        事务失败
//        Runnable task1 = () -> {
//            try {
//                accountService.deductBalanceFailed(1L, 1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        };
//        executor.submit(task1);

        Runnable task1 = () -> {
            try {
                accountService.deductBalanceRR(1L, 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task2 = () -> {
            try {
                accountService.deductBalanceRR(1L, 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
