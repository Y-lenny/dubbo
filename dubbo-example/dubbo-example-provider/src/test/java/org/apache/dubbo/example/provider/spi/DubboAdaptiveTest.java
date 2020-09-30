package org.apache.dubbo.example.provider.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

/**
 * <b>dubbo 自适应扩展机制 测试用例</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-28 17:16
 * @since 1.0
 */
public class DubboAdaptiveTest {

    @Test
    public void testAdaptive() throws Exception {

        ExtensionLoader<CarMaker> extensionLoader =
                ExtensionLoader.getExtensionLoader(CarMaker.class);

        CarMaker carMaker = extensionLoader.getExtension("raceCarMaker");
        Car car = carMaker.makeCar(URL.valueOf("dubbo://192.168.0.101:20880/WheelMaker?wheel.maker=michelinWheelMaker"));
        System.out.println(car.toString());

    }
}
