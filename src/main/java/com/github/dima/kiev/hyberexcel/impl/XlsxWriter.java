package com.github.dima.kiev.hyberexcel.impl;

import com.github.dima.kiev.hyberexcel.RecordWriter;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;
import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class XlsxWriter<T extends RowEntity> extends AbstractXlsxSheet<T> implements RecordWriter<T> {

    public XlsxWriter(String filePath, String password) throws IOException, InvalidFormatException, GeneralSecurityException, ClassNotFoundException {
        super(filePath, password, PackageAccess.READ_WRITE);
    }

    public XlsxWriter(String filePath) throws IOException, InvalidFormatException, ClassNotFoundException {
        super(filePath, PackageAccess.READ_WRITE);
    }

    @Override
    public void saveRecords(String jsonRecordsArray) {
        JsonArray recordsArray = new JsonParser().parse(jsonRecordsArray).getAsJsonArray();

        Iterator<JsonElement> jsonRecords = recordsArray.iterator();
        while (jsonRecords.hasNext()) {
            JsonObject record = jsonRecords.next().getAsJsonObject();
            int rowNumber = record.get("row_number").getAsInt();

            Row row = getSheet().getRow(rowNumber - 1); // todo create row if it is not exist!!!!

            createColumns(row, record);
        }
        super.save();
    }

/*    protected void createColumns(Row row, T recordObj) {
        getColumnsToBeSaved().forEach((column) -> {
            try {
                row.createCell(column.getColumnNumber())
                        .setCellValue(getFieldValue(recordObj, column.getOriginalName()));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    } */

    private void createColumns(Row row, T recordObj) {
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

    private void createColumns(Row row, JsonObject record) {
        getColumnsToBeSaved().forEach((column) -> {
                row.createCell(column.getColumnNumber())
                        .setCellValue(record.get(column.getOriginalName()).getAsString());
        });
    }

/*    protected String getFieldValue(T obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        //return (String) field.get(obj);
        return field.get(obj).toString();
    }
*/
    private List<ColumnMetaInfo> getColumnsToBeSaved() {
        return meta.getColumns()
                .stream()
                .filter(ColumnMetaInfo::isOutput)
                .collect(Collectors.toList());
    }

    @Override
    public void saveRecords(Collection<T> records) {
        records.forEach((recordObj) -> {
                Row row = getSheet().getRow(recordObj.rowNumber - 1); // todo create row if it is not exist!!!!
                createColumns(row, recordObj);
        });
        super.save();
    }

    @Override
    public void appendRecords(String jsonRecordsArray) {
        int rowNumber = getSheet().getLastRowNum();
        JsonArray recordsArray = new JsonParser().parse(jsonRecordsArray).getAsJsonArray();

        Iterator<JsonElement> jsonRecords = recordsArray.iterator();
        while (jsonRecords.hasNext()) {
            JsonObject record = jsonRecords.next().getAsJsonObject();

            Row row = getSheet().getRow(++rowNumber);

            createColumns(row, record);
        }
        super.save();
    }

    @Override
    public void appendRecords(Collection<T> records) {
        AtomicInteger rowNumber = new AtomicInteger(getSheet().getLastRowNum());
        records.forEach((recordObj) -> {
                Row row = getSheet().createRow(rowNumber.incrementAndGet());
                createColumns(row, recordObj);
        });
        super.save();
    }

}