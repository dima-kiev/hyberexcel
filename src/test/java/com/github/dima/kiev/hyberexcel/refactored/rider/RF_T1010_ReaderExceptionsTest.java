package com.github.dima.kiev.hyberexcel.refactored.rider;

import com.github.dima.kiev.hyberexcel.domain.RReader;
import com.github.dima.kiev.hyberexcel.domain.Workbook;
import com.github.dima.kiev.hyberexcel.entities.PasswordEntity;
import com.github.dima.kiev.hyberexcel.entities.S1SimpleEntity;
import com.github.dima.kiev.hyberexcel.entities.UnsupportedTypeEntity;
import com.github.dima.kiev.hyberexcel.exceptions.*;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("RF1010. Exceptions")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RF_T1010_ReaderExceptionsTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");
    private static final String PASSWORD_INPUT_FILE_PATH = Resources.getAbsPathOf("password.xlsx");
    private static final String WRONG_PASSWORD = "wrong password";
    private static final String WRONG_FORMAT_FILE_PATH = Resources.getAbsPathOf("WrongFormat.xlsx");
    private static final String UNEXISTING_FILE_PATH = "unExisting.xlsx";

    @Test
    @Order(1)
    @DisplayName("Wrong file path")
    void wrongFilePath() {
        assertThrows(FileNotFoundException.class,
                () -> new RReader<S1SimpleEntity>(new Workbook(UNEXISTING_FILE_PATH)){},
                "No Exception when wrong file path given, or wrong exception");
    }

    @Test
    @Order(2)
    @DisplayName("Wrong file format")
    void wrongFileFormat() {
        assertThrows(NotOfficeXmlFileException.class,
                () -> new RReader<S1SimpleEntity>(new Workbook(WRONG_FORMAT_FILE_PATH)){},
                "No exception when wrong file format (internal structure) given, or wrong exception");
    }

    @Test
    @Order(3)
    @DisplayName("Empty input stream")
    void emptyInputStream() {
        assertThrows(EmptyFileException.class,
                () -> new RReader<S1SimpleEntity>(new Workbook(new ByteArrayInputStream(new byte[] {}))){},
                "No exception when empty input stream given, or wrong exception");
    }

    @Test
    @Order(4)
    @DisplayName("Wrong password")
    void wrongPassword() {
        assertThrows(WrongPasswordException.class,
                () -> new RReader<PasswordEntity>(new Workbook(PASSWORD_INPUT_FILE_PATH, WRONG_PASSWORD)){},
                "No exception using wrong password, or wrong exception");
    }

    @Test
    @Order(5)
    @DisplayName("Bad col name for predicate")
    void badColNameForPredicate() throws InvalidFormatException, IOException, ClassNotFoundException {
        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(new Workbook(INPUT_FILE_PATH)){};
        reader.addPredicate("wrongCoolName", (val) -> !((String)val).contains("something"));

        assertThrows(NoSuchFieldForPredicateTestException.class,
                () -> reader.lineObjects(0, 10),
                "No exception with bad column name for predicate, or wrong exception");
    }

    @Test
    @Order(6)
    @DisplayName("Bad predicate for column")
    void typeMismatchForColAndPredicate() throws InvalidFormatException, IOException, ClassNotFoundException {
        RReader<S1SimpleEntity> reader = new RReader<S1SimpleEntity>(new Workbook(INPUT_FILE_PATH)){};
        reader.addPredicate("columnG", (val) -> ((Integer)val).equals(5));

        assertThrows(BadPredicateException.class,
                () -> reader.lineObjects(0, 10),
                "No exception with bad column predicate for predicate, or wrong exception");
    }

    @Test
    @Order(7)
    @DisplayName("Unsupported entity field type")
    void unsupportedType() {
        assertThrows(UnsupportedFieldTypeException.class,
                () -> new RReader<UnsupportedTypeEntity>(new Workbook(INPUT_FILE_PATH)){},
                "No UnsupportedFieldTypeException exception for unsupported entity field type, or wrong exception");
    }

    @Test
    @Order(8)
    @DisplayName("Forget {} trick for Generics in runtime")
    void apiMissusing() {
        assertThrows(APIMissUseException.class,
                () -> new RReader<S1SimpleEntity>(new Workbook(INPUT_FILE_PATH)), // <- please pay attention to the absence of {} in the end
                "No UnsupportedFieldTypeException exception for unsupported entity field type, or wrong exception");
    }

    @Disabled
    @Test
    @Order(9)
    @DisplayName("Wrong Generic Entity type")
    void wrongGenericEntityType() {
        assertThrows(APIMissUseException.class,
                () -> new RReader<Integer>(new Workbook(INPUT_FILE_PATH)){},
                "No UnsupportedFieldTypeException exception for unsupported entity field type, or wrong exception");
    }


}
