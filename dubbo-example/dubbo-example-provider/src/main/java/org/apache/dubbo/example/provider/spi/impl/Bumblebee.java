package org.apache.dubbo.example.provider.spi.impl;

import org.apache.dubbo.example.provider.spi.Robot;

/**
 * <b>spi 接口实现2</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-28 15:06
 * @since 1.0
 */
public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
