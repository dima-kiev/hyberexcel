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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2020. File Write open actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2020_WriterOpenFileActionsTest {

    private static final String TEMPLATE_FILE_NAME = "empty.xlsx";
    //private static final String PASSWORD_TEMPLATE_FILE_PATH = Resources.getAbsPathOf("EmptyPassword.xlsx");
    //private static final String PASSWORD = "password";

    @Test
    @Order(1)
    @DisplayName("Local file write")
    void openLocalFileToWrite() throws IOException, InvalidFormatException, ClassNotFoundException, GeneralSecurityException, InstantiationException, IllegalAccessException {

        String tmpFilePath = TestUtils.createTempFileWithTemplate(TEMPLATE_FILE_NAME);

        RecordWriter<S1SimpleEntity> writer = new XlsxWriter<S1SimpleEntity>(tmpFilePath){};
        writer.appendRecords(Collections.emptyList());

        RecordReader<S1SimpleEntity> reader = new XlsxReader<S1SimpleEntity>(tmpFilePath){};
        List<S1SimpleEntity> readRecords = reader.lines();

        assertEquals(Collections.emptyList(), readRecords, "something read from file expected as empty");

    }


}