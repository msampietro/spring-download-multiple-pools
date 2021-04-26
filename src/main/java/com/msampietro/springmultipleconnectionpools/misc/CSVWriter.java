package com.msampietro.springmultipleconnectionpools.misc;

public interface CSVWriter extends AutoCloseable {

    void writeNext(String[] nextLine);

    void flush();

}
