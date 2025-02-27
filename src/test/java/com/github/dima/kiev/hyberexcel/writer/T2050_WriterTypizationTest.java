package com.github.dima.kiev.hyberexcel.writer;

import com.github.dima.kiev.hyberexcel.entities.DefaultTypeValuesEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.impl.XlsxWriter;
import com.github.dima.kiev.hyberexcel.util.TestUtils;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("2050. Write Field type actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2050_WriterTypizationTest {

    private static final String TEMPLATE_FILE_NAME = "empty.xlsx";

    private List<DefaultTypeValuesEntity> writeToTempFileAndReadBack(List<DefaultTypeValuesEntity> dataToWrite) {
        List<DefaultTypeValuesEntity> readBackData;

        String tmpFilePath = TestUtils.createTempFileWithTemplate(TEMPLATE_FILE_NAME);
        try {
            new XlsxWriter<DefaultTypeValuesEntity>(tmpFilePath){}.appendRecords(dataToWrite);
        } catch (Exception e) {
            throw new RuntimeException("Error on write operation", e);
        }
        try {
            readBackData = new XlsxReader<DefaultTypeValuesEntity>(tmpFilePath){}.lines();
        } catch (Exception e) {
            throw new RuntimeException("Error on read operation", e);
        }

        return readBackData;
    }

    @Test
    @Order(1)
    @DisplayName("String type")
    void writeStringType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(String.class, readRecords.get(0).stringType.getClass(), "String type read wrong");
        assertEquals(entity.stringType, readRecords.get(0).stringType, "String value wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Character type")
    void writeCharacterType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Character.class, readRecords.get(0).characterType.getClass(), "Character type read wrong");
        assertEquals(entity.characterType, readRecords.get(0).characterType, "Character value wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Byte type")
    void writeByteType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Byte.class, readRecords.get(0).byteType.getClass(), "Byte type read wrong");
        assertEquals(entity.byteType, readRecords.get(0).byteType, "Byte value wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Short type")
    void writeShortType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Short.class, readRecords.get(0).shortType.getClass(), "Short type read wrong");
        assertEquals(entity.shortType, readRecords.get(0).shortType, "Short Short wrong");
   }

    @Test
    @Order(5)
    @DisplayName("Integer type")
    void writeIntegerType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Integer.class, readRecords.get(0).integerType.getClass(), "Integer type read wrong");
        assertEquals(entity.integerType, readRecords.get(0).integerType, "Integer value wrong");
    }

    @Test
    @Order(6)
    @DisplayName("Long type")
    void writeLongType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Long.class, readRecords.get(0).longType.getClass(), "Long type read wrong");
        assertEquals(entity.longType, readRecords.get(0).longType, "Long value wrong");
    }

    @Test
    @Order(7)
    @DisplayName("Float type")
    void writeFloatType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Float.class, readRecords.get(0).floatType.getClass(), "Float type read wrong");
        assertEquals(entity.floatType, readRecords.get(0).floatType, "Float value wrong");
    }

    @Test
    @Order(8)
    @DisplayName("Double type")
    void writeDoubleType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Double.class, readRecords.get(0).doubleType.getClass(), "Double type read wrong");
        assertEquals(entity.doubleType, readRecords.get(0).doubleType, "Double value wrong");
    }

    @Test
    @Order(9)
    @DisplayName("BigInteger type")
    void writeBigIntegerType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(BigInteger.class, readRecords.get(0).bigIntegerType.getClass(), "BigInteger type read wrong");
        assertEquals(entity.bigIntegerType, readRecords.get(0).bigIntegerType, "BigInteger value wrong");
    }

    @Test
    @Order(10)
    @DisplayName("LocalDate type")
    void writeLocalDateType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(LocalDate.class, readRecords.get(0).localDateType.getClass(), "LocalDate type read wrong");
        assertEquals(entity.localDateType, readRecords.get(0).localDateType, "LocalDate value wrong");
    }

    @Test
    @Order(11)
    @DisplayName("Date type")
    void writeDateType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Date.class, readRecords.get(0).dateType.getClass(), "Date type read wrong");
        assertEquals(entity.dateType, readRecords.get(0).dateType, "Date value wrong");
    }

    @Test
    @Order(12)
    @DisplayName("Boolean type")
    void writeBooleanType(){
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(Boolean.class, readRecords.get(0).booleanType.getClass(), "Boolean type read wrong");
        assertEquals(entity.booleanType, readRecords.get(0).booleanType, "Boolean value wrong");
    }

    @Test
    @Order(13)
    @DisplayName("Json Object type")
    void writeJsonObjectType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertNotNull(readRecords.get(0).jsonObjectType, "Json Object type expected not null");
        assertEquals(LinkedTreeMap.class, readRecords.get(0).jsonObjectType.getClass(), "Json Object type read wrong");
        assertEquals(entity.jsonObjectType, readRecords.get(0).jsonObjectType, "Json Object value wrong");
    }
/*
    @Test
    @Order(14)
    @DisplayName("Json Array type")
    void writeJsonArrayType() {
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(List.class.isInstance(records.get(0).jsonArrayType), "wrong type instantiated");
        List<Map<String, Object>> expected = gson.fromJson("[{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]", new TypeToken<List<Map<String, Object>>>(){}.getType());
        assertEquals(expected, records.get(0).jsonArrayType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonArrayType.get(0).get("f_2").getClass(), "Double type json field read wrong");
        assertTrue(List.class.isInstance(records.get(1).jsonArrayType), "wrong type instantiated");
        expected = gson.fromJson("[]", new TypeToken<List<Map<String, Object>>>(){}.getType());
        assertEquals(expected, records.get(1).jsonArrayType, "parsed objects are not the same");
    }
*/
    @Test
    @Order(15)
    @DisplayName("Custom Json type")
    void writeJsonCustomType() {
        List<DefaultTypeValuesEntity> dataToWrite = new ArrayList<>();
        DefaultTypeValuesEntity entity = new DefaultTypeValuesEntity();
        dataToWrite.add(entity);

        List<DefaultTypeValuesEntity> readRecords = writeToTempFileAndReadBack(dataToWrite);

        assertEquals(1, readRecords.size(), "Wrong number of objects read.");
        assertEquals(entity.jsonCustomType.getClass(), readRecords.get(0).jsonCustomType.getClass(), "Json Custom Object type read wrong");
        assertEquals(entity.jsonCustomType, readRecords.get(0).jsonCustomType, "Json Custom Object value wrong");
    }
/*
    @Disabled
    @Test
    @Order(16)
    @DisplayName("List of Custom Json type")
    void writeJsonListOfCustomType() {
        String expJson = "[{\"fieldOne\":\"value_one\", \"fieldTwo\":\"value_two\", \"someValue\": 55.55, \"jsonObject\": {\"f_1\":\"v_1\", \"f_2\":2}, \"jsonArray\": [{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]}, \n" +
                         "{\"fieldOne\":\"value_one-2\", \"fieldTwo\":\"value_two-2\", \"someValue\": 55.55, \"jsonObject\": {\"f_1\":\"v_1\", \"f_2\":2}, \"jsonArray\": [{\"f_1\": \"v_1\", \"f_2\":2}, {\"f_1\": \"v_2\", \"f_2\":2.2}]}]";
        assertEquals(9, records.size(), "Wrong number of objects read.");
        assertTrue(List.class.isInstance(records.get(0).jsonListOfCustomType), "wrong type instantiated");
        List<S2TypesEntity.CustomJson> expected = gson.fromJson(expJson, new TypeToken<List<S2TypesEntity.CustomJson>>(){}.getType());
        assertEquals(expected, records.get(0).jsonListOfCustomType, "parsed objects are not the same");
        assertEquals(Double.class, records.get(0).jsonListOfCustomType.get(0).jsonObject.get("f_2").getClass(), "Double type json field read wrong");
    }
*/
}