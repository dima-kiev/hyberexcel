package com.github.dima.kiev.hyberexcel.writer;

import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.RecordWriter;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.impl.XlsxWriter;
import com.github.dima.kiev.hyberexcel.util.TestUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2030. Writer simple actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2030_WriterSimpleActionsTest {

    private static final String TEMPLATE_FILE_NAME = "empty.xlsx";

    private List<S1SimpleEntity> createDataSample() {

        S1SimpleEntity se = new S1SimpleEntity();
        se.columnA = "test data";
        se.columnB = "";
        se.columnC = "";
        se.columnD = "";
        se.columnE = "";
        se.columnF = "";
        se.columnG = "";
        se.columnH = "";

        List<S1SimpleEntity> data = new ArrayList<>();
        data.add(se);

        return data;
    }

    @Test
    @Order(1)
    @DisplayName("Append. List<Entity>")
    void appendListEntities() throws InvalidFormatException, IOException, ClassNotFoundException, GeneralSecurityException, InstantiationException, IllegalAccessException {

        String tmpFilePath = TestUtils.createTempFileWithTemplate(TEMPLATE_FILE_NAME);

        List<S1SimpleEntity> data = createDataSample();

        RecordWriter<S1SimpleEntity> writer = new XlsxWriter<S1SimpleEntity>(tmpFilePath){};
        writer.appendRecords(data);

        RecordReader<S1SimpleEntity> reader = new XlsxReader<S1SimpleEntity>(tmpFilePath){};
        List<S1SimpleEntity> readRecords = reader.lines();

        //note: rowNumber field of Entity class are not considered in equals check
        assertEquals(data, readRecords, "wrote and read data are different");

    }

/*
    @Test
    @Order(2)
    @DisplayName("Input stream")
    void openInputStream() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        InputStream inputStream = new FileInputStream(new File(INPUT_FILE_PATH));
        RecordReader<S1SimpleEntity> reader = new XlsxReader<S1SimpleEntity>(inputStream){};
        List<S1SimpleEntity> records = reader.lineObjects();

        assertEquals(19, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Local file, password")
    void openLocalFilePassword() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException, GeneralSecurityException {
        RecordReader<PasswordEntity> reader = new XlsxReader<PasswordEntity>(PASSWORD_INPUT_FILE_PATH, PASSWORD){};
        List<PasswordEntity> records = reader.lineObjects();

        assertEquals(1, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Input stream, password")
    void openInputStreamPassword() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException, GeneralSecurityException {
        InputStream inputStream = new FileInputStream(new File(PASSWORD_INPUT_FILE_PATH));
        RecordReader<PasswordEntity> reader = new XlsxReader<PasswordEntity>(inputStream, PASSWORD){};
        List<PasswordEntity> records = reader.lineObjects();

        assertEquals(1, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }
    */

}