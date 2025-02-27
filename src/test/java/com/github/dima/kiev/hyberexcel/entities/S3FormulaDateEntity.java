package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

import java.time.LocalDate;

@RecordEntity(sheetName = "S3Formulas", startRow = 2)
public class S3FormulaDateEntity extends RowEntity {

    @RecordField(column = "A") public Integer caseNumber;
    @RecordField(column = "B") public LocalDate formulaResultValue;
    @RecordField(column = "C") public String expectedResultType;
    @RecordField(column = "D") public LocalDate expectedValue;

}
