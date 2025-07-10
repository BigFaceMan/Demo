package org.example;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mapper.UserMapper;

import java.io.InputStream;

public class MyBatisTest {
    public static void main(String[] args) throws Exception {
        // 1. 读取配置文件
        InputStream input = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(input);

        // 2. 获取 session
        try (SqlSession session = factory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 3. 插入用户
            User newUser = new User();
            newUser.setName("Alice");
            newUser.setAge(22);
            mapper.insertUser(newUser);
            System.out.println("插入后 ID：" + newUser.getId());

            // 4. 查询用户
            User user = mapper.selectUserById(newUser.getId());
            System.out.println("查询结果：" + user);
        }
    }
}
