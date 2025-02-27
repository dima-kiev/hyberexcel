package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;

public class CharacterColumnMetaInfo extends ColumnMetaInfo {

    public CharacterColumnMetaInfo(Field field) {
        super(field);
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        String cellValue = extractString(row, evaluator).trim();
        if (cellValue.length() > 1) {
            //throw new RuntimeException("Wrong Character value in source file: " + cellValue);
            return cellValue.charAt(0); // todo
        } else if (cellValue.length()== 0) {
            return ' '; // todo
        }
        return cellValue.charAt(0);
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.STRING)
           .setCellValue(getFieldValue(entity).toString());
    }
}
