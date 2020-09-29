package org.apache.dubbo.example.provider.spi;

import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.common.extension.Wrapper;

/**
 * <b>spi 测试接口</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-28 15:03
 * @since 1.0
 */
@SPI("robot")
@Wrapper
public interface Robot {
    void sayHello();
}
