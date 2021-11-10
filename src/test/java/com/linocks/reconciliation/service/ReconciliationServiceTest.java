package com.linocks.reconciliation.service;

import com.linocks.reconciliation.dto.UnmatchedRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReconciliationServiceTest {
    @Autowired
    ReconciliationService reconciliationService;
    MultipartFile file1, file2;
    String file1Content, file2Content;
    List<String> fileOneLines, fileTwoLines;

    @BeforeEach
    void setUp() {
        file1Content = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription," +
                "TransactionID,TransactionType,WalletReference\n" +

                "Card Campaign,2014-01-11 22:27:44,-20000,*MOLEPS ATM25             MOLEPOLOLE    BW," +
                "DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5, \n" +

                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW," +
                "DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n";

        file2Content = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription," +
                "TransactionID,TransactionType,WalletReference\n" +

                "Card Campaign,2014-01-11 22:27:44,-20050,*MOLEPS ATM25             MOLEPOLOLE    BW," +
                "DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5, \n" +

                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW," +
                "DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n";

        fileOneLines = Arrays.stream(file1Content.split("\n")).skip(1).collect(Collectors.toList()); //skip heading
        fileTwoLines = Arrays.stream(file2Content.split("\n")).skip(1).collect(Collectors.toList()); //skip heading

        file1 = new MockMultipartFile("file1.csv", file1Content.getBytes(StandardCharsets.UTF_8));
        file2 = new MockMultipartFile("file2.csv", file2Content.getBytes(StandardCharsets.UTF_8));
    }


    @Test
    void getUnmatchedRecords() {
        List<UnmatchedRecord> unmatchedRecords = reconciliationService.getUnmatchedRecords(fileOneLines, fileTwoLines);
        assertThat(unmatchedRecords).hasSize(1);
    }

    @Test
    void getComparisonData() {
        Map<String, Object> comparisonData = reconciliationService.getComparisonData(file1, file2);
        assertThat(comparisonData).hasSize(9);
    }
}