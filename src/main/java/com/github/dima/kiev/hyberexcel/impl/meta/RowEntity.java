package com.github.dima.kiev.hyberexcel.impl.meta;

public abstract class RowEntity {

    public int rowNumber;
    private int fileName;
    private int sheetName;

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
