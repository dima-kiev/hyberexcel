package com.github.dima.kiev.hyberexcel.domain;

import com.github.dima.kiev.hyberexcel.exceptions.APIMissUseException;
import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import com.github.dima.kiev.hyberexcel.impl.meta.SheetMetaInfo;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class RWriter<E> {

    private SheetMetaInfo meta;
    private WriteAbleWorkbook workbook;
    Supplier<WriteAbleWorkbook> workbookSupplier = () -> workbook;

    public RWriter(WriteAbleWorkbook workbook) {
        this.workbook = workbook;
        this.meta = new SheetMetaInfo(getGenericClass());
    }

    // TODO epic. global refactor to make this lambda sense
    public RWriter(Supplier<WriteAbleWorkbook> workbookSupplier) {
        this.workbookSupplier = workbookSupplier;
        this.meta = new SheetMetaInfo(getGenericClass());
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

    public RWriter appendRecords(Collection<E> records) {
        AtomicInteger rowNumber = new AtomicInteger(workbook.getSheet(meta.getSheetName()).getLastRowNum());
        records.forEach((recordObj) -> {
            Row row = workbook.getSheet(meta.getSheetName()).createRow(rowNumber.incrementAndGet());
            createColumns(row, recordObj);
        });
        //workbook.saveAndClose();
        return this;
    }

    public Path saveToFile() {
        workbook.saveAndClose();
        return workbook.getFilePath();
    }

    private void createColumns(Row row, E recordObj) {
        getColumnsToBeSaved().forEach((column) -> {
            try {
                column.createCellAndSetValue(row, recordObj);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) { // todo own exception
                throw new RuntimeException(e);
            }
        });
    }

    private List<ColumnMetaInfo> getColumnsToBeSaved() {
        return meta.getColumns()
                .stream()
                .filter(ColumnMetaInfo::isOutput)
                .collect(Collectors.toList());
    }

}
