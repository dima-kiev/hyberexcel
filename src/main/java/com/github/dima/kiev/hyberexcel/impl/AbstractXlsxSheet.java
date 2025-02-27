package com.github.dima.kiev.hyberexcel.impl;

import com.github.dima.kiev.hyberexcel.impl.meta.RowEntity;
import com.github.dima.kiev.hyberexcel.impl.meta.SheetMetaInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;

public class AbstractXlsxSheet<E extends RowEntity> extends AbstractXlsxWorkbook {

    protected SheetMetaInfo meta;
    private Sheet sheet;
    protected FormulaEvaluator evaluator;

    public AbstractXlsxSheet(String filePath, String password, PackageAccess accessMode) throws IOException, InvalidFormatException, GeneralSecurityException, ClassNotFoundException {
        super(filePath, password, accessMode);
        this.meta = new SheetMetaInfo(getGenericClass());
        this.sheet = meta.getSheet(workbook);
    }

    public AbstractXlsxSheet(String filePath, PackageAccess accessMode) throws IOException, InvalidFormatException, ClassNotFoundException {
        super(filePath, accessMode);
        this.meta = new SheetMetaInfo(getGenericClass());
        this.sheet = meta.getSheet(workbook);
    }

    public AbstractXlsxSheet(InputStream is, String password, PackageAccess accessMode) throws IOException, InvalidFormatException, GeneralSecurityException, ClassNotFoundException {
        super(is, password, accessMode);
        this.meta = new SheetMetaInfo(getGenericClass());
        this.sheet = meta.getSheet(workbook);
    }

    public AbstractXlsxSheet(InputStream is, PackageAccess accessMode) throws IOException, InvalidFormatException, ClassNotFoundException {
        super(is, accessMode);
        this.meta = new SheetMetaInfo(getGenericClass());
        this.sheet = meta.getSheet(workbook);
    }

    Sheet getSheet() {
        return this.sheet;
    }

    FormulaEvaluator getEvaluator() {
        if (evaluator == null) {
            evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
        }
        return evaluator;
    }

    @SuppressWarnings("unchecked")
    Class<E> getGenericClass() throws ClassNotFoundException {
        Type mySuperclass = getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
        String className = tType.getTypeName();


        return (Class<E>) Class.forName(className);
    }

}
