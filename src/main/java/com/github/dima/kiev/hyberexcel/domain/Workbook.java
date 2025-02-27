package com.github.dima.kiev.hyberexcel.domain;

import com.github.dima.kiev.hyberexcel.exceptions.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.SecurityException;
import java.security.GeneralSecurityException;

// todo try to make all constructors throws section the same

public class Workbook implements Closeable {

    XSSFWorkbook workbook;
    FormulaEvaluator evaluator;

    //private String filePath;
    private String password;

    public Workbook(InputStream is) {
        try {
            this.workbook = new XSSFWorkbook(OPCPackage.open(is));
        } catch (IOException ioe) {
            throw new FileOperationException("Error opening file.", ioe);
        } catch (InvalidFormatException ife) {
            throw new InvalidXlsxFileFormatException(ife);
        }
    }

    public Workbook(InputStream is, String password) {
        this.password = password;
        try {
            POIFSFileSystem fileSystem = new POIFSFileSystem(is);
            Decryptor decryptor = Decryptor.getInstance(new EncryptionInfo(fileSystem));
            decryptor.verifyPassword(password);
            InputStream decryptedIs = decryptor.getDataStream(fileSystem);
            workbook = new XSSFWorkbook(OPCPackage.open(decryptedIs));
        } catch (InvalidFormatException ife) {
            throw new InvalidXlsxFileFormatException(ife);
        } catch (NullPointerException wrongPasswordException) {
            throw new WrongPasswordException(wrongPasswordException);
        } catch (GeneralSecurityException gse) {
            throw new SecurityException("Error opening file. Some security issue. ", gse);
        } catch (IOException ioe) {
            throw new FileOperationException("Error opening file.", ioe);
        }
    }

    public Workbook(String filePath, String password) throws FileNotFoundException {
        this(new FileInputStream(new File(filePath)), password);
        //this.filePath = filePath;
    }

    public Workbook(String filePath) throws FileNotFoundException {
        this(new FileInputStream(new File(filePath)));
        //this.filePath = filePath;
    }

    @Override
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new FileClosingError("Some error while closing the file", e);
        }
    }

/*
    public void saveAndClose() {
        if (password == null) {
            try (OutputStream nullOutputStream = new OutputStream(){ @Override public void write(int b) {}}) {
                workbook.write(nullOutputStream);
                workbook.close();
            } catch (IOException e) {
                throw new FileClosingError("Some error while writing and closing the file", e);
            }
        } else {
            try (POIFSFileSystem fileSystem = new POIFSFileSystem()) {
                Encryptor encryptor = new EncryptionInfo(EncryptionMode.standard).getEncryptor();
                encryptor.confirmPassword(password);
                OutputStream encryptedOS = encryptor.getDataStream(fileSystem);
                workbook.write(encryptedOS);
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
*/

    FormulaEvaluator getEvaluator() {
        if (evaluator == null) {
            synchronized (this) {
                evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
            }
        }
        return evaluator;
    }

    Sheet getSheet(String sheetName) {
        Sheet requestedSheet = null;
        if (sheetName == null || sheetName.isEmpty()) {
            requestedSheet = getDefaultSheet();
        } else {
            requestedSheet = workbook.getSheet(sheetName);
        }
        if (requestedSheet == null) { // todo check what if no such sheet in workbook
            throw new NoSuchSheetException("no such sheet in workbook: " + sheetName);
        }
        return requestedSheet;
    }

    Sheet getSheet(Integer sheetAt) {
        Sheet requestedSheet = null;
        if (sheetAt == null || sheetAt < 0) {
            requestedSheet = getDefaultSheet();
        } else {
            requestedSheet = workbook.getSheetAt(sheetAt);
        }
        if (requestedSheet == null) { // todo check what if no such sheet in workbook
            throw new NoSuchSheetException("no such sheet# in workbook: " + sheetAt);
        }
        return requestedSheet;
    }

    private Sheet getDefaultSheet() {
        return workbook.getSheetAt(0);
    }

}
