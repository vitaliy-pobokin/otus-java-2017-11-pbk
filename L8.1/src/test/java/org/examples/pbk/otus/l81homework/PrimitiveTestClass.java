package org.examples.pbk.otus.l81homework;

import java.util.Objects;

class PrimitiveTestClass implements TestClass {
    byte aByte;
    short aShort;
    int anInt;
    long aLong;
    float aFloat;
    double aDouble;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveTestClass that = (PrimitiveTestClass) o;
        return aByte == that.aByte &&
                aShort == that.aShort &&
                anInt == that.anInt &&
                aLong == that.aLong &&
                Float.compare(that.aFloat, aFloat) == 0 &&
                Double.compare(that.aDouble, aDouble) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(aByte, aShort, anInt, aLong, aFloat, aDouble);
    }
}
