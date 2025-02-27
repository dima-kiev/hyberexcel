package com.github.dima.kiev.hyberexcel.reader;

import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.entities.S3FormulaBoolEntity;
import com.github.dima.kiev.hyberexcel.entities.S3FormulaDateEntity;
import com.github.dima.kiev.hyberexcel.entities.S3FormulaNumberEntity;
import com.github.dima.kiev.hyberexcel.entities.S3FormulaStringEntity;
import com.github.dima.kiev.hyberexcel.impl.XlsxReader;
import com.github.dima.kiev.hyberexcel.util.Resources;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("1070. Formula evaluation")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class T1070_ReaderFormulaEvaluationTest {

    private static final String INPUT_FILE_PATH = Resources.getAbsPathOf("HyberExcel.xlsx");

    @Test
    @Order(1)
    @DisplayName("Number result formula")
    void evaluateNumber() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        RecordReader<S3FormulaNumberEntity> reader = new XlsxReader<S3FormulaNumberEntity>(INPUT_FILE_PATH){};
        reader.addPredicate("expectedResultType", (val) -> val.equals("number"));
        List<S3FormulaNumberEntity> records = reader.lines();

        records.forEach(rec -> {
            assertEquals("number", rec.expectedResultType, "Wrong case loaded for case #: " + rec.caseNumber);
            assertEquals(rec.expectedValue, rec.formulaResultValue, "Evaluated value is not the same to expected for case #: " + rec.caseNumber);
        });
    }

    // TODO: why this warn: Cleaning up unclosed ZipFile for archive C:\Users\dimak\IdeaProjects\hyberexcel\target\test-classes\HyberExcel.xlsx
    @Test
    @Order(2)
    @DisplayName("String result formula")
    void evaluateString() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        RecordReader<S3FormulaStringEntity> reader = new XlsxReader<S3FormulaStringEntity>(INPUT_FILE_PATH){};
        reader.addPredicate("expectedResultType", (val) -> val.equals("string result"));
        List<S3FormulaStringEntity> records = reader.lines();

        records.forEach(rec -> {
            assertEquals("string result", rec.expectedResultType, "Wrong case loaded for case #: " + rec.caseNumber);
            assertEquals(rec.expectedValue, rec.formulaResultValue, "Evaluated value is not the same to expected for case #: " + rec.caseNumber);
        });
    }

    @Test
    @Order(3)
    @DisplayName("Date result formula")
    void evaluateDate() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        RecordReader<S3FormulaDateEntity> reader = new XlsxReader<S3FormulaDateEntity>(INPUT_FILE_PATH){};
        reader.addPredicate("expectedResultType", (val) -> val.equals("date"));
        List<S3FormulaDateEntity> records = reader.lines();

        records.forEach(rec -> {
            assertEquals("date", rec.expectedResultType, "Wrong case loaded for case #: " + rec.caseNumber);
            assertEquals(rec.expectedValue, rec.formulaResultValue, "Evaluated value is not the same to expected for case #: " + rec.caseNumber);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Boolean result formula")
    void evaluateBoolean() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvalidFormatException {
        RecordReader<S3FormulaBoolEntity> reader = new XlsxReader<S3FormulaBoolEntity>(INPUT_FILE_PATH){};
        reader.addPredicate("expectedResultType", (val) -> val.equals("boolean"));
        List<S3FormulaBoolEntity> records = reader.lines();

        records.forEach(rec -> {
            assertEquals("boolean", rec.expectedResultType, "Wrong case loaded for case #: " + rec.caseNumber);
            assertEquals(rec.expectedValue, rec.formulaResultValue, "Evaluated value is not the same to expected for case #: " + rec.caseNumber);
        });
    }



}