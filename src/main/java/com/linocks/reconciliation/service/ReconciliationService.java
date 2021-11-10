package com.linocks.reconciliation.service;

import com.linocks.reconciliation.dto.UnmatchedRecord;
import com.linocks.reconciliation.util.CSVUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReconciliationService {

    @Autowired
    CSVUtil csvUtil;
    int matchingRecord;
    Logger logger = LoggerFactory.getLogger(ReconciliationService.class);

    public List<UnmatchedRecord> getUnmatchedRecords(List<String> fileOneContent, List<String> fileTwoContent) {
        int fileOneTotalRecords = fileOneContent.size();
        int fileTwoTotalRecords = fileTwoContent.size();
        int match = 0;

        List<UnmatchedRecord> unmatchedRecords = new ArrayList<>();
        List<String> tempLinesOne = new ArrayList<>(fileOneContent);
        List<String> tempLinesTwo = new ArrayList<>(fileTwoContent);

        if (fileTwoTotalRecords >= fileOneTotalRecords) {
            for (int i = 0; i < fileOneTotalRecords; i++) {
                if (!tempLinesTwo.remove(fileOneContent.get(i))) {
                    String[] line1Arr = fileOneContent.get(i).split(",");
                    String[] line2Arr = fileTwoContent.get(i).split(",");

                    try {
                        String fileOneDate = line1Arr[1];
                        String fileOneReference = line1Arr[7];
                        String fileOneAmount = line1Arr[2];
                        String fileTwoDate = line2Arr[1];
                        String fileTwoReference = line2Arr[7];
                        String fileTwoAmount = line2Arr[2];

                        unmatchedRecords
                                .add(new UnmatchedRecord(fileOneDate, fileOneReference, fileOneAmount,
                                        fileTwoDate, fileTwoReference, fileTwoAmount));
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        logger.error("transaction record is missing some fields!!!");
                        logger.error("file1: " + fileOneContent.get(i));
                        logger.error("file2:" + fileTwoContent.get(i));
                    }
                } else {
                    match++;
                }
            }

            if (fileTwoTotalRecords > fileOneTotalRecords) {
                for (int i = fileOneTotalRecords; i < fileTwoTotalRecords; i++) {
                    String[] line2Arr = fileTwoContent.get(i).split(",");

                    if (i == 150) {
                        System.out.println("debug");
                    }

                    if (line2Arr.length > 0) {
                        try {
                            String fileTwoDate = line2Arr[1];
                            String fileTwoReference = null;

                            if (line2Arr.length < 8) {
                                fileTwoReference = "";
                            } else {
                                fileTwoReference = line2Arr[7];
                            }

                            String fileTwoAmount = line2Arr[2];
                            unmatchedRecords.add(new UnmatchedRecord("", "", "", fileTwoDate, fileTwoReference, fileTwoAmount));
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            logger.error("transaction record " + (i + 2) + " is missing some fields!!!");
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < fileTwoTotalRecords; i++) {
                if (!tempLinesOne.remove(fileTwoContent.get(i))) {
                    String[] line1Arr = fileOneContent.get(i).split(",");
                    String[] line2Arr = fileTwoContent.get(i).split(",");

                    try {
                        String fileOneDate = line1Arr[1];
                        String fileOneReference = line1Arr[7];
                        String fileOneAmount = line1Arr[2];
                        String fileTwoDate = line2Arr[1];
                        String fileTwoReference = line2Arr[7];
                        String fileTwoAmount = line2Arr[2];

                        unmatchedRecords
                                .add(new UnmatchedRecord(fileOneDate, fileOneReference, fileOneAmount,
                                        fileTwoDate, fileTwoReference, fileTwoAmount));

                    } catch (ArrayIndexOutOfBoundsException ex) {
                        logger.error("transaction record " + (i + 2) + " is missing some fields!!!");
                    }
                } else {
                    match++;
                }

            }

            for (int i = fileTwoTotalRecords; i < fileOneTotalRecords; i++) {

                String[] line1Arr = fileOneContent.get(i).split(",");

                try {
                    if (line1Arr.length > 0) {
                        String fileOneDate = line1Arr[1];
                        String fileOneReference = line1Arr[7];
                        String fileOneAmount = line1Arr[2];

                        unmatchedRecords.add(new UnmatchedRecord(fileOneDate, fileOneReference, fileOneAmount, "", "", ""));
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    logger.error("transaction record " + (i + 2) + " is missing some fields!!!");
                }
            }
        }

        matchingRecord = match;
        return unmatchedRecords;
    }

    public Map<String, Object> getComparisonData(MultipartFile file1, MultipartFile file2) {
        Map<String, Object> comparisonData = new HashMap<>();

        matchingRecord = 0;

        List<String> fileOneLineRecords = csvUtil.readCSV(file1);
        List<String> tempFileOneLineRecords = new ArrayList<>(fileOneLineRecords);
        int fileOneTotalRecords = fileOneLineRecords.size();

        List<String> fileTwoLineRecords = csvUtil.readCSV(file2);
        int fileTwoTotalRecords = fileTwoLineRecords.size();
        List<String> tempFileTwoLineRecords = new ArrayList<>(fileTwoLineRecords);

        List<UnmatchedRecord> unmatchedRecords = getUnmatchedRecords(tempFileOneLineRecords, tempFileTwoLineRecords);

        int fileOneTotalUnmatched = fileOneTotalRecords - matchingRecord;
        int fileTwoTotalUnmatched = fileTwoTotalRecords - matchingRecord;

        comparisonData.put("file1", file1.getOriginalFilename());
        comparisonData.put("file2", file2.getOriginalFilename());
        comparisonData.put("file1TotalRecords", String.valueOf(fileOneTotalRecords));
        comparisonData.put("file2TotalRecords", String.valueOf(fileTwoTotalRecords));
        comparisonData.put("matchingRecords", String.valueOf(matchingRecord));
        comparisonData.put("file1Unmatched", String.valueOf(fileOneTotalUnmatched));
        comparisonData.put("file2Unmatched", String.valueOf(fileTwoTotalUnmatched));
        comparisonData.put("unMatchedReport", unmatchedRecords);
        comparisonData.put("showCompare", true);

        return comparisonData;

    }

}
