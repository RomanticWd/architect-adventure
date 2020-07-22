# architect-adventure
《架构探险-从零开始写java-web框架》阅读笔记，希望借助于这本书理解spring框架中的ioc与aop原理。

## 202007
### 2020-07-19 
借助ThreadLocal封装DbHelper

### 2020-07-20 
1. 使用WebServlet实现接口开发工作，这种开发方式一个接口就要对应一个servlet，随着业务的扩涨，servlet也会随之增多。这就诞生了后来的contreller，controller就是将同一个业务模块的servlet合并到一个类中，此类中包含了若干方法，每个方法处理一个特定的请求。
而后续我们需要做的内容就是封装这样一个controller。
2. 搭建一个smart-framework框架，实现类似于controller，service，resource等注解的功能。

### 2020-07-21
1. 定义properties工具类用于读取配置文件
2. 定义类加载工具类用于加载指定class，指定包下的所有class
3. 定义controller，service，inject，action等注解
4. 增加获取应用包下所有Bean类的方法

### 2020-07-22
1. 实现bean容器，将基础应用包下的所有类通过反射全部实例化并存放到一个<bean类，bean实例>映射的大map中。
2. 实现IOC，框架启动的时候会去遍历上面初始化的大map，将其中被某些注解修饰（如inject，resource）的成员变量通过反射初始化。
3. 加载所有controller类，并通过反射获取类中带有action注解的方法，进而获取action注解中的请求表达式，得到请求url与请求method，将此封装成一个request对象。同时将controller与action封装成一个handler对象，最终生成一个<request，handler>映射的大map。
4. 定义一个入口程序，统一加载ClassHelper,BeanHelper,IocHelper,ControllerHelper。
5. 定义一个dispatchServlet请求转发器，通过第四步的入口程序初始化框架中所有的helper，处理所有的请求，从httpServletRequest对象中获取请求方法与请求路径，然后根据第三步中的map从而知道调用哪个controller中的哪个方法。并从httpServletRequest中获取请求参数，并通过反射调用controller中的action注解修饰的方法。