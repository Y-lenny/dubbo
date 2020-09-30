package org.apache.dubbo.example.provider.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * <b>Spi 测试用例</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:32
 * @since 1.0
 */
@SPI("wheelMaker")
public interface WheelMaker {
    Wheel makeWheel(URL url);
}
