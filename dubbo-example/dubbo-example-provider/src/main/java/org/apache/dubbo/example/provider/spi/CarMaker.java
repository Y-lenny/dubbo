package org.apache.dubbo.example.provider.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

/**
 * <b>description</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:38
 * @since 1.0
 */
@SPI("carMaker")
public interface CarMaker {
    Car makeCar(URL url);
}
