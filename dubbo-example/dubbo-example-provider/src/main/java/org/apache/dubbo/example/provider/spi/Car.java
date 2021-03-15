package org.apache.dubbo.example.provider.spi;

/**
 * <b>车实体</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-09-30 11:38
 * @since 1.0
 */
public class Car {

    private Wheel wheel;

    public Car(Wheel wheel) {
        this.wheel = wheel;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheel=" + wheel +
                '}';
    }
}
