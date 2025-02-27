package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.BiFunction;

public class LocalDateColumnMetaInfo extends ColumnMetaInfo {

    public LocalDateColumnMetaInfo(Field field) {
        super(field);
    }

    // https://stackoverflow.com/questions/19028192/converting-number-representation-of-date-in-excel-to-date-in-java

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        BiFunction<CellType, Cell, Object> extractDate = (cellType, cell) -> {
            Date cellValue = new Date();
            switch (cellType) {
                case NUMERIC:
                    cellValue = cell.getDateCellValue();
                    break;
                default:
                    // todo
            }
            return cellValue;
        };
        LocalDate result = null;
        Date extractedDate = (Date)extractTemplate(row, evaluator, extractDate);
        if (extractedDate != null) {
            result = new java.sql.Date(extractedDate.getTime()).toLocalDate();
        }

        return result;
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        row.createCell(getColumnNumber(), CellType.NUMERIC)
           .setCellValue(Date.from(((LocalDate) getFieldValue(entity)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
    }
}
