package com.github.dima.kiev.hyberexcel.refactored.rider;

import com.github.dima.kiev.hyberexcel.domain.RReader;
import com.github.dima.kiev.hyberexcel.domain.Workbook;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RF1030. Simple read actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RF_T1030_ReaderSimpleActionsTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");

    private static RReader<S1SimpleEntity> reader;

    @BeforeAll
    static void setup() throws IOException {
        reader = new RReader<S1SimpleEntity>(new Workbook(INPUT_FILE_PATH)){};
    }

    @Test
    @Order(1)
    @DisplayName("Read all to List<Entity>")
    void readAllLinesToEntityList() {
        List<S1SimpleEntity> records = reader.lineObjects();

        assertEquals(19, records.size(), "Wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Read all to List<Map<String, String>>")
    void readAllLinesToListOfMaps() throws IOException {
        List<Map<String, String>> records = reader.linesAsMap();

        assertEquals(19, records.size(), "Wrong number of lineObjects while reading to List<Map<String, String>>.");
        assertEquals("val_A2", records.get(0).get("columnA"), "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Read labels to List<String>")
    void getFieldLabels() {
        List<String> fieldLabels = reader.labels();

        assertEquals(8, fieldLabels.size(), "Wrong number of field labels.");
        assertEquals("columnA", fieldLabels.get(0), "Labels wrong");
        assertEquals("columnB", fieldLabels.get(1), "Labels wrong");
    }


}