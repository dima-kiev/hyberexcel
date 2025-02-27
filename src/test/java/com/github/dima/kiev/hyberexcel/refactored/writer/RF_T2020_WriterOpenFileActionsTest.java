package com.github.dima.kiev.hyberexcel.refactored.writer;

import com.github.dima.kiev.hyberexcel.domain.RReader;
import com.github.dima.kiev.hyberexcel.domain.RWriter;
import com.github.dima.kiev.hyberexcel.domain.Workbook;
import com.github.dima.kiev.hyberexcel.domain.WriteAbleWorkbook;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.util.Resources;
import com.github.dima.kiev.hyberexcel.util.TestUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RF2020. File Write open actions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RF_T2020_WriterOpenFileActionsTest {

    private static final String TEMPLATE_FILE_NAME = "empty.xlsx";
    private static final String DATA_FILE_NAME = Resources.getAbsPathOf("HyberExcel.xlsx");
    //private static final String PASSWORD_TEMPLATE_FILE_PATH = Resources.getAbsPathOf("EmptyPassword.xlsx");
    //private static final String PASSWORD = "password";

    @Test
    @Order(1)
    @DisplayName("Local file write")
    void openLocalFileToWrite() throws IOException {

        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(new Workbook(DATA_FILE_NAME)){};
        List<S1SimpleEntity> readRecords = reader.lineObjects();

        String tmpFilePath = TestUtils.createTempFileWithTemplate(TEMPLATE_FILE_NAME);

        RWriter<S1SimpleEntity> writer = new RWriter<S1SimpleEntity>(new WriteAbleWorkbook(tmpFilePath)){};
        Path savedFilePath = writer.appendRecords(readRecords).saveToFile();

        RReader<S1SimpleEntity> reReader = new RReader<S1SimpleEntity>(new Workbook(savedFilePath.toString())){};
        List<S1SimpleEntity> reReadRecords = reReader.lineObjects();

        assertEquals(readRecords.size(), reReadRecords.size(), "file written wrong: the number of records not equals");

       // assertEquals(Collections.emptyList(), readRecords, "something read from file expected as empty");

    }

}
