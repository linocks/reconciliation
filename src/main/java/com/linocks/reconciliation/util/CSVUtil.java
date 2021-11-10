package com.linocks.reconciliation.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVUtil {

    public List<String> readCSV(MultipartFile file) {
        List<String> fileLines = new ArrayList<>();
        String line = null;

        try (BufferedReader csvFile = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            csvFile.readLine(); //skip header

            while ((line = csvFile.readLine()) != null) {
                if (!line.startsWith(",,,,,,,")) {
                    fileLines.add(line.trim());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

}
