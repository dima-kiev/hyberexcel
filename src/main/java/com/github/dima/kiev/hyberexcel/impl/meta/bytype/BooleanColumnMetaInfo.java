package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

public class BooleanColumnMetaInfo extends ColumnMetaInfo {

    public BooleanColumnMetaInfo(Field field) {
        super(field);
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {

        BiFunction<CellType, Cell, Object> extractBool = (cellType, cell) -> {
            Boolean cellValue = null;
            switch (cellType) {
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case STRING:
                    String rawCellValueS = cell.getStringCellValue();
                    cellValue = "TRUE".equalsIgnoreCase(rawCellValueS.trim());
                    break;
                case NUMERIC:
                    double rawCellValueN = cell.getNumericCellValue();
                    cellValue = rawCellValueN > 0;
                    break;
                case BLANK:
                case ERROR:
                default:
            }
            return cellValue;
        };

        return (Boolean) extractTemplate(row, evaluator, extractBool);
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.BOOLEAN)
                .setCellValue((Boolean) getFieldValue(entity));
    }
}
