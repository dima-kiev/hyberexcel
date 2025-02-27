package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.JsonType;
import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@RecordEntity
public class DefaultTypeValuesEntity extends RowEntity {

    @RecordField(column = "A") public String stringType = "Sample value";
    @RecordField(column = "B") public Character characterType = 'a';
    @RecordField(column = "C") public Byte byteType = 1;
    @RecordField(column = "D") public Short shortType = 1;
    @RecordField(column = "E") public Integer integerType = 1;
    @RecordField(column = "F") public Long longType = 1L;
    @RecordField(column = "G") public Float floatType = 1.1f;
    @RecordField(column = "H") public Double doubleType = 1.1;
    @RecordField(column = "I") public BigInteger bigIntegerType = new BigInteger("1");
    @RecordField(column = "J") public LocalDate localDateType = LocalDate.now();
    @RecordField(column = "K") public Date dateType = new Date();
    @RecordField(column = "L") public Boolean booleanType = true;

    @RecordField(column = "M") @JsonType public List<Map<String, Object>> jsonArrayType;
    @RecordField(column = "N") @JsonType public Map<String, Object> jsonObjectType;
    @RecordField(column = "O") @JsonType public CustomJson jsonCustomType;
    @RecordField(column = "P") @JsonType public List<CustomJson> jsonListOfCustomType;
    {
        jsonObjectType = new HashMap<String, Object>(){{ put("f_1", "v_1"); put( "f_2", 2.0); }};

        jsonArrayType = new ArrayList<Map<String, Object>>(){{
            add(jsonObjectType);
            add(new HashMap<String, Object>(){{ put("f_1", "v_2"); put("f_2", 2.2); }});
        }};

        jsonCustomType = new CustomJson();
        jsonCustomType.fieldOne = "value_one";
        jsonCustomType.fieldTwo = "value_two";
        jsonCustomType.someValue = 55.55;
        jsonCustomType.jsonObject = jsonObjectType;
        jsonCustomType.jsonArray = jsonArrayType;

        CustomJson second = new CustomJson();
        second.fieldOne = "value_one-2";
        second.fieldTwo = "value_two-2";
        second.someValue = 55.55;
        second.jsonObject = jsonObjectType;
        second.jsonArray = jsonArrayType;

        jsonListOfCustomType = new ArrayList<CustomJson>(){{
           add(jsonCustomType);
           add(second);
        }};
    }

    @Override
    public String toString() {
        return "DefaultTypeValuesEntity{" +
                "stringType='" + stringType + '\'' +
                ", characterType=" + characterType +
                ", byteType=" + byteType +
                ", shortType=" + shortType +
                ", integerType=" + integerType +
                ", longType=" + longType +
                ", floatType=" + floatType +
                ", doubleType=" + doubleType +
                ", bigIntegerType=" + bigIntegerType +
                ", localDateType=" + localDateType +
                ", dateType=" + dateType +
                ", booleanType=" + booleanType +
                ", jsonArrayType=" + jsonArrayType +
                ", jsonObjectType=" + jsonObjectType +
                ", jsonCustomType=" + jsonCustomType +
                ", jsonListOfCustomType=" + jsonListOfCustomType +
                '}';
    }

    public class CustomJson {
        public String fieldOne;
        public String fieldTwo;
        public Double someValue;
        public Map<String, Object> jsonObject;
        public List<Map<String, Object>> jsonArray;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CustomJson that = (CustomJson) o;

            if (!Objects.equals(fieldOne, that.fieldOne)) return false;
            if (!Objects.equals(fieldTwo, that.fieldTwo)) return false;
            if (!Objects.equals(someValue, that.someValue)) return false;
            if (!Objects.equals(jsonObject, that.jsonObject)) return false;
            return Objects.equals(jsonArray, that.jsonArray);
        }

        @Override
        public int hashCode() {
            int result = fieldOne != null ? fieldOne.hashCode() : 0;
            result = 31 * result + (fieldTwo != null ? fieldTwo.hashCode() : 0);
            result = 31 * result + (someValue != null ? someValue.hashCode() : 0);
            result = 31 * result + (jsonObject != null ? jsonObject.hashCode() : 0);
            result = 31 * result + (jsonArray != null ? jsonArray.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "CustomJson{" +
                    "fieldOne='" + fieldOne + '\'' +
                    ", fieldTwo='" + fieldTwo + '\'' +
                    ", someValue=" + someValue +
                    ", jsonObject=" + jsonObject +
                    ", jsonArray=" + jsonArray +
                    '}';
        }
    }
}
