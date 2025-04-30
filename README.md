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

