package org.apache.dubbo.example.provider.spi.impl;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.example.provider.spi.Wheel;
import org.apache.dubbo.example.provider.spi.WheelMaker;

/**
 * <b>实际轮胎制造商</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 14:13
 * @since 1.0
 */
public class MichelinwheelMaker implements WheelMaker {
    @Override
    public Wheel makeWheel(URL url) {
        return new Wheel(url.getPath());
    }
}
