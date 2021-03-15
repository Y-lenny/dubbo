package org.apache.dubbo.example.provider.spi;

/**
 * <b>轮胎实体</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:34
 * @since 1.0
 */
public class Wheel {

    public Wheel() {
    }

    public Wheel(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "name='" + name + '\'' +
                '}';
    }
}
