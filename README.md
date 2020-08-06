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

### 2020-07-23
1. 三种代理模式：静态代理，JDK动态代理，CGLib动态代理，JDK动态代理与CGLib动态代理的区别在于CGLib类库可以代理没有接口的类，通过加载代理类的class文件，通过其字节码生成子类来处理。

### 2020-07-29
1. Spring AOP的前置增强、后置增强、环绕增强的编程式实现，将部分功能横切到代码中，而不是写死在代码中。
2. 环绕增强有点类似于将前置增强与后置增强合并起来，几种增强的原理有点类似于方法层面的拦截器。
3. 抛出增强，可以拦截到抛出的异常信息，方法，参数，目标对象，异常对象等。
4. 引入增强，某个类实现了A接口，但是没有实现B接口，但是该类还是可以调用B接口的方法。
5. 引入增强使用遇到的问题，希望后面的学习中可以解决下面的疑问
* 原有的环绕增强失效了。
* 同一段代码在debug模式与run模式打印的内容不同（在代码中留了todo）。

### 2020-08-03
1. 各类增强类型所对应的解决方案

| 增强类型 | 基于AOP接口 | 基于AOP注解 | 基于`<aop:config>`配置 |
|---|---|---|---|
|Before Advice(前置增强)| MethodBeforeAdvice | @Before | `<aop:before>` |
|After Advice(后置增强)| AfterReturningAdvice | @After | `<aop:after>` |
|Around Advice(环绕增强)| MethodInterceptor | @Around | `<aop:around>` |
|Throws Advice(抛出增强)| ThrowAdvice | @AfterThrowing | `<aop:aftaer-throwing>` |
|Introduction Advice(引入增强)| DelegatingIntroductionInterceptor | @DeclareParents | `<aop:declare-parents>` |

2. 既然CGLib可以代理任何类了，那为什么还要用JDK的动态代理呢？
根据实际项目经验得知，CGLib创建代理的速度比较慢，但创建代理后运行的速度却非常快，而JDK动态代理正好相反。
如果在运行的时候不断的用CGLib去创建代理，系统的性能就会大打折扣，所以建议一般在系统初始化的时候用CGLib去创建代理，并放入spring的ApplicationContext中以备后用。
3. 定义一个切面注解Aspect，后续使用框架的时候可以通过此注解拦截方法。
4. 定义代理接口Proxy，接口中只有一个方法doProxy（ProxyChain proxyChain），这里定义了一个链式代理，ProxyChain对象封装了doProxyChain方法，通过这个方法再去执行doProxy方法，将多个代理通过一条链子串起来，一个个的去执行，执行顺序取决于添加到链上的先后顺序。
5. Proxy接口的实现类AspectProxy是一个抽象类，抽象类中定义了一些公共方法，而抽象类与接口的区别之一，就是抽象类的方法不需要全部实现，这样就可以在子类中有选择性的进行实现。这是一种模板设计模式。
Aspect注解与AspectProxy抽象类使用示例：
```java
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("-----begin------");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        System.out.println("-----end------");
    }
}
```
### 2020-08-06
1. 获取所有继承AspectProxy的代理类，找出其中带有Aspect注解的代理类，如上面的ControllerAspect就是一个带有Aspect注解的类。
2. 判断ControllerAspect类所带有的Aspect注解中的注解类属性是否是Aspect注解，如上面ControllerAspect类所带的Aspect注解的注解类属性是Controller注解。
3. 获取带有Controller注解的所有类，这样就形成了一个代理类及其目标类集合之间的映射关系。即ControllerAspect代理类与所有带有Controller注解的目标类集合之间的映射关系。
4. 再次循环反转，找出目标类与代理对象列表之间的映射关系。比如一个UserController使用了Controller注解，那么UserController就是目标类，ControllerAspect代理类会生成一个代理对象，而继承AspectProxy的类可能会存在多个，最终就会生成一个目标对象与代理对象之间的映射关系。
5. 最终生成了一个目标类UserController的代理链（多个代理）对象ProxyChain。每个代理有自己的横切逻辑。
6. 将这个代理对象放入Bean map中，最后由ioc进行依赖注入。后面再使用UserController中的方法时候就会生成一个代理对象，并执行代理对象的横切逻辑。
