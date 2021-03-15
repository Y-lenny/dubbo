package org.apache.dubbo.example.provider.spi.impl;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.example.provider.spi.Car;
import org.apache.dubbo.example.provider.spi.CarMaker;
import org.apache.dubbo.example.provider.spi.Wheel;
import org.apache.dubbo.example.provider.spi.WheelMaker;

/**
 * <b>赛车制造商</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:39
 * @since 1.0
 */
public class RaceCarMaker implements CarMaker {

    WheelMaker wheelMaker;

    /**
     * 通过 setter 注入 AdaptiveWheelMaker
     * @param wheelMaker
     */
    public void setWheelMaker(WheelMaker wheelMaker) {
        this.wheelMaker = wheelMaker;
    }

    @Override
    public Car makeCar(URL url) {
        Wheel wheel = wheelMaker.makeWheel(url);
        return new Car(wheel);
    }
}
