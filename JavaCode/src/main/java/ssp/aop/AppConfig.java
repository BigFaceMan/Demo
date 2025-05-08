package ssp.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "ssp.aop")  // 自动扫描组件
@EnableAspectJAutoProxy  // 启用 AOP
public class AppConfig {
}
