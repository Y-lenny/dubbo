### dubbo_源码_SPI

--- 
#### 1. 概述
Dubbo 项目地址：[https://github.com/Y-lenny/dubbo.git](https://github.com/Y-lenny/dubbo.git)
>以上地址是以源地址fork出来，后续源码解析、注释将会基于此进行编辑
#### 1.1 简单介绍
SPI全称Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的API，它可以用来启用框架扩展和替换组建。
整体机制图如下：spi 整体机制图.png
> - Java SPI是基于"基于接口的编程+策略模式+配置文件"组合实现的动态加载机制。
> - Java SPI就是提供这样的一个机制：为某个接口寻找服务实现的机制。有点类似IOC的思想，就是将装配的控制权移到程序之外，在模块化设计中这个机制尤其重要。所以SPI的核心思想就是解耦。
#### 1.2 SPI 使用场景
1. 数据库驱动加载接口实现类的加载JDBC加载不同类型数据库的驱动
2. 日志门面接口实现类加载SLF4J加载不同提供商的日志实现类
3. Spring中大量使用了SPI,比如：对servlet3.0规范对ServletContainerInitializer的实现、自动类型转换Type Conversion SPI(Converter SPI、Formatter SPI)等
4. Dubbo中也大量使用SPI的方式实现框架的扩展, 不过它对Java提供的原生SPI做了封装，允许用户扩展实现Filter接口
#### 2. Java SPI 使用及源码解析
##### 2.1 示例代码
略... 参见：JavaSpiTest.java类使用，主要分为三步：
- 定义SPI接口
- 基于接口进行实现
- 在项目目录：META-INF.services配置接口全限定名并把接口实现写入到文件中
##### 2.2 源码解析
Java通过ServiceLoader.load()方法进行接口实现的加载，具体代码：
```java
// 1. 定义加载成员变量
public final class ServiceLoader<S>
    implements Iterable<S>
{
    // 默认加载文件路径
    private static final String PREFIX = "META-INF/services/";

    // 当前被加载服务的接口或类
    private final Class<S> service;

    // 类加载器
    private final ClassLoader loader;

    // The access control context taken when the ServiceLoader is created
    private final AccessControlContext acc;

    // 服务提供者列表缓存，排序方式是按照加载先后次序
    private LinkedHashMap<String,S> providers = new LinkedHashMap<>();

    // 懒查找迭代器（获取时才做类加载并实例化）
    private LazyIterator lookupIterator;
    
    // 2. 迭代获取-获取迭代器
    public Iterator<S> iterator() {
        return new Iterator<S>() {

            Iterator<Map.Entry<String,S>> knownProviders
                = providers.entrySet().iterator();

            public boolean hasNext() {
                if (knownProviders.hasNext())
                    return true;
                return lookupIterator.hasNext();
            }

            public S next() {
                if (knownProviders.hasNext())
                    return knownProviders.next().getValue();
                return lookupIterator.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
    // 2. 迭代获取-假如provider不存在则 通过懒查询迭代器查询接口对应路径下类全限定名列表并实例化存入到provider中。
    private boolean hasNextService() {
             if (nextName != null) {
                 return true;
             }
             if (configs == null) {
                 try {
                     String fullName = PREFIX + service.getName();
                     if (loader == null)
                         configs = ClassLoader.getSystemResources(fullName);
                     else
                         configs = loader.getResources(fullName);
                 } catch (IOException x) {
                     fail(service, "Error locating configuration files", x);
                 }
             }
             while ((pending == null) || !pending.hasNext()) {
                 if (!configs.hasMoreElements()) {
                     return false;
                 }
                 pending = parse(service, configs.nextElement());
             }
             nextName = pending.next();
             return true;
         }
    private S nextService() {
        if (!hasNextService())
            throw new NoSuchElementException();
        String cn = nextName;
        nextName = null;
        Class<?> c = null;
        try {
            c = Class.forName(cn, false, loader);
        } catch (ClassNotFoundException x) {
            fail(service,
                 "Provider " + cn + " not found");
        }
        if (!service.isAssignableFrom(c)) {
            fail(service,
                 "Provider " + cn  + " not a subtype");
        }
        try {
            S p = service.cast(c.newInstance());
            providers.put(cn, p);
            return p;
        } catch (Throwable x) {
            fail(service,
                 "Provider " + cn + " could not be instantiated",
                 x);
        }
        throw new Error();          // This cannot happen
    }
    private boolean hasNextService() {
            if (nextName != null) {
                return true;
            }
            if (configs == null) {
                try {
                    String fullName = PREFIX + service.getName();
                    if (loader == null)
                        configs = ClassLoader.getSystemResources(fullName);
                    else
                        configs = loader.getResources(fullName);
                } catch (IOException x) {
                    fail(service, "Error locating configuration files", x);
                }
            }
            while ((pending == null) || !pending.hasNext()) {
                if (!configs.hasMoreElements()) {
                    return false;
                }
                pending = parse(service, configs.nextElement());
            }
            nextName = pending.next();
            return true;
        }
}
```
#### 3. Spring SPI 使用及源码解析
##### 3.1 示例代码
略... 参见：SpringFactoriesLoaderTest.java类使用
##### 3.2 源码解析

```java
// SpringFactoriesLoader 类封装了接口实例化逻辑
public class SpringFactoriesLoader{	
 
    public static <T> List<T> loadFactories(Class<T> factoryType, @Nullable ClassLoader classLoader) {
        Assert.notNull(factoryType, "'factoryType' must not be null");
        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
        }
        // 从spring.facotries加载接口对应的全限定名
        List<String> factoryImplementationNames = loadFactoryNames(factoryType, classLoaderToUse);
        if (logger.isTraceEnabled()) {
            logger.trace("Loaded [" + factoryType.getName() + "] names: " + factoryImplementationNames);
        }
        List<T> result = new ArrayList<>(factoryImplementationNames.size());
        for (String factoryImplementationName : factoryImplementationNames) {
            // 实例化
            result.add(instantiateFactory(factoryImplementationName, factoryType, classLoaderToUse));
        }
        AnnotationAwareOrderComparator.sort(result);
        return result;
    }
}
```

#### 4. Dubbo SPI 使用及源码解析
##### 4.1
略... 参见：DubboSpiTest.java类使用
##### 4.2 
略... 参见：ExtensionLoader.java类注释
##### 4.3 总结
> 1. JDK 标准的 SPI 会一次性实例化扩展点所有实现，如果有扩展实现初始化很耗时，但如果没用上也加载，会很浪费资源。
> 2. 如果扩展点加载失败，连扩展点的名称都拿不到了。比如：JDK 标准的 ScriptEngine，通过 getName() 获取脚本类型的名称，但如果 RubyScriptEngine 因为所依赖的 jruby.jar 不存在，导致 RubyScriptEngine 类加载失败，这个失败原因被吃掉了，和 ruby 对应不起来，当用户执行 ruby 脚本时，会报不支持 ruby，而不是真正失败的原因。
> 3. 增加了对扩展点 IoC[DUbbo实例的依赖注入] 和 AOP[Wrapper]的支持，一个扩展点可以直接 setter 注入其它扩展点。

参考链接：
1. [高级开发必须理解的Java中SPI机制](https://developer.aliyun.com/article/640161)
2. [Java SPI机制详解](https://juejin.im/post/6844903605695152142)
3. [Java SPI 机制及其实现](https://rgb-24bit.github.io/blog/2019/java-spi.html)
4. [代理模式、命令模式、策略模式和门面模式初探](https://www.jianshu.com/p/235e3f514722)
5. [Dubbo SPI](https://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html)
6. [扩展点加载](https://dubbo.apache.org/zh-cn/docs/dev/SPI.html)

