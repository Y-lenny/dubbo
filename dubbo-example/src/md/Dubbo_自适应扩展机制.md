### dubbo_自适应扩展机制

--- 
### 1. 概述
Dubbo 项目地址：[https://github.com/Y-lenny/dubbo.git](https://github.com/Y-lenny/dubbo.git)
>以上地址是以源地址fork出来，后续源码解析、注释将会基于此进行编辑
#### 1.1 简单介绍
在 Dubbo 中，很多拓展都是通过 SPI 机制进行加载的，比如 Protocol、Cluster、LoadBalance 等。
有时，有些拓展并不想在框架启动阶段被加载，而是希望在拓展方法被调用时，根据运行时参数进行加载。
这听起来有些矛盾。拓展未被加载，那么拓展方法就无法被调用（静态方法除外）。拓展方法未被调用，拓展就无法被加载。
对于这个矛盾的问题，Dubbo 通过自适应拓展机制很好的解决了。自适应拓展机制的实现逻辑比较复杂，
首先 Dubbo 会为拓展接口生成具有代理功能的代码。然后通过 javassist 或 jdk 编译这段代码，得到 Class 类。
最后再通过反射创建代理类，整个过程比较复杂。

#### 2. 自适应拓展源码解析
##### 1.1 伪代码
略... 参见：AdaptiveWheelMaker.java类
##### 1.2 源码解析
略... 参见：ExtensionLoader.java类中getAdaptiveExtension()方法入口
参考链接：
1. [自适应拓展机制](https://dubbo.apache.org/zh-cn/docs/source_code_guide/adaptive-extension.html)
