package org.examples.pbk.otus.l81homework;

import com.google.gson.Gson;
import org.examples.pbk.otus.l81homework.converter.TypeConverter;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tests {

    private JsonFramework framework;

    @Before
    public void init() {
        framework = new JsonFramework();
    }

    @Test
    public void objectWithoutFieldsShouldReturnCurlyBraces() {
        Object testObject = new Object();
        assertEquals("{}", framework.toJson(testObject));
    }

    @Test
    public void testNullJsonSerialization() {
        assertEquals("null", framework.toJson(null));
    }

    @Test
    public void testPrimitivesShouldBeSerializedAsValues() {
        byte b = 1;
        short s = 2;
        int i = 3;
        long l = 4L;
        float f = 5.0f;
        double d = 6.0;
        boolean bool = false;
        char c = 'a';
        assertEquals("1", framework.toJson(b));
        assertEquals("2", framework.toJson(s));
        assertEquals("3", framework.toJson(i));
        assertEquals("4", framework.toJson(l));
        assertEquals("5.0", framework.toJson(f));
        assertEquals("6.0", framework.toJson(d));
        assertEquals("false", framework.toJson(bool));
        assertEquals("\"a\"", framework.toJson(c));
    }

    @Test
    public void testPrimitivesWrapperShouldBeSerializedAsValues() {
        Byte b = 1;
        Short s = 2;
        Integer i = 3;
        Long l = 4L;
        Float f = 5.0f;
        Double d = 6.0;
        Boolean bool = false;
        Character c = 'a';
        assertEquals("1", framework.toJson(b));
        assertEquals("2", framework.toJson(s));
        assertEquals("3", framework.toJson(i));
        assertEquals("4", framework.toJson(l));
        assertEquals("5.0", framework.toJson(f));
        assertEquals("6.0", framework.toJson(d));
        assertEquals("false", framework.toJson(bool));
        assertEquals("\"a\"", framework.toJson(c));
    }

    @Test
    public void objectWithIntFieldShouldReturnFieldNameWithValue() {
        TestClass testClass = new TestClass() {
            int value = 1;
        };
        assertEquals("{\"value\":1}", framework.toJson(testClass));
    }

    @Test
    public void fieldsWithAnyPrimitiveNumberValueShouldBeStoredAsIs() {
        PrimitiveTestClass testClass = new PrimitiveTestClass();
        testClass.aByte = 0;
        testClass.aShort = 10;
        testClass.anInt = -100;
        testClass.aLong = 1000L;
        testClass.aFloat = 0.001f;
        testClass.aDouble = -0.0002;
        Gson gson = new Gson();
        assertEquals(testClass, gson.fromJson(framework.toJson(testClass), PrimitiveTestClass.class));
    }

    @Test
    public void charFieldValueShouldBeStoredInQuotationMarks() {
        TestClass testClass = new TestClass() {
            char aChar = 'c';
        };
        assertEquals("{\"aChar\":\"c\"}", framework.toJson(testClass));
    }

    @Test
    public void booleanFieldsShouldBeStoredWithoutQuotationMarks() {
        TestClass testClass1 = new TestClass() {
          boolean bool = true;
        };
        assertEquals("{\"bool\":true}", framework.toJson(testClass1));
        TestClass testClass2 = new TestClass() {
            boolean bool = false;
        };
        assertEquals("{\"bool\":false}", framework.toJson(testClass2));
    }

    @Test
    public void stringFieldShouldHaveQuotationMarks() {
        TestClass testClass = new TestClass() {
            String str = "ababab";
        };
        assertEquals("{\"str\":\"ababab\"}", framework.toJson(testClass));
    }

    @Test
    public void nullValuesShouldBeStoredWithoutQuotationMarks() {
        TestClass testClass = new TestClass() {
            Object isNull = null;
            String nullStr = null;
        };
        assertEquals("{\"isNull\":null,\"nullStr\":null}", framework.toJson(testClass));
    }

    @Test
    public void primitiveWrapperObjectsShouldBeStoredSameAsPrimitives() {
        PrimitiveWrapperTestClass testClass = new PrimitiveWrapperTestClass();
        testClass.aByte = 0;
        testClass.aShort = 10;
        testClass.anInt = -100;
        testClass.aLong = 1000L;
        testClass.aFloat = 0.001f;
        testClass.aDouble = -0.0002;
        testClass.aBoolean = true;
        testClass.character = 'c';
        Gson gson = new Gson();
        assertEquals(testClass, gson.fromJson(framework.toJson(testClass), PrimitiveWrapperTestClass.class));
    }

    @Test
    public void innerObjectShouldBeProperlyFormatted() {
        TestClass outer = new TestClass() {
            TestClass inner = new TestClass() {
                int a = 1;
            };
        };
        assertEquals("{\"inner\":{\"a\":1}}", framework.toJson(outer));
    }

    @Test
    public void emptyArrayShouldBeRepresentedAsEmptyBraces() {
        TestClass testClass = new TestClass() {
            int[] ints = new int[0];
        };
        assertEquals("{\"ints\":[]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyArrayOfPrimitives() {
        TestClass testClass = new TestClass() {
            int[] ints = {1, 2, 3};
        };
        assertEquals("{\"ints\":[1,2,3]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyArrayOfPrimitiveWrappers() {
        TestClass testClass = new TestClass() {
            Float[] floats = {1.0f, 2.0f, 3.0f};
        };
        assertEquals("{\"floats\":[1.0,2.0,3.0]}", framework.toJson(testClass));
    }

    @Test
    public void testArrayOfCharsShouldBeStoredAsArrayOfStrings() {
        TestClass testClass = new TestClass() {
            char[] chars = {'a', 'b', 'c'};
        };
        assertEquals("{\"chars\":[\"a\",\"b\",\"c\"]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyArrayOfStrings() {
        TestClass testClass = new TestClass() {
            String[] strings = {"ab", "bc", "ca"};
        };
        assertEquals("{\"strings\":[\"ab\",\"bc\",\"ca\"]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyArrayOfNulls() {
        TestClass testClass = new TestClass() {
            Object[] nulls = {null, null};
        };
        assertEquals("{\"nulls\":[null,null]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyArrayOfObjects() {
        TestClass testClass = new TestClass() {
            TestClass[] testClasses = {
                    new TestClass() {Integer i = 0;},
                    new TestClass() {Double d = 2.0;}
            };
        };
        assertEquals("{\"testClasses\":[{\"i\":0},{\"d\":2.0}]}", framework.toJson(testClass));
    }

    @Test
    public void testEmptyListShouldBeRepresentedAsEmptyArray() {
        TestClass testClass = new TestClass() {
            List<Integer> list = Collections.emptyList();
        };
        assertEquals("{\"list\":[]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyListShouldBeRepresentedAsArray() {
        TestClass testClass = new TestClass() {
            List<Integer> list = Arrays.asList(1, 2, 3);
        };
        assertEquals("{\"list\":[1,2,3]}", framework.toJson(testClass));
    }

    @Test
    public void testTwoDimensionalArraySerialization() {
        TestClass testClass = new TestClass() {
            int[][] ints = {{0, 1}, {1, 2}, {2, 3}};
        };
        assertEquals("{\"ints\":[[0,1],[1,2],[2,3]]}", framework.toJson(testClass));
    }

    @Test
    public void testArrayOfListsSerialization() {
        TestClass testClass = new TestClass() {
            List[] lists = {Arrays.asList(0,1), Arrays.asList(1,2), Arrays.asList(2,3)};
        };
        assertEquals("{\"lists\":[[0,1],[1,2],[2,3]]}", framework.toJson(testClass));
    }

    @Test
    public void testNonEmptyListOfObjectsJsonSerialization() {
        TestClass testClass = new TestClass() {
            List<TestClass> list = Arrays.asList(
                    new TestClass() {Integer i = 0;},
                    new TestClass() {Double d = 2.0;});
        };
        assertEquals("{\"list\":[{\"i\":0},{\"d\":2.0}]}", framework.toJson(testClass));
    }

    @Test
    public void testAddCustomConverterToJsonValue() {
        PrimitiveTestClass testClass = new PrimitiveTestClass();
        testClass.aByte = 1;
        testClass.anInt = 2;
        TypeConverter<JsonValue> testTypeConverter = new TypeConverter() {
            @Override
            public JsonValue convert(Object o) {
                PrimitiveTestClass testClass = (PrimitiveTestClass) o;
                int sum = testClass.aByte + testClass.anInt;
                return Json.createValue(sum);
            }
        };
        framework.addCustomConverter(PrimitiveTestClass.class, testTypeConverter);
        assertEquals("3", framework.toJson(testClass));
    }

    @Test
    public void testAddCustomConverterToJsonObject() {
        PrimitiveTestClass testClass = new PrimitiveTestClass();
        testClass.aByte = 1;
        testClass.anInt = 2;
        TypeConverter<JsonObject> testTypeConverter = new TypeConverter() {
            @Override
            public JsonObject convert(Object o) {
                PrimitiveTestClass testClass = (PrimitiveTestClass) o;
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("byte", testClass.aByte);
                objectBuilder.add("int", testClass.anInt);
                return objectBuilder.build();
            }
        };
        framework.addCustomConverter(PrimitiveTestClass.class, testTypeConverter);
        assertEquals("{\"byte\":1,\"int\":2}", framework.toJson(testClass));
    }
}
