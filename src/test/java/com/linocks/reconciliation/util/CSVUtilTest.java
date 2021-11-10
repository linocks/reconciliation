package com.linocks.reconciliation.util;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CSVUtilTest {
    CSVUtil csvUtil;
    MultipartFile file1;
    String file1Content;

    @BeforeEach
    void setUp() {
        csvUtil = new CSVUtil();
        file1Content = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription," +
                "TransactionID,TransactionType,WalletReference\n" +
                "Card Campaign,2014-01-11 22:27:44,-20000,*MOLEPS ATM25             MOLEPOLOLE    BW," +
                "DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5, \n" +
                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW," +
                "DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n";
        file1 = new MockMultipartFile("file1.csv", file1Content.getBytes(StandardCharsets.UTF_8));

    }


    @Test
    void readCSV() {
        List<String> fileLines = csvUtil.readCSV(file1);
        assertThat(fileLines)
                .hasSize(2)
                .allMatch(s -> s.startsWith("Card Campaign"));
        try {
            System.out.println(file1.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}