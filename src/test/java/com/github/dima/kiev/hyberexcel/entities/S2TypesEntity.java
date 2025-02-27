package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.JsonType;
import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RecordEntity(sheetName = "S2Types", startRow = 2)
public class S2TypesEntity extends RowEntity {

    @RecordField(column = "A") public String stringType;
    @RecordField(column = "B") public Character characterType;
    @RecordField(column = "C") public Byte byteType;
    @RecordField(column = "D") public Short shortType;
    @RecordField(column = "E") public Integer integerType;
    @RecordField(column = "F") public Long longType;
    @RecordField(column = "G") public Float floatType;
    @RecordField(column = "H") public Double doubleType;
    @RecordField(column = "I") public BigInteger bigIntegerType;
    @RecordField(column = "J") public LocalDate localDateType;
    @RecordField(column = "K") public Date dateType;
    @RecordField(column = "L") public Boolean booleanType;

    @RecordField(column = "M") @JsonType public List<Map<String, Object>> jsonArrayType;
    @RecordField(column = "N") @JsonType public Map<String, Object> jsonObjectType;
    @RecordField(column = "O") @JsonType public CustomJson jsonCustomType;
    @RecordField(column = "P") @JsonType public List<CustomJson> jsonListOfCustomType;

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
    }
}
