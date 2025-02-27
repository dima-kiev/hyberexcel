package com.github.dima.kiev.hyberexcel.reader;

import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("1030. Simple read actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1030_ReaderSimpleActionsTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");

    private static RecordReader<S1SimpleEntity> reader;

    @BeforeAll
    static void setup() throws InvalidFormatException, IOException, ClassNotFoundException {
        reader = new XlsxReader<S1SimpleEntity>(INPUT_FILE_PATH){};
    }

    @Test
    @Order(1)
    @DisplayName("Read all to List<Entity>")
    void readAllLinesToEntityList() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        List<S1SimpleEntity> records = reader.lines();

        assertEquals(19, records.size(), "Wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Read all to List<Map<String, String>>")
    void readAllLinesToListOfMaps() throws IOException {
        List<Map<String, String>> records = reader.records();

        assertEquals(19, records.size(), "Wrong number of lineObjects while reading to List<Map<String, String>>.");
        assertEquals("val_A2", records.get(0).get("columnA"), "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Read labels to List<String>")
    void getFieldLabels() {
        List<String> fieldLabels = reader.getFieldLabels();

        assertEquals(8, fieldLabels.size(), "Wrong number of field labels.");
        assertEquals("columnA", fieldLabels.get(0), "Labels wrong");
    }
}