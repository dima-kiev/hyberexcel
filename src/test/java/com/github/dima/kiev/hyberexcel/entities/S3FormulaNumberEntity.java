package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

@RecordEntity(sheetName = "S3Formulas", startRow = 2)
public class S3FormulaNumberEntity extends RowEntity {

    @RecordField(column = "A") public Integer caseNumber;
    @RecordField(column = "B") public Integer formulaResultValue;
    @RecordField(column = "C") public String expectedResultType;
    @RecordField(column = "D") public Integer expectedValue;

}
