package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;

public class FloatColumnMetaInfo extends ColumnMetaInfo {

    public FloatColumnMetaInfo(Field field) {
        super(field);
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        Float val = 0.0f;
        try {
            val = extractNumeric(row, evaluator).floatValue();
        } catch (Exception ignored) {

        }
        return val;
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.NUMERIC)
                .setCellValue(((Float) getFieldValue(entity)).doubleValue());
    }
}
