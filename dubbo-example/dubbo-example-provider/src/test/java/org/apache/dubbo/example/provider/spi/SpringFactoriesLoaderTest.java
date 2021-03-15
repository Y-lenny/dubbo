package org.apache.dubbo.example.provider.spi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

/**
 * <b>SpringFactoriesLoader spring SPI加载器测试</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-29 15:08
 * @since 1.0
 */
public class SpringFactoriesLoaderTest {

    @Test
    public void testSpringFactoriesLoader() throws IOException {
        Enumeration<URL> urls = SpringFactoriesLoaderTest.class.getClassLoader().getResources("META-INF/spring.factories");
        while (urls.hasMoreElements()) {
            System.out.println(urls.nextElement());
        }

        List<BeanInfoFactory> beanInfoFactoryList = SpringFactoriesLoader.loadFactories(BeanInfoFactory.class, SpringFactoriesLoaderTest.class.getClassLoader());
        beanInfoFactoryList.forEach(beanInfoFactory -> {
            // spring.factories 文件内容：org.springframework.beans.BeanInfoFactory=org.springframework.beans.ExtendedBeanInfoFactory
            // class org.springframework.beans.ExtendedBeanInfoFactory
            System.out.println(beanInfoFactory.getClass());
        });
    }
}
