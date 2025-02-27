package com.github.dima.kiev.hyberexcel;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface RecordReader<E> {

    RecordReader<E> addPredicate(String fieldLabel, Predicate<Object> predicate);

    List<E> lines() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException;
    List<E> lines(int startFrom, int limit) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException;

    List<Map<String, String>> records() throws IOException;
    List<Map<String, String>> records(int startFromResult, int limit) throws IOException;

    List<String> getFieldLabels();

}
