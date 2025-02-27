package com.github.dima.kiev.hyberexcel.entities;

import com.github.dima.kiev.hyberexcel.annotations.RecordEntity;
import com.github.dima.kiev.hyberexcel.annotations.RecordField;
import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;

import java.util.Objects;

@RecordEntity(startRow = 1)
public class S1SimpleEntity  extends RowEntity {

    @RecordField(column = "A") public String columnA;
    @RecordField(column = "B") public String columnB;
    @RecordField(column = "C") public String columnC;
    @RecordField(column = "D") public String columnD;
    @RecordField(column = "E") public String columnE;
    @RecordField(column = "F") public String columnF;
    @RecordField(column = "G") public String columnG;
    @RecordField(column = "H") public String columnH;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        S1SimpleEntity that = (S1SimpleEntity) o;

        if (!Objects.equals(columnA, that.columnA)) return false;
        if (!Objects.equals(columnB, that.columnB)) return false;
        if (!Objects.equals(columnC, that.columnC)) return false;
        if (!Objects.equals(columnD, that.columnD)) return false;
        if (!Objects.equals(columnE, that.columnE)) return false;
        if (!Objects.equals(columnF, that.columnF)) return false;
        if (!Objects.equals(columnG, that.columnG)) return false;
        return Objects.equals(columnH, that.columnH);
    }

    @Override
    public int hashCode() {
        int result = columnA != null ? columnA.hashCode() : 0;
        result = 31 * result + (columnB != null ? columnB.hashCode() : 0);
        result = 31 * result + (columnC != null ? columnC.hashCode() : 0);
        result = 31 * result + (columnD != null ? columnD.hashCode() : 0);
        result = 31 * result + (columnE != null ? columnE.hashCode() : 0);
        result = 31 * result + (columnF != null ? columnF.hashCode() : 0);
        result = 31 * result + (columnG != null ? columnG.hashCode() : 0);
        result = 31 * result + (columnH != null ? columnH.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S1SimpleEntity{" +
                "columnA='" + columnA + '\'' +
                ", columnB='" + columnB + '\'' +
                ", columnC='" + columnC + '\'' +
                ", columnD='" + columnD + '\'' +
                ", columnE='" + columnE + '\'' +
                ", columnF='" + columnF + '\'' +
                ", columnG='" + columnG + '\'' +
                ", columnH='" + columnH + '\'' +
                ", rowNumber=" + rowNumber +
                '}';
    }
}
