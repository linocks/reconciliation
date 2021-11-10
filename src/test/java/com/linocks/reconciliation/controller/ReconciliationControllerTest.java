package com.linocks.reconciliation.controller;

import com.linocks.reconciliation.service.ReconciliationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest(ReconciliationController.class)
class ReconciliationControllerTest {

    @MockBean
    private ReconciliationService reconciliationService;
    @Autowired
    private MockMvc mockMvc;
    private MockMultipartFile file1, file2;

    @BeforeEach
    void setUp() {
        String file1Content = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription," +
                "TransactionID,TransactionType,WalletReference\n" +

                "Card Campaign,2014-01-11 22:27:44,-20000,*MOLEPS ATM25             MOLEPOLOLE    BW," +
                "DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5, \n" +

                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW," +
                "DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n";

        String file2Content = "ProfileName,TransactionDate,TransactionAmount,TransactionNarrative,TransactionDescription," +
                "TransactionID,TransactionType,WalletReference\n" +

                "Card Campaign,2014-01-11 22:27:44,-20050,*MOLEPS ATM25             MOLEPOLOLE    BW," +
                "DEDUCT,0584011808649511,1,P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5, \n" +

                "Card Campaign,2014-01-11 22:39:11,-10000,*MOGODITSHANE2            MOGODITHSANE  BW," +
                "DEDUCT,0584011815513406,1,P_NzI1MjA1NjZfMTM3ODczODI3Mi4wNzY5,\n";

        file1 = new MockMultipartFile("testfile1.csv", file1Content.getBytes(StandardCharsets.UTF_8));
        file2 = new MockMultipartFile("testfile2.csv", file2Content.getBytes(StandardCharsets.UTF_8));


    }

    @Test
    void getHomePage() {
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("index"))
                    .andExpect(MockMvcResultMatchers.model().attribute("showCompare", false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getComparison() {
        try {
            mockMvc
                    .perform(MockMvcRequestBuilders.multipart("/compare")
                            .file("csvFile1", file1.getBytes())
                            .file("csvFile2", file2.getBytes()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("index"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}