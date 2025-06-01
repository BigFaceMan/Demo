# 一些技术的小demo

# AI

## FunctionCall

LLM提供的Api接口参数，提供模型调用外部函数的能力



# JavaCode

## IO



- BIO：阻塞IO

- NIO：非阻塞IO
  - NIO底层基于epoll实现	

## 同步

- synchronous：交替打印ab

  - abPrintSpin：自旋锁实现

  - abPrintCondition：条件变量实现



## AQS Lock

- 利用原子变量的CAS操作实现的阻塞、非阻塞的可重入锁
  - SpinLock: 自旋锁
  - AQSLock：阻塞锁
    - 将要被阻塞的Thread用双向链表存储
    - 利用LockSupport.park() 将Thread阻塞
    - 利用 unlock的时候不改变head节点只做unpark + 两次check 再将Thread park，避免了链表加入节点时非原子操作的问题

- Time Test
  T: 5
  Thread: 100
  opt: 10

- SpinLock
  ave cost : 397.2
- AQSLock
  ave cost : 36.0

##  juc相关工具

- CountDownLatch
- Condition

## 线程池

- 核心Thread 阻塞去读blockqueue中的Runnable task，然后执行
- 非核心Thread poll 定时读，如果一段时间没读到就结束
- 因此参数有：
  - CoreSize
  - MaxSize
  - TimeOut
  - TimeUnit
  - BlockQueue
  - RejectHandle



## 定时任务

- 能够定时执行一些任务，类似于springboot的注解@Scheduled

  - Pool：线程池 来执行定时任务
  - Job：定时任务对象 
    - task、执行的时间、时间间隔

  - Trigger：用于管理什么时候将任务加入线程池的对象
    - 判断某个task是否要执行了
    - 如果不用执行就sleep到对应的事件，用LockSupport来park，因为sleep需要实现中断不优雅
      - 我们必须将job按照事件排序，利用小根堆即可
    - 执行完再将任务放入小根堆
    - 如果队列是空没必要空转，将线程park，加入一个任务的时候再去唤醒线程
    - 注意虚假唤醒的情况，用while 来check，而不是用if 只check一次



## miniSpring



### Srping

**tag:  负责 造对象、拿对象**

- 创建对象
	- 识别带有@Component注解的对象
- 依赖注入
	- 识别带有@Autowired的字段
	- 避免循环注入
	- 对象创建分为两个部分
		- new 一个对象
		- 给需要注入的对象赋值
		- 因此二级缓存防止循环依赖
- 初始化后自动调用某些函数
	- 识别实例中带有@PostConstruct的方法
	- 针对于具体实例自动调用的方法
- 生命周期函数
	- 实现了BeanPostProcessor接口的类
	- 先create这些类，然后别的类创建的时候，用这些类走一下初始化
	- 分为beforeInitializeBean、afterInitializeBean两个待实现的方法
	- 提供针对于所有方法的前置后置方法

### SpringMVC



**一套自动注入的MVC框架**

spring -> MVC

- 自动注入一个服务器
  - Tomcat web服务器，建立请求与servlet的对应关系
  - 使用一个DispatcherServlet 匹配所有请求，在自己写的类内做路由    创建Servlet（名字与类映射） 、加入Mapping（名字与pattern映射）
    - 建立 url 与 servlet（路由内是handler）的对应关系
      - 加入BeanPostConstruct注解，在对象初始化后，将有Controller注解的对象都解析其有没有Handler
      - 如果有RequestMapping，则该方法是一个Handdler，
      - WebHandler 存储 与handler 相关的信息，method、resulteTyep
      - 使用map来存储 pattern 和 webHandler的对应关系
- 接收请求
  - 处理参数
    - 将req中的参数 变成 mothod 需要的参数
    - 解析method的parameter的注解的名字
- 处理请求
  - 使用invoke调用
- 返回数据
  - 根据resulteType来处理handler的返回类型
    - JSON
      - 有@ResponseBody注解得到
    - LOCAL
      - 本地资源
      - 用ModelAndView 动态渲染
    - HTML
      - 字符串



## Aop

面向切面编程

- **Aspect**：切面，只是一个概念，没有具体的接口或类与之对应，是 Join point，Advice 和 Pointcut 的一个统称。

  - 要实现的功能类

- **Advice**：通知，即我们定义的一个切面中的横切逻辑，有“around”，“before”和“after”三种类型。在很多的 AOP 实现框架中，Advice 通常作为一个拦截器，也可以包含许多个拦截器作为一条链路围绕着 Join point 进行处理。

  - 功能类中的方法

- **Target object**：目标对象，就是被代理的对象。

- **AOP proxy**：AOP 代理，指在 AOP 实现框架中**实现切面协议的对象**（代理对象）。在 Spring AOP 中有两种代理，分别是 JDK 动态代理和 CGLIB 动态代理。

- **Join point**：连接点，指程序执行过程中的一个点，例如方法调用、异常处理等。在 Spring AOP 中，仅支持方法级别的连接点。

  - **程序执行过程中的某个点，通常是函数调用**

- **Pointcut**：切入点，用于匹配连接点，一个 AspectJ 中包含哪些 Join point 需要由 Pointcut 进行筛选。

  - **对连接点的筛选**  具体执行的那个函数

  

  

## jvmTest

虚拟机测试

- 虚拟机文件结构, ClassFileStruct
- 类加载, ClassLoaderTest

# DesignPattern

23个设计模式

## 创建型模式

单例模式、工厂模式、抽象工厂模式



## 结构性模式

适配器模式、装饰器模式、代理模式

## 行为型模式

观察者模式、策略模式、命令模式

