package com.msampietro.springdownloadmultiplepools.misc;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CSVWriterWrapper implements CSVWriter {

    private final CsvWriter csvWriter;

    public CSVWriterWrapper(OutputStream outputStream) {
        var bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        csvWriter = new CsvWriter(bufferedWriter, new CsvWriterSettings());
    }

    public CSVWriterWrapper(OutputStream outputStream, Charset charset) {
        var bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, charset));
        csvWriter = new CsvWriter(bufferedWriter, new CsvWriterSettings());
    }

    @Override
    public void writeNext(String[] nextLine) {
        csvWriter.writeRow(nextLine);
    }

    @Override
    public void close() {
        csvWriter.close();
    }

    @Override
    public void flush() {
        csvWriter.flush();
    }

}
