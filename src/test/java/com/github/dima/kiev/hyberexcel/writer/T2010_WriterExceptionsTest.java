package com.github.dima.kiev.hyberexcel.writer;

import com.github.dima.kiev.hyberexcel.entities.PasswordEntity;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.exceptions.FileNotFoundExceptionUnchecked;
import com.github.dima.kiev.hyberexcel.exceptions.WrongPasswordException;
import com.github.dima.kiev.hyberexcel.impl.XlsxWriter;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("2010. Writer Exceptions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T2010_WriterExceptionsTest {

    private static final String PASSWORD_INPUT_FILE_PATH = Resources.getAbsPathOf("password.xlsx");
    private static final String WRONG_PASSWORD = "wrong password";
    private static final String WRONG_FORMAT_FILE_PATH = Resources.getAbsPathOf("WrongFormat.xlsx");
    private static final String UNEXISTING_FILE_PATH = "unExisting.xlsx";

    @Test
    @Order(1)
    @DisplayName("Wrong file path")
    void wrongFilePath() {
        assertThrows(FileNotFoundExceptionUnchecked.class,
                     () -> new XlsxWriter<S1SimpleEntity>(UNEXISTING_FILE_PATH){},
                    "No Exception when wrong file path given, or wrong exception");
    }

    @Test
    @Order(2)
    @DisplayName("Wrong file format")
    void wrongFileFormat() {
        assertThrows(NotOfficeXmlFileException.class,
                () -> new XlsxWriter<S1SimpleEntity>(WRONG_FORMAT_FILE_PATH){},
                "No exception when wrong file format (internal structure) given, or wrong exception");
    }

    @Test
    @Order(4)
    @DisplayName("Wrong password")
    void wrongPassword() {
        assertThrows(WrongPasswordException.class,
                () -> new XlsxWriter<PasswordEntity>(PASSWORD_INPUT_FILE_PATH, WRONG_PASSWORD){},
                "No exception using wrong password, or wrong exception");
    }




}