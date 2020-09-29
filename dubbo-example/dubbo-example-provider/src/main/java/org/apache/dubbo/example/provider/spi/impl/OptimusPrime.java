package org.apache.dubbo.example.provider.spi.impl;

import org.apache.dubbo.example.provider.spi.Robot;

/**
 * <b>spi 接口实现1</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-28 15:04
 * @since 1.0
 */
public class OptimusPrime implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}
