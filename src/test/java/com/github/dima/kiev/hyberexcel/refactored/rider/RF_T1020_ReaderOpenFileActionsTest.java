package com.github.dima.kiev.hyberexcel.refactored.rider;

import com.github.dima.kiev.hyberexcel.domain.RReader;
import com.github.dima.kiev.hyberexcel.domain.Workbook;
import com.github.dima.kiev.hyberexcel.entities.PasswordEntity;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RF1020. File Read open actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RF_T1020_ReaderOpenFileActionsTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");
    private static final String PASSWORD_INPUT_FILE_PATH = Resources.getAbsPathOf("password.xlsx");
    private static final String PASSWORD = "password";

    @Test
    @Order(1)
    @DisplayName("Local file")
    void openLocalFile() throws IOException {
        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(new Workbook(INPUT_FILE_PATH)){};
        List<S1SimpleEntity> records = reader.lineObjects();

        assertEquals(19, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(2)
    @DisplayName("Local file, password")
    void openLocalFilePassword() throws IOException {
        RReader<PasswordEntity> reader = new RReader<PasswordEntity>(new Workbook(PASSWORD_INPUT_FILE_PATH, PASSWORD)){};
        List<PasswordEntity> records = reader.lineObjects();

        assertEquals(1, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(3)
    @DisplayName("Input stream")
    void openInputStream() throws IOException {
        InputStream inputStream = new FileInputStream(new File(INPUT_FILE_PATH));
        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(new Workbook(inputStream)){};
        List<S1SimpleEntity> records = reader.lineObjects();

        assertEquals(19, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(4)
    @DisplayName("Input stream, password")
    void openInputStreamPassword() throws IOException {
        InputStream inputStream = new FileInputStream(new File(PASSWORD_INPUT_FILE_PATH));
        RReader<PasswordEntity> reader = new RReader<PasswordEntity>(new Workbook(inputStream, PASSWORD)){};
        List<PasswordEntity> records = reader.lineObjects();

        assertEquals(1, records.size(), "File Input Stream opening: Wrong number of objects read.");
        assertEquals("val_a", records.get(0).columnA, "File Input Stream opening: The value in 1st cell of 1st line wrong");
    }

    @Test
    @Order(5)
    @DisplayName("Lambda provides file")
    void openLocalFileViaLambda() throws IOException {
        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(() -> {
            try {
                return new Workbook(INPUT_FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }){};
        List<S1SimpleEntity> records = reader.lineObjects();

        assertEquals(19, records.size(), "Local File opening: wrong number of objects read.");
        assertEquals("val_A2", records.get(0).columnA, "Local File opening: The value in 1st cell of 1st line wrong");
    }

}
