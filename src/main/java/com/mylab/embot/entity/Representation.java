package com.mylab.embot.entity;

public enum Representation {

    IN_PERSON(1),
    PARENTS(2),
    REPRESENTATIVE(4);

    private final int valueIndex;

    Representation(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    public int getValueIndex() {
        return valueIndex;
    }
}
