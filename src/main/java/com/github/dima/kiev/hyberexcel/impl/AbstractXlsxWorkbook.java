package com.github.dima.kiev.hyberexcel.impl;

import com.github.dima.kiev.hyberexcel.exceptions.FileClosingError;
import com.github.dima.kiev.hyberexcel.exceptions.FileNotFoundExceptionUnchecked;
import com.github.dima.kiev.hyberexcel.exceptions.SecuredFileClosingError;
import com.github.dima.kiev.hyberexcel.exceptions.WrongPasswordException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.security.GeneralSecurityException;

class AbstractXlsxWorkbook implements Closeable {

    XSSFWorkbook workbook;

    private String filePath;
    private String password;

    AbstractXlsxWorkbook(String filePath, String password, PackageAccess accessMode) throws IOException, InvalidFormatException, GeneralSecurityException {
        this(new FileInputStream(new File(filePath)), password, accessMode);
        this.filePath = filePath;
    }

    AbstractXlsxWorkbook(String filePath, PackageAccess accessMode) throws IOException, InvalidFormatException {
        this.filePath = filePath;
        try {
            this.workbook = new XSSFWorkbook(OPCPackage.open(new File(filePath), accessMode));
        } catch (InvalidOperationException causedByFileNotFoundException) {
            throw new FileNotFoundExceptionUnchecked(causedByFileNotFoundException.getCause());
        }
    }

    AbstractXlsxWorkbook(InputStream is, PackageAccess accessMode) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(OPCPackage.open(is));
    }

    AbstractXlsxWorkbook(InputStream is, String password, PackageAccess accessMode) throws IOException, GeneralSecurityException, InvalidFormatException {
        this.password = password;

        POIFSFileSystem fs = new POIFSFileSystem(is);
        EncryptionInfo info = new EncryptionInfo(fs);
        Decryptor d = Decryptor.getInstance(info);
        d.verifyPassword(password);
        try {
            is = d.getDataStream(fs);
        } catch (NullPointerException wrongPasswordException) {
            throw new WrongPasswordException(wrongPasswordException);
        }
        workbook = new XSSFWorkbook(OPCPackage.open(is));
    }

    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new FileClosingError("Some error while closing the file", e);
        }
    }

    protected void save() {
        if (password == null) {
            try (OutputStream bos = new OutputStream(){ @Override public void write(int b) {}}) {
                workbook.write(bos);
                workbook.close();
            } catch (IOException e) {
                throw new FileClosingError("Some error while writing and closing the file", e);
            }
        } else {
            try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
                EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
                Encryptor enc = info.getEncryptor();
                enc.confirmPassword(password);
                OutputStream encryptedDS = enc.getDataStream(fileSystem);
                workbook.write(encryptedDS);
                FileOutputStream fos = new FileOutputStream(filePath);
                fileSystem.writeFilesystem(fos);
                fos.close();
                workbook.close();
            } catch (IOException e) {
                throw new FileClosingError("Some error while closing the file with password", e);
            } catch(GeneralSecurityException e) {
                throw new SecuredFileClosingError("Error closing file according to some security reason", e);
            }
        }
    }

}


