package com.github.dima.kiev.hyberexcel.impl;

import com.github.dima.kiev.hyberexcel.exceptions.BadPredicateException;
import com.github.dima.kiev.hyberexcel.exceptions.NoSuchFieldForPredicateTestException;
import com.github.dima.kiev.hyberexcel.RecordReader;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;
import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class XlsxReader<E extends RowEntity> extends AbstractXlsxSheet<E> implements RecordReader<E> {

    private Map<String, Predicate<Object>> predicates = new HashMap<>();

    public XlsxReader(String filePath, String password) throws IOException, InvalidFormatException, GeneralSecurityException, ClassNotFoundException {
        super(filePath, password, PackageAccess.READ);
    }

    public XlsxReader(String filePath) throws IOException, InvalidFormatException, ClassNotFoundException {
        super(filePath, PackageAccess.READ);
    }

    public XlsxReader(InputStream is, String password) throws IOException, InvalidFormatException, GeneralSecurityException, ClassNotFoundException {
        super(is, password, PackageAccess.READ);
    }

    public XlsxReader(InputStream is) throws IOException, InvalidFormatException, ClassNotFoundException {
        super(is, PackageAccess.READ);
    }

    @Override
    public List<Map<String, String>> records() throws IOException {
        return records(0, Integer.MAX_VALUE);
    }

    @Override
    public List<E> lines() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        return lines(0, Integer.MAX_VALUE);
    }

    /******************************************
     *
     * @param startFromResult means 1 as first
     * @param limit -
     * @return -
     *
     *****************************************/
    @Override
    public List<Map<String, String>> records(int startFromResult, int limit) throws IOException {

        List<Map<String, String>> records = new ArrayList<>();

        try {
            Sheet sheet = getSheet();

            int rowsMatchedEnumerator = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext() && records.size() < limit) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();
                if (rowNumber < meta.getStartRow()) {
                    continue;
                }

                Map<String, String> record = new HashMap<>();
                meta.getColumns().forEach(column -> record.put(column.getOriginalName(), column.extract(row, getEvaluator()).toString()));
                if (isLimitationsPassed(record)) {
                    if (++rowsMatchedEnumerator >= startFromResult) {
                        record.put("row_number", String.valueOf(rowNumber + 1)); // +1 to correct shoe excel row numbers in views
                        records.add(record);
                    }
                }
            }
        } finally {
            super.close();
        }

        return records;
    }

    /******************************************
     *
     * @param startFromResult means 1 as first
     * @param limit -
     * @return -
     *
     *****************************************/
    @Override
    public List<E> lines(int startFromResult, int limit) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

        List<E> records = new ArrayList<>();

        try {
            Sheet sheet = getSheet();

            int rowsMatchedEnumerator = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext() && records.size() < limit) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();
                if (rowNumber < meta.getStartRow()) {
                    continue;
                }

                E recordObj = getGenericClass().newInstance();
                meta.getColumns().forEach(column -> setFieldValue(recordObj, column, row));
                if (isLimitationsPassed(recordObj)) {
                    if (++rowsMatchedEnumerator >= startFromResult) {
                        recordObj.setRowNumber(rowNumber + 1); // +1 to correct shoe excel row numbers in views
                        records.add(recordObj);
                    }
                }
            }
        } finally {
            super.close();
        }

        return records;
    }

    private void setFieldValue(E obj, ColumnMetaInfo col, Row row) {
        try {
            Field field = obj.getClass().getDeclaredField(col.getOriginalName());
            field.setAccessible(true);
            field.set(obj, col.extract(row, getEvaluator()));
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException ignored) {
            // EPIC TODO            // todo check this suppression
        }
    }

    private Object getFieldValue(E obj, String fieldName) {
        String cellValue = "";
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            cellValue = (String) field.get(obj);
        } catch (IllegalAccessException ignored) {
            // SUPPRESS. It is ok here
        } catch (NoSuchFieldException e) {
            // todo log
            throw new NoSuchFieldForPredicateTestException("Please check predicate for field name: " + fieldName, e);
        }
        return cellValue;
    }

    private boolean isLimitationsPassed(E obj) {
        return predicates
                .keySet()
                .stream()
                .allMatch( label -> {   try {
                                            return predicates.get(label).test(getFieldValue(obj, label));
                                        } catch (NoSuchFieldForPredicateTestException e) {
                                            throw e;
                                        } catch (Exception e) {
                                            throw new BadPredicateException("Bad predicate for label: " + label, e);
                                        }
                });
    }

    private boolean isLimitationsPassed(Map<String, String> recordToTest) {
        return predicates
                .keySet()
                .stream()
                .noneMatch(label -> recordToTest
                                        .entrySet()
                                        .stream()
                                        .filter(entry -> entry.getKey().equals(label))
                                        .allMatch(entry -> predicates.get(label).test(entry.getValue())));
    }

    @Override
    public List<String> getFieldLabels() {
        return meta.getColumns()
                .stream()
                .map(ColumnMetaInfo::getOriginalName)
                .collect(Collectors.toList());// todo ??? << "row_number";
    }

    @Override
    @SuppressWarnings("unchecked")
    public XlsxReader addPredicate(String fieldLabel, Predicate<Object> predicate) {
        this.predicates.put(fieldLabel, predicate);
        return this;
    }

}
