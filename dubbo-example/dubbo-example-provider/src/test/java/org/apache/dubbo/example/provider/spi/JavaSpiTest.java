package org.apache.dubbo.example.provider.spi;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * <b>Java spi 测试用例</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-28 15:12
 * @since 1.0
 */
public class JavaSpiTest {

    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
