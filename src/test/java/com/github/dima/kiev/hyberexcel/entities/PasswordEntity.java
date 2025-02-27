package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

@RecordEntity(startRow = 1)
public class PasswordEntity extends RowEntity {

    @RecordField(column = "A") public String columnA;

}
