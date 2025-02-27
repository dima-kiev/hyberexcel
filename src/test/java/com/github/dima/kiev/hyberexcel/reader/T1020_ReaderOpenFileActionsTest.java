package com.github.dima.kiev.hyberexcel.reader;

import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.entities.PasswordEntity;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("1020. File Read open actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1020_ReaderOpenFileActionsTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");
    private static final String PASSWORD_INPUT_FILE_PATH = Resources.getAbsPathOf("password.xlsx");
    private static final String PASSWORD = "password";

    @Test
    @Order(1)
    @DisplayName("Local file")
    void openLocalFile() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        RecordReader<S1SimpleEntity> reader = new XlsxReader<S1SimpleEntity>(INPUT_FILE_PATH){};
        List<S1SimpleEntity> records = reader.lines();

        assertEquals(19, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Input stream")
    void openInputStream() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        InputStream inputStream = new FileInputStream(new File(INPUT_FILE_PATH));
        RecordReader<S1SimpleEntity> reader = new XlsxReader<S1SimpleEntity>(inputStream){};
        List<S1SimpleEntity> records = reader.lines();

        assertEquals(19, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Local file, password")
    void openLocalFilePassword() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException, GeneralSecurityException {
        RecordReader<PasswordEntity> reader = new XlsxReader<PasswordEntity>(PASSWORD_INPUT_FILE_PATH, PASSWORD){};
        List<PasswordEntity> records = reader.lines();

        assertEquals(1, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Input stream, password")
    void openInputStreamPassword() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException, GeneralSecurityException {
        InputStream inputStream = new FileInputStream(new File(PASSWORD_INPUT_FILE_PATH));
        RecordReader<PasswordEntity> reader = new XlsxReader<PasswordEntity>(inputStream, PASSWORD){};
        List<PasswordEntity> records = reader.lines();

        assertEquals(1, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }

}