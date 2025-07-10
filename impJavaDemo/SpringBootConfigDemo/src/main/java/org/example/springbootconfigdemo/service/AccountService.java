package org.example.springbootconfigdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public boolean deductBalance(Long accountId, int amount) throws InterruptedException {
        int blance = jdbcTemplate.queryForObject(
                "SELECT balance FROM account WHERE id = ?",
                Integer.class, accountId
        );
        System.out.println(Thread.currentThread().getName() + " 查询账户余额: " + blance);

        int affected = jdbcTemplate.update(
                "UPDATE account SET balance = balance - ? WHERE id = ? AND balance >= ?",
                amount, accountId, amount
        );

        System.out.println(Thread.currentThread().getName() + " 扣款 " + amount + (affected > 0 ? " ✅ 成功" : " ❌ 失败"));
        blance = jdbcTemplate.queryForObject(
                "SELECT balance FROM account WHERE id = ?",
                Integer.class, accountId
        );
        System.out.println(Thread.currentThread().getName() + " 操作后 查询账户余额: " + blance);

        Thread.sleep(1000); // 测试MVCC
        blance = jdbcTemplate.queryForObject(
                "SELECT balance FROM account WHERE id = ?",
                Integer.class, accountId
        );
        System.out.println(Thread.currentThread().getName() + " sleep后 查询账户余额: " + blance);

        System.out.println(Thread.currentThread().getName() + " submit transaction");

        return affected > 0;
    }

    @Transactional
    public boolean deductBalanceFailed(Long accountId, int amount) throws InterruptedException {
        int balance = jdbcTemplate.queryForObject(
                "SELECT balance FROM account WHERE id = ?",
                Integer.class, accountId
        );
        System.out.println(Thread.currentThread().getName() + " 查询账户余额: " + balance);

        for (int i = 0; i < 10; i ++) {
            int affected = jdbcTemplate.update(
                    "UPDATE account SET balance = balance - ? WHERE id = ? AND balance >= ?",
                    amount, accountId, amount
            );

            if (affected == 0) {
                System.out.println(Thread.currentThread().getName() + " ❌ 扣款失败，余额不足，抛出异常进行回滚！");
                throw new RuntimeException("余额不足，事务回滚");
            }

            balance = jdbcTemplate.queryForObject(
                    "SELECT balance FROM account WHERE id = ?",
                    Integer.class, accountId
            );
            System.out.println(Thread.currentThread().getName() + " 第 " + i +  "次 扣款成功后 查询账户余额: " + balance);
        }

        Thread.sleep(1000); // 模拟事务未提交期间，其他线程可能读到旧快照
        System.out.println(Thread.currentThread().getName() + " ✅ 提交事务");

        return true;
    }
    @Transactional
    public boolean deductBalanceRR(Long accountId, int amount) throws InterruptedException {
        int balance = jdbcTemplate.queryForObject(
                "SELECT balance FROM account WHERE id = ? for update",
                Integer.class, accountId
        );
        System.out.println(Thread.currentThread().getName() + " 查询账户余额: " + balance);
        Thread.sleep(1000); // 模拟事务未提交期间，其他线程可能读到旧快照
        if (balance >= 1000) {
            int affected = jdbcTemplate.update(
                    "UPDATE account SET balance = balance - ? WHERE id = ?",
                    amount, accountId
            );
        }
        Thread.sleep(1000); // 模拟事务未提交期间，其他线程可能读到旧快照
        System.out.println(Thread.currentThread().getName() + " ✅ 提交事务");
        return true;
    }
}
/*
*
余额1000 验证MVCC
pool-2-thread-1 查询账户余额: 1000
pool-2-thread-1 扣款 1000 ✅ 成功
pool-2-thread-1 操作后 查询账户余额: 0
pool-2-thread-2 查询账户余额: 1000
pool-2-thread-1 submit transaction
pool-2-thread-2 扣款 1000 ❌ 失败
pool-2-thread-2 操作后 查询账户余额: 1000
pool-2-thread-2 submit transaction

余额20000 验证update会加锁
pool-2-thread-3 查询账户余额: 20000
pool-2-thread-1 查询账户余额: 20000
pool-2-thread-2 查询账户余额: 20000
pool-2-thread-5 查询账户余额: 20000
pool-2-thread-1 扣款 1000 ✅ 成功
pool-2-thread-1 操作后 查询账户余额: 19000
pool-2-thread-4 查询账户余额: 20000
pool-2-thread-1 sleep后 查询账户余额: 19000
pool-2-thread-1 submit transaction
pool-2-thread-2 扣款 1000 ✅ 成功
pool-2-thread-2 操作后 查询账户余额: 18000
pool-2-thread-2 sleep后 查询账户余额: 18000
pool-2-thread-2 submit transaction
pool-2-thread-3 扣款 1000 ✅ 成功
pool-2-thread-3 操作后 查询账户余额: 17000
pool-2-thread-3 sleep后 查询账户余额: 17000
pool-2-thread-3 submit transaction
pool-2-thread-5 扣款 1000 ✅ 成功
pool-2-thread-5 操作后 查询账户余额: 16000
pool-2-thread-5 sleep后 查询账户余额: 16000
pool-2-thread-5 submit transaction
pool-2-thread-4 扣款 1000 ✅ 成功
pool-2-thread-4 操作后 查询账户余额: 15000
pool-2-thread-4 sleep后 查询账户余额: 15000
pool-2-thread-4 submit transaction

deductBalanceFailed  验证事务失败回滚
pool-2-thread-1 查询账户余额: 9000
pool-2-thread-1 第 0次 扣款成功后 查询账户余额: 8000
pool-2-thread-1 第 1次 扣款成功后 查询账户余额: 7000
pool-2-thread-1 第 2次 扣款成功后 查询账户余额: 6000
pool-2-thread-1 第 3次 扣款成功后 查询账户余额: 5000
pool-2-thread-1 第 4次 扣款成功后 查询账户余额: 4000
pool-2-thread-1 第 5次 扣款成功后 查询账户余额: 3000
pool-2-thread-1 第 6次 扣款成功后 查询账户余额: 2000
pool-2-thread-1 第 7次 扣款成功后 查询账户余额: 1000
pool-2-thread-1 第 8次 扣款成功后 查询账户余额: 0
pool-2-thread-1 ❌ 扣款失败，余额不足，抛出异常进行回滚！
*
deductBalanceRR 验证事务未提交期间，其他线程可能读到旧快照
pool-2-thread-2 查询账户余额: 1000
pool-2-thread-1 查询账户余额: 1000
pool-2-thread-2 ✅ 提交事务
pool-2-thread-1 ✅ 提交事务
for update
pool-2-thread-1 查询账户余额: 1000
pool-2-thread-1 ✅ 提交事务
pool-2-thread-2 查询账户余额: 0
pool-2-thread-2 ✅ 提交事务
*
*
*
*
* */
