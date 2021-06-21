package com.msampietro.springdownloadmultiplepools.misc;

public interface CSVWriter extends AutoCloseable {

    void writeNext(String[] nextLine);

    void flush();

}
