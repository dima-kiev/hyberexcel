package com.github.dima.kiev.hyberexcel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;

public interface RecordWriter<E> {

    void appendRecords(String jsonRecordsArray) throws IOException, GeneralSecurityException;
    void appendRecords(Collection<E> records) throws IOException, GeneralSecurityException;

    void saveRecords(Collection<E> records) throws IOException, GeneralSecurityException;
    void saveRecords(String jsonRecordsArray) throws IOException, GeneralSecurityException;

}
