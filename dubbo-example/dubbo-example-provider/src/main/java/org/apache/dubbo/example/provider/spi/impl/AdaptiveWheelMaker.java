package org.apache.dubbo.example.provider.spi.impl;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.example.provider.spi.Wheel;
import org.apache.dubbo.example.provider.spi.WheelMaker;

/**
 * <b>自适应轮胎制造商</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:36
 * @since 1.0
 */
// 表示拓展的加载逻辑由人工编码完成，假如Adaptive 是注解在接口方法上的，表示拓展的加载逻辑需由框架自动生成
@Adaptive
public class AdaptiveWheelMaker implements WheelMaker {

    @Override
    public Wheel makeWheel(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }

        // 1.从 URL 中获取 WheelMaker 名称
        String wheelMakerName = url.getParameter("wheel.maker");
        if (wheelMakerName == null) {
            throw new IllegalArgumentException("wheelMakerName == null");
        }

        // 2.通过 SPI 加载具体的 WheelMaker
        WheelMaker wheelMaker = ExtensionLoader
                .getExtensionLoader(WheelMaker.class).getExtension(wheelMakerName);

        // 3.调用目标方法
        return wheelMaker.makeWheel(url);
    }
}
