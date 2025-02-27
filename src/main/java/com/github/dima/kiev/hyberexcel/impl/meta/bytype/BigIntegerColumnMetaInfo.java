package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.math.BigInteger;

public class BigIntegerColumnMetaInfo extends ColumnMetaInfo {

    public BigIntegerColumnMetaInfo(Field field) {
        super(field);
    }

    // todo !!!!!
    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        BigInteger val = new BigInteger("0");
        try {
            val = new BigInteger("" + extractNumeric(row, evaluator).longValue());
        } catch (Exception ignored) {
            try {
                val = new BigInteger(extractString(row, evaluator));
            } catch (Exception ignored2) { }
        }
        return val;
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.STRING)
                .setCellValue(getFieldValue(entity).toString());
    }
}
