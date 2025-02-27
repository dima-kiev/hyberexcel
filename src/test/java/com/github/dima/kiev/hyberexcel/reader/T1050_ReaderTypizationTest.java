package com.github.dima.kiev.hyberexcel.reader;

import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.entities.S2TypesEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.util.Resources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("1050. Read Field type actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1050_ReaderTypizationTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");

    private static RecordReader<S2TypesEntity> reader;
    private static List<S2TypesEntity> records;

    private static Gson gson = new Gson();

    @BeforeAll
    static void setup() throws InvalidFormatException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        reader = new XlsxReader<S2TypesEntity>(INPUT_FILE_PATH){};
        records = reader.lines();
    }

    @Test
    @Order(1)
    @DisplayName("String type")
    void readStringType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(String.class, records.get(0).stringType.getClass(), "String type read wrong");
        assertEquals("String_1", records.get(0).stringType, "String value wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Character type")
    void readCharacterType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Character.class, records.get(0).characterType.getClass(), "Character type read wrong");
        assertEquals('a', records.get(0).characterType, "Character value wrong");
        assertEquals('b', records.get(1).characterType, "Character value wrong");
        assertEquals('ы', records.get(2).characterType, "Character value wrong");
        assertEquals('ъ', records.get(3).characterType, "Character value wrong");
        assertEquals('*', records.get(4).characterType, "Character value wrong");
        assertEquals('3', records.get(5).characterType, "Character value wrong");
        assertEquals('-', records.get(6).characterType, "Character value wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Byte type")
    void readByteType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Byte.class, records.get(0).byteType.getClass(), "Byte type read wrong");
        assertEquals(Byte.valueOf("0"), records.get(0).byteType, "Byte value wrong");
        assertEquals(Byte.valueOf("1"), records.get(1).byteType, "Byte value wrong");
        assertEquals(Byte.valueOf("-1"), records.get(2).byteType, "Byte value wrong");
        assertEquals(Byte.valueOf("-128"), records.get(3).byteType, "Byte value wrong");
        assertEquals(Byte.valueOf("127"), records.get(4).byteType, "Byte value wrong");
        //assertEquals(Byte.valueOf("255"), records.get(5).byteType, "Byte value wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Short type")
    void readShortType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Short.class, records.get(0).shortType.getClass(), "Short type read wrong");
        assertEquals((short) 0, records.get(0).shortType, "Short value wrong");
        assertEquals((short) 1, records.get(1).shortType, "Short value wrong");
        assertEquals((short) -1, records.get(2).shortType, "Short value wrong");
        assertEquals((short) -32768, records.get(3).shortType, "Short value wrong");
        assertEquals((short) 32767, records.get(4).shortType, "Short value wrong");
        //assertEquals((short) 65000, records.get(5).shortType, "Short value wrong");
        // todo assertThrows for wrong values
    }

    @Test
    @Order(5)
    @DisplayName("Integer type")
    void readIntegerType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Integer.class, records.get(0).integerType.getClass(), "Integer type read wrong");
        assertEquals(0, records.get(0).integerType, "Integer value wrong");
        assertEquals(1, records.get(1).integerType, "Integer value wrong");
        assertEquals(-1, records.get(2).integerType, "Integer value wrong");
        assertEquals(-2147483648, records.get(3).integerType, "Integer value wrong");
        assertEquals(2147483647, records.get(4).integerType, "Integer value wrong");
        //assertEquals(10000000000, records.get(5).integerType, "Integer value wrong");
    }

    @Test
    @Order(6)
    @DisplayName("Long type")
    void readLongType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Long.class, records.get(0).longType.getClass(), "Long type read wrong");
        assertEquals(0L, records.get(0).longType, "Long value wrong");
        assertEquals(1L, records.get(1).longType, "Long value wrong");
        assertEquals(-1L, records.get(2).longType, "Long value wrong");
        assertEquals(-9223372036854769664L, records.get(3).longType, "Long value wrong");
        assertEquals(9223372036854775807L, records.get(4).longType, "Long value wrong");
    }

    @Test
    @Order(7)
    @DisplayName("Float type")
    void readFloatType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Float.class, records.get(0).floatType.getClass(), "Float type read wrong");
        assertEquals(0.0f, records.get(0).floatType, "Float value wrong");
        assertEquals(1.0f, records.get(1).floatType, "Float value wrong");
        assertEquals(-1.0f, records.get(2).floatType, "Float value wrong");
        assertEquals(1.1f, records.get(3).floatType, "Float value wrong");
        assertEquals(-1.1f, records.get(4).floatType, "Float value wrong");
    }

    @Test
    @Order(8)
    @DisplayName("Double type")
    void readDoubleType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Double.class, records.get(0).doubleType.getClass(), "Double type read wrong");
        assertEquals(0.0, records.get(0).doubleType, "Double value wrong");
        assertEquals(1.0, records.get(1).doubleType, "Double value wrong");
        assertEquals(-1.0, records.get(2).doubleType, "Double value wrong");
        assertEquals(1.1, records.get(3).doubleType, "Double value wrong");
        assertEquals(-1.1, records.get(4).doubleType, "Double value wrong");
    }

    @Test
    @Order(9)
    @DisplayName("BigInteger type")
    void readBigIntegerType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(BigInteger.class, records.get(0).bigIntegerType.getClass(), "BigInteger type read wrong");
        assertEquals(new BigInteger("0"), records.get(0).bigIntegerType, "BigInteger value wrong");
        assertEquals(new BigInteger("1"), records.get(1).bigIntegerType, "BigInteger value wrong");
        assertEquals(new BigInteger("-1"), records.get(2).bigIntegerType, "BigInteger value wrong");
        //assertEquals(new BigInteger("-9223372036854770000"), records.get(3).bigIntegerType, "BigInteger value wrong");
        assertEquals(new BigInteger("9223372036854775807"), records.get(4).bigIntegerType, "BigInteger value wrong");
        //assertEquals(new BigInteger("3339223372036854775808"), records.get(5).bigIntegerType, "BigInteger value wrong");
        //assertEquals(new BigInteger(""), records.get(6).bigIntegerType, "BigInteger value wrong");
        //assertEquals(new BigInteger(""), records.get(7).bigIntegerType, "BigInteger value wrong");
        //assertEquals(new BigInteger(""), records.get(8).bigIntegerType, "BigInteger value wrong");
    }

    @Test
    @Order(10)
    @DisplayName("LocalDate type")
    void readLocalDateType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(LocalDate.class, records.get(0).localDateType.getClass(), "BigInteger type read wrong");
        assertEquals(LocalDate.of(1976, 7, 11), records.get(0).localDateType, "LocalDate value wrong");
        assertEquals(LocalDate.of(2002, 5, 1), records.get(1).localDateType, "LocalDate value wrong");
        assertEquals(LocalDate.of(2007, 8, 6), records.get(2).localDateType, "LocalDate value wrong");
        assertEquals(LocalDate.of(2007, 8, 6), records.get(3).localDateType, "LocalDate value wrong");
        assertNull(records.get(4).localDateType, "LocalDate value wrong");
    }

    @Test
    @Order(11)
    @DisplayName("Date type")
    void readDateType() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Date.class, records.get(0).dateType.getClass(), "Date type read wrong");
        assertEquals(formatter.parse("11/07/1976"), records.get(0).dateType, "Date value wrong");
        assertEquals(formatter.parse("1/05/2002"), records.get(1).dateType, "Date value wrong");
        assertEquals(formatter.parse("6/08/2007"), records.get(2).dateType, "Date value wrong");
        assertEquals(formatter.parse("6/08/2007"), records.get(3).dateType, "Date value wrong");
        assertNull(records.get(4).dateType, "Date value wrong");
    }

    @Test
    @Order(12)
    @DisplayName("Boolean type")
    void readBooleanType(){
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertEquals(Boolean.class, records.get(0).booleanType.getClass(), "Boolean type read wrong");
        assertEquals(true, records.get(0).booleanType, "Boolean value wrong");
        assertEquals(false, records.get(1).booleanType, "Boolean value wrong");
        assertEquals(true, records.get(2).booleanType, "Boolean value wrong");
        assertEquals(false, records.get(3).booleanType, "Boolean value wrong");
    }

    @Test
    @Order(13)
    @DisplayName("Json Object type")
    void readJsonObjectType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(Map.class.isInstance(records.get(0).jsonObjectType), "wrong type instantiated");
        Map<String, Object> expected = gson.fromJson("{\"f_1\":\"v_1\", \"f_2\":2}", new TypeToken<Map<String, Object>>(){}.getType());
        assertEquals(expected, records.get(0).jsonObjectType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonObjectType.get("f_2").getClass(), "Double type json field read wrong");
        assertTrue(Map.class.isInstance(records.get(1).jsonObjectType), "wrong type instantiated");
        expected = gson.fromJson("{}", new TypeToken<Map<String, Object>>(){}.getType());
        assertEquals(expected, records.get(1).jsonObjectType, "parsed objects are not the same");
    }

    @Test
    @Order(14)
    @DisplayName("Json Array type")
    void readJsonArrayType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(List.class.isInstance(records.get(0).jsonArrayType), "wrong type instantiated");
        List<Map<String, Object>> expected = gson.fromJson("[{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]", new TypeToken<List<Map<String, Object>>>(){}.getType());
        assertEquals(expected, records.get(0).jsonArrayType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonArrayType.get(0).get("f_2").getClass(), "Double type json field read wrong");
        assertTrue(List.class.isInstance(records.get(1).jsonArrayType), "wrong type instantiated");
        expected = gson.fromJson("[]", new TypeToken<List<Map<String, Object>>>(){}.getType());
        assertEquals(expected, records.get(1).jsonArrayType, "parsed objects are not the same");
    }

    @Test
    @Order(15)
    @DisplayName("Custom Json type")
    void readJsonCustomType() {
        String expJson = "{\"fieldOne\":\"value_one\", \"fieldTwo\":\"value_two\", \"someValue\": 55.55, \"jsonObject\": {\"f_1\":\"v_1\", \"f_2\":2}, \"jsonArray\": [{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]}";
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(S2TypesEntity.CustomJson.class.isInstance(records.get(0).jsonCustomType), "wrong type instantiated");
        S2TypesEntity.CustomJson expected = gson.fromJson(expJson, S2TypesEntity.CustomJson.class);
        assertEquals(expected, records.get(0).jsonCustomType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonCustomType.jsonObject.get("f_2").getClass(), "Double type json field read wrong");
    }

    @Disabled
    @Test
    @Order(16)
    @DisplayName("List of Custom Json type")
    void readJsonListOfCustomType() {
        String expJson = "[{\"fieldOne\":\"value_one\", \"fieldTwo\":\"value_two\", \"someValue\": 55.55, \"jsonObject\": {\"f_1\":\"v_1\", \"f_2\":2}, \"jsonArray\": [{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]}, \n" +
                         "{\"fieldOne\":\"value_one-2\", \"fieldTwo\":\"value_two-2\", \"someValue\": 55.55, \"jsonObject\": {\"f_1\":\"v_1\", \"f_2\":2}, \"jsonArray\": [{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]}]";
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(List.class.isInstance(records.get(0).jsonListOfCustomType), "wrong type instantiated");
        List<S2TypesEntity.CustomJson> expected = gson.fromJson(expJson, new TypeToken<List<S2TypesEntity.CustomJson>>(){}.getType());
        assertEquals(expected, records.get(0).jsonListOfCustomType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonListOfCustomType.get(0).jsonObject.get("f_2").getClass(), "Double type json field read wrong");
    }

}