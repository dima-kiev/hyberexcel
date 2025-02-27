package com.github.dima.kiev.hyberexcel.impl.meta;

import com.github.dima.kiev.hyberexcel.annotations.JsonType;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.exceptions.UnsupportedFieldTypeException;
import com.github.dima.kiev.hyberexcel.impl.meta.bytype.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;

public abstract class ColumnMetaInfo {

    private boolean output;
    private String column;
    private int columnNumber;
    private String originalName;
    private Class<?> type;

    public ColumnMetaInfo(Field field) {
        this.type = field.getType();
        this.originalName = field.getName();
        parseMeta(field.getAnnotation(RecordField.class));
    }

    private void parseMeta(RecordField fieldMeta) {
        this.column = fieldMeta.column();
        this.output = fieldMeta.output();
        this.columnNumber = parseColumnName(this.column);
    }

    static ColumnMetaInfo getColumnMetaInfoForFieldType(Field field) {
        ColumnMetaInfo cmi = null;
        if (field.isAnnotationPresent(JsonType.class)) {
            cmi = new JsonColumnMetaInfo(field);
        } else {
            switch (field.getType().toString()) {
                case "class java.lang.String":
                    cmi = new StringColumnMetaInfo(field);
                    break;
                case "class java.lang.Character":
                    cmi = new CharacterColumnMetaInfo(field);
                    break;
                case "class java.lang.Byte":
                    cmi = new ByteColumnMetaInfo(field);
                    break;
                case "class java.lang.Short":
                    cmi = new ShortColumnMetaInfo(field);
                    break;
                case "class java.lang.Integer":
                    cmi = new IntegerColumnMetaInfo(field);
                    break;
                case "class java.lang.Long":
                    cmi = new LongColumnMetaInfo(field);
                    break;
                case "class java.lang.Float":
                    cmi = new FloatColumnMetaInfo(field);
                    break;
                case "class java.lang.Double":
                    cmi = new DoubleColumnMetaInfo(field);
                    break;
                case "class java.math.BigInteger":
                    cmi = new BigIntegerColumnMetaInfo(field);
                    break;
                case "class java.time.LocalDate":
                    cmi = new LocalDateColumnMetaInfo(field);
                    break;
                case "class java.util.Date":
                    cmi = new DateColumnMetaInfo(field);
                    break;
                case "class java.lang.Boolean":
                    cmi = new BooleanColumnMetaInfo(field);
                    break;
                default:
                    throw new UnsupportedFieldTypeException("Unsupported type of field in entity class: " + field.getType().toString() + ". Please use one of: String, Character, Byte, Short, Integer, Long, Float, Double, BigInteger, LocalDate, Date, Boolean. Or special type annotation");
            }
        }
        return cmi;
    }

    public abstract void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException;

    protected Object getFieldValue(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(getOriginalName());
        field.setAccessible(true);
        return field.get(obj);
    }

    public abstract Object extract(Row row, FormulaEvaluator evaluator);

    protected Object extractTemplate(Row row, FormulaEvaluator evaluator, BiFunction<CellType, Cell, Object> extract) {
        //Object res = ""; // TODO null;
        Object res = null;
        Cell cell = row.getCell(this.getColumnNumber());
        if (cell != null) {
            CellType cellType = cell.getCellType();
            if (cellType == FORMULA) {
                evaluator.evaluateFormulaCell(cell);
                cellType = cell.getCachedFormulaResultType();
            }
            res =  extract.apply(cellType, cell);
        }
        return res;
    }

    protected String extractString(Row row, FormulaEvaluator evaluator) {
        BiFunction<CellType, Cell, Object> extractor = (cellType, cell) -> {
            String cellValue = "";
            switch (cellType) {
                case NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getStringCellValue();
                    break;
                default:
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getStringCellValue();
            }
            return cellValue;
        };
        String extracted = (String) extractTemplate(row, evaluator, extractor);
        return extracted == null ? "" : extracted;
    }

    protected Double extractNumeric(Row row, FormulaEvaluator evaluator) {
        BiFunction<CellType, Cell, Object> extractor = (cellType, cell) -> {
            Double cellValue;
            switch (cellType) {
                case NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case BLANK:
                    cellValue = null;
                    break;
                case ERROR:
                    cell.setCellType(CellType.STRING);
                    throw new RuntimeException("Some error there is in cell: " + cell.getStringCellValue());
                case BOOLEAN:
                    throw new RuntimeException("Can`t parse Boolean cell value: " + cell.getBooleanCellValue() + " to Numeric value");
                case STRING:
                default:
                    cell.setCellType(CellType.STRING);
                    try {
                        cellValue = Double.parseDouble(cell.getStringCellValue());
                    } catch (Exception e) {
                        throw new RuntimeException("Can`t parse String cell value: " + cell.getStringCellValue() + " to Numeric value");
                    }
            }
            return cellValue;
        };
        return (Double) extractTemplate(row, evaluator, extractor);
    }

    private int parseColumnName(String xlsxColumn) {
        int colNum;

        int rangeStart = (int) 'A';
        int rangeEnd = (int) 'Z';
        xlsxColumn = xlsxColumn.toUpperCase();

        if (xlsxColumn.length() == 1) {
            colNum = (int) xlsxColumn.charAt(0) - rangeStart;
        } else if (xlsxColumn.length() == 2) {
            colNum = ((int) xlsxColumn.charAt(0) - rangeStart + 1) * (rangeEnd - rangeStart) + ((int) xlsxColumn.charAt(1) - rangeStart + 1);
        } else {
            throw new IllegalArgumentException("can`t parse xlsx column name. Should be like 'G' or 'AT', but actually have: " + xlsxColumn);
        }

        return colNum;
    }

    public boolean isOutput() {
        return output;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Class<?> getType() {
        return type;
    }

}
