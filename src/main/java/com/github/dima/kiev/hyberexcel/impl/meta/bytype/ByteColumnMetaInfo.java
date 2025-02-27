package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;

public class ByteColumnMetaInfo extends ColumnMetaInfo {

    public ByteColumnMetaInfo(Field field) {
        super(field);
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        Byte val = 0;
        try {
            val = extractNumeric(row, evaluator).byteValue();
        } catch (Exception ignored) {

        }
        return val;
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.NUMERIC)
                .setCellValue(((Byte) getFieldValue(entity)).doubleValue());
    }
}
