# architect-adventure
《架构探险-从零开始写java-web框架》阅读笔记，希望借助于这本书理解spring框架中的ioc与aop原理。

## 202007
### 2020-07-19 
借助ThreadLocal封装DbHelper

### 2020-07-20 
1. 使用WebServlet实现接口开发工作，这种开发方式一个接口就要对应一个servlet，随着业务的扩涨，servlet也会随之增多。这就诞生了后来的contreller，controller就是将同一个业务模块的servlet合并到一个类中，此类中包含了若干方法，每个方法处理一个特定的请求。
而后续我们需要做的内容就是封装这样一个controller。
2. 搭建一个smart-framework框架，实现类似于controller，service，resource等注解的功能。