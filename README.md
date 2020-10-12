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

### 2020-08-12
1. ThreadLocal学习：ThreadLocal其实就是一个容器，用于存放线程的局部变量，不能因为它的命名而错误的理解。
2. 在一个类中使用static成员变量时候，需要考虑线程安全的问题，即多个线程需要独享自己的static成员变量吗？补充一下static修饰变量时候的概念：static修饰的成员变量属于类，不属于某个对象（也就是说多个对象访问或修改static修饰的成员变量时，其中一个对象将static成员变量进行了修改，其它的对象的static成员变量值跟着改变，即多个对象共享同一个static成员变量）
3. ThreadLocal的API：
* public T get(): 从线程局部变量中获取值
* public void set(T value)：将值放入线程局部变量中
* public void remove()：从线程局部变量中移除值（有利于jvm垃圾回收）
* protected T initialValue()：返回局部变量中的初始值（默认为null），initialValue方法是protected，是为了提醒程序员这个方法需要程序员实现，要给这个线程局部变量设置一个初始值
4. ThreadLocal使用案例：
在封装数据库的常用操作时候时长会这样实现，这就导致了多线程的情况下，所有线程共享一个连接，线程1有可能会关闭线程2的连接，从而线程2报错‘connection closed’。
```java
public class DBUtil() {
    private static Connection conn = null;
    //获取连接
    public static Connection getConection(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            
        }
        return conn;
    }
    //关闭连接
    public static void closeConection(){
        try{
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            
        }
    }
}
```
这时候就需要使用ThreadLocal进行封装, 将每个线程之间的connection进行个里，不再互相干扰。
```java
public class DBUtil() {
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
    //获取连接
    public static Connection getConection(){
        Connection conn = connContainer.get();
        try{
            if (conn != null) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,username,password);
            }
        } catch (Exception e) {
            
        } finally {
            connContainer.set(conn);
        }
        return conn;
    }
    //关闭连接
    public static void closeConection(){
        Connection conn = connContainer.get();
        try{
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            
        } finally {
            connContainer.remove(conn);
        }
    }
}
```

### 2020-08-24
1. 增加Transaction注解
2. 增加事务代理机制
3. 疑问点：同一个线程中会有多个事务存在吗？
理论上是没有的，因为事务代理机制中会判断当前线程中事务控制相关逻辑当前未在执行中且方法上加了Transaction注解，如果存在多个事务，就会导致某个事务可以生效，其他事务不能生效。

### 2020-09-01
1. 在框架中添加事务代理机制
2. 增加DispatchServlet处理所有的请求，根据请求方法与请求路径来调用具体的action方法，并返回对应的值。
3. Controller与ServletAPI解耦
因为Controller中是无法调用Servlet API的，因为无法获取request与response这类对象，我们必须在dispatchServlet中将这些对象传递给Controller的Action方法才能拿到这些对象，这也会增加Controller与ServletAPI的耦合。
这里提供一个线程安全的对象，通过它来封装request与response对象，并提供一系列常用的Servlet API，这样就可以在Controller中随时通过该对象操作Request与Response。
这个对象要求一定是线程安全的，每个请求线程独自拥有一个Request与Response对象，不同请求线程之间是隔离的。

### 2020-09-10
1. 增加安全控制子模块smart-plugin-security
在子模块的pom.xml文件中引入了smart-framework的jar包，在引入之前需要在smart-framework项目下执行`mvn install`命令安装jar到本地，不然smart-plugin-security的pom文件会报错。
2. 通过ServletContainerInitializer实现安全框架初始化操作
在web容器启动时为提供给第三方组件机会做一些初始化的工作，例如注册servlet或者filters等，servlet规范中通过ServletContainerInitializer实现此功能。每个框架要使用ServletContainerInitializer就必须在对应的jar包的META-INF/services 目录创建一个名为javax.servlet.ServletContainerInitializer的文件，文件内容指定具体的ServletContainerInitializer实现类，那么，当web容器启动时就会运行这个初始化器做一些组件内的初始化工作。

### 2020-10-09
1. 更新framework模块的databaseHelper方法，增加数据源的使用
2. 增加两个自定义的Realm，SmartJdbcRealm和SmartCustomRealm，前者基于SQL配置的实现，后者用于提供更加灵活的SmartSecurity接口的实现。
3. SmartCustomRealm实现了AuthorizingRealm方法，覆盖父类的doGetAuthenticationInfo用于认证，覆盖父类的doGetAuthorizationInfo用于授权，这就是常见的Authorization和Authentication的区别。

### 2020-10-12
1. 基于shiro自定义jsp标签进行权限控制
2. 继承framework框架中的AspectProxy切面代理抽象类，重写before方法进行前置增强，保证对Controller注解的类进行权限指定的权限校验。