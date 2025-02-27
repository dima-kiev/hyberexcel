package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.function.BiFunction;

public class DateColumnMetaInfo extends ColumnMetaInfo {

    public DateColumnMetaInfo(Field field) {
        super(field);
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        BiFunction<CellType, Cell, Object> extractDate = (cellType, cell) -> {
            Date cellValue = null;
            switch (cellType) {
                case NUMERIC:
                    cellValue = cell.getDateCellValue();
                    break;
                default:
                // todo
            }
            return cellValue;
        };

        return (Date) extractTemplate(row, evaluator, extractDate);
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.NUMERIC)
                .setCellValue((Date) getFieldValue(entity));

    }
}
