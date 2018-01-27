package org.examples.pbk.otus.l81homework;

import java.util.Objects;

public class PrimitiveWrapperTestClass implements TestClass {
    Byte aByte;
    Short aShort;
    Integer anInt;
    Long aLong;
    Float aFloat;
    Double aDouble;
    Boolean aBoolean;
    Character character;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveWrapperTestClass that = (PrimitiveWrapperTestClass) o;
        return Objects.equals(aByte, that.aByte) &&
                Objects.equals(aShort, that.aShort) &&
                Objects.equals(anInt, that.anInt) &&
                Objects.equals(aLong, that.aLong) &&
                Objects.equals(aFloat, that.aFloat) &&
                Objects.equals(aDouble, that.aDouble) &&
                Objects.equals(aBoolean, that.aBoolean) &&
                Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {

        return Objects.hash(aByte, aShort, anInt, aLong, aFloat, aDouble, aBoolean, character);
    }
}
