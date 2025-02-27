package com.github.dima.kiev.hyberexcel.domain;

import com.github.dima.kiev.hyberexcel.exceptions.APIMissUseException;
import com.github.dima.kiev.hyberexcel.exceptions.BadPredicateException;
import com.github.dima.kiev.hyberexcel.exceptions.NoSuchFieldForPredicateTestException;
import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import com.github.dima.kiev.hyberexcel.impl.meta.SheetMetaInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RReader<E> {

    private SheetMetaInfo meta;
    private Workbook workbook;
    Supplier<Workbook> workbookSupplier = () -> workbook;

    private final Map<String, Predicate<Object>> predicates = new HashMap<>();

    public RReader(Workbook workbook) {
        this.workbook = workbook;
        this.meta = new SheetMetaInfo(getGenericClass());
    }

    // TODO epic. global refactor to make this lambda sense
    public RReader(Supplier<Workbook> workbookSupplier) {
        this.workbookSupplier = workbookSupplier;
        this.meta = new SheetMetaInfo(getGenericClass());
    }

    public RReader addPredicate(String fieldLabel, Predicate<Object> predicate) {
        this.predicates.put(fieldLabel, predicate);
        return this;
    }

    public List<E> lineObjects() {
        return lineObjects(0, Integer.MAX_VALUE);
    }

    public List<E> lineObjects(int startFromResult, int limit) {

        List<E> records = new ArrayList<>();

        try {
            workbook = workbookSupplier.get();
            Sheet sheet = workbook.getSheet(meta.getSheetName());

            int rowsMatchedEnumerator = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext() && records.size() < limit) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();
                if (rowNumber < meta.getStartRow()) {
                    continue;
                }

                E recordObj = instantiate();
                meta.getColumns().forEach(column -> setFieldValue(recordObj, column, row));
                if (isLimitationsPassed(recordObj)) {
                    if (++rowsMatchedEnumerator >= startFromResult) {
                        records.add(recordObj);
                    }
                }
            }
        } finally {
            workbook.close();
        }

        return records;
    }

    public List<Map<String, String>> linesAsMap() {
        return linesAsMap(0, Integer.MAX_VALUE);
    }

    public List<Map<String, String>> linesAsMap(int startFromResult, int limit) {

        List<Map<String, String>> records = new ArrayList<>();

        try {
            workbook = workbookSupplier.get();
            Sheet sheet = workbook.getSheet(meta.getSheetName());

            int rowsMatchedEnumerator = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext() && records.size() < limit) {
                Row row = rowIterator.next();
                int rowNumber = row.getRowNum();
                if (rowNumber < meta.getStartRow()) {
                    continue;
                }

                Map<String, String> record = new HashMap<>();
                meta.getColumns().forEach(column -> record.put(column.getOriginalName(),
                                                               column.extract(row, workbook.getEvaluator()).toString()));
                if (isLimitationsPassed(record)) {
                    if (++rowsMatchedEnumerator >= startFromResult) {
                        records.add(record);
                    }
                }
            }
        } finally {
            workbook.close();
        }

        return records;
    }

    public List<String> labels() {
        return meta.getColumns().stream().map(ColumnMetaInfo::getOriginalName).collect(Collectors.toList());
    }

    // TODO make this meth singleton like to not determine it each time - only once
    @SuppressWarnings("unchecked")
    private Class<E> getGenericClass() {
        Class<E> clazz = null;
        try {
            Type genericSuperclass = getClass().getGenericSuperclass();
            Type tType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            String className = tType.getTypeName();

 //  test Wrong Generic Entity type
 // TODO           if (className == "java.lang.Object") { // TODO Validate entity by checking the annotations and throw the own exception here
 //               throw new APIMissUseException();
 //           }

            clazz = (Class<E>) Class.forName(className);
        } catch (ClassCastException e) {
            throw new APIMissUseException(e);
        } catch (ClassNotFoundException ignored) {} // it is ok here. impossible

        return clazz;
    }

    private E instantiate() {
        E entity = null;
        try {
            entity = getGenericClass().newInstance();
        } catch (IllegalAccessException | InstantiationException ignored) {}  // it`s ok here. impossible

        return entity;
    }

    private void setFieldValue(E obj, ColumnMetaInfo col, Row row) {
        try {
            Field field = obj.getClass().getDeclaredField(col.getOriginalName());
            field.setAccessible(true);
            field.set(obj, col.extract(row, workbook.getEvaluator()));
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException ignored) {
            // EPIC TODO            // todo check this suppression
        }
    }

    private Object getFieldValue(E obj, String fieldName) {
        Object cellValue = new Object();
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            cellValue = field.get(obj);
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
                .allMatch( label -> {
                    try {
                        return predicates.get(label).test(getFieldValue(obj, label));
                    } catch (NoSuchFieldForPredicateTestException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new BadPredicateException("Bad predicate for label: " + label, e);
                    }
                });
    }

    private boolean isLimitationsPassed(Map<String, String> record) {
        return predicates
                .keySet()
                .stream()
                .allMatch( label -> {
                    try {
                        return predicates.get(label).test(record.get(label));
                    } catch (NoSuchFieldForPredicateTestException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new BadPredicateException("Bad predicate for label: " + label, e);
                    }
                });
    }

}
