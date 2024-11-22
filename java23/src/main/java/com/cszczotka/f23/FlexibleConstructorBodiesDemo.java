package com.cszczotka.f23;

public class FlexibleConstructorBodiesDemo {

    private int value;

    public FlexibleConstructorBodiesDemo(int value) {
        int preInitialization = value * 2;  // Non-referential statement
        this.value = preInitialization;  // Initialization
    }


    public int getValue() {
        return value;
    }
}
