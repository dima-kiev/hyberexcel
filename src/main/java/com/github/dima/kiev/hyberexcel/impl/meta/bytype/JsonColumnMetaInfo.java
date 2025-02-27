package com.github.dima.kiev.hyberexcel.impl.meta.bytype;

import com.github.dima.kiev.hyberexcel.impl.meta.ColumnMetaInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class JsonColumnMetaInfo extends ColumnMetaInfo {

    private Field field;

    public JsonColumnMetaInfo(Field field) {
        super(field);
        this.field = field;
    }

    @Override
    public Object extract(Row row, FormulaEvaluator evaluator) {
        String rowCellValue = extractString(row, evaluator);
        try {
            return new Gson().fromJson(rowCellValue, field.getType());
        } catch (Exception ignored) {
            /// EPIC TODO
        }
        return "JSON ERROR";
    }

    @Override
    public void createCellAndSetValue(Row row, Object entity) throws NoSuchFieldException, IllegalAccessException {
        Object v = getFieldValue(entity);
        Class<?> clz = field.getType();

        System.out.println(v.getClass() + " " +  field.getType());

        if (field.getType().toString().contains("List")) {
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Type listClass = listType.getActualTypeArguments()[0];
            System.out.println("List<    " + listClass);
        }



        String str = v.toString();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
        String json = gson.toJson(field.getType().cast(v));
        if (field.getType().toString().contains("Map")) {
            Map<String, Object> m = new HashMap<>();
            ((Map<String, Object>)v).entrySet().forEach((Map.Entry entry) -> m.put((String) entry.getKey(), entry.getValue()));
            json = gson.toJson(m);
        }
/*        if (clz.isAssignableFrom(Map.class)) {
            json = gson.toJson((Map<String, Object>) v);
        } else if (clz.isAssignableFrom(List.class)) {
            json = gson.toJson((List<Map<String, Object>>) v);
        }
*/

        row.createCell(getColumnNumber(), CellType.STRING)
                .setCellValue(json);

    }
}
