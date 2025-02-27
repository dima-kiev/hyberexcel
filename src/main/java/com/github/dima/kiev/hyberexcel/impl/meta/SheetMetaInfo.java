package com.github.dima.kiev.hyberexcel.impl.meta;

import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SheetMetaInfo {

    private String sheetName;
    private int startRow;
    private List<ColumnMetaInfo> columns;

    public SheetMetaInfo(Class<?> recordEntity) {
//        assert(recordEntity.isAnnotationPresent(RecordEntity.class));
        parseSheetMeta(recordEntity);
        parseFieldsMeta(recordEntity);
    }

    private void parseSheetMeta(Class<?> recordEntity) {
        RecordEntity entity = recordEntity.getAnnotation(RecordEntity.class);

        this.sheetName = entity.sheetName();
        this.startRow = entity.startRow();
    }

    private void parseFieldsMeta(Class<?> recordEntity) {
        columns = Stream.of(recordEntity.getDeclaredFields())
                        .peek(field -> field.setAccessible(true))
                        .filter(field -> field.isAnnotationPresent(RecordField.class))
                        .map(ColumnMetaInfo::getColumnMetaInfoForFieldType)
                        .collect(Collectors.toList());
    }

    public String getSheetName() {
        return sheetName;
    }

    public Sheet getSheet(XSSFWorkbook workbook) {
        Sheet sheet = null;
        if (sheetName.isEmpty()) {
            sheet = workbook.getSheetAt(0);
        } else {
            sheet = workbook.getSheet(sheetName);
        }
        return sheet;
    }

    public int getStartRow() {
        return startRow;
    }

    public List<ColumnMetaInfo> getColumns() {
        return columns;
    }

}
