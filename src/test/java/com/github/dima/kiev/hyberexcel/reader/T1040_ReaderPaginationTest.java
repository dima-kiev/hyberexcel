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

@DisplayName("1040. Pagination and Predicates")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1040_ReaderPaginationTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");

    private static RecordReader<S1SimpleEntity> reader;

    @BeforeAll
    static void setup() throws InvalidFormatException, IOException, ClassNotFoundException {
        reader = new XlsxReader<S1SimpleEntity>(INPUT_FILE_PATH){};
    }

    @Test
    @Order(1)
    @DisplayName("Read 1st page to List<Entity>")
    void read1stPageOfLinesToEntityList() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        List<S1SimpleEntity> records = reader.lines(0, 10);

        assertEquals(10, records.size(), "Wrong number of objects read while 1st page reading.");
        assertEquals("val_A2", records.get(0).columnA, "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Read 2nd not full page to List<Entity>")
    void read2ndPageOfLinesToEntityList() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        List<S1SimpleEntity> records = reader.lines(11, 10);

        assertEquals(9, records.size(), "Wrong number of objects read while 2nd page reading.");
        assertEquals("val_A12", records.get(0).columnA, "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Read some page to List<Map<String, String>>")
    void readPageOfLinesToListOfMaps() throws IOException {
        List<Map<String, String>> records = reader.records(5, 10);

        assertEquals(10, records.size(), "Wrong number of lineObjects read while 1st page reading to List<Map<String, String>>.");
        assertEquals("val_A6", records.get(0).get("columnA"), "The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Read 1st page of matched results")
    void read1stPageOfMatched() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        reader.addPredicate("columnG", (val) -> !((String)val).contains("_G"));
        List<S1SimpleEntity> records = reader.lines(0, 10);

        assertEquals(2, records.size(), "Wrong number of objects read while 1st page reading by predicate.");
        assertEquals("val_A12", records.get(0).columnA, "The value in 1st cell of 1st line wrong");
        assertEquals("val_A17", records.get(1).columnA, "The value in 1st cell of 1st line wrong");
    }

}