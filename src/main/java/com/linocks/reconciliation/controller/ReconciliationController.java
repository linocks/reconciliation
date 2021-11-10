package com.linocks.reconciliation.controller;

import com.linocks.reconciliation.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReconciliationController {

    @Autowired
    ReconciliationService reconciliationService;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("showCompare", false);
        return "index";
    }

    @PostMapping("/compare")
    public String getComparison(@RequestParam("csvFile1") MultipartFile file1, @RequestParam("csvFile2") MultipartFile file2,
                                Model model, RedirectAttributes redirectAttributes) {

        if (file2 == null || file1 == null) {
            redirectAttributes.addFlashAttribute("error", "Please Select a file!!");
        } else {

            if ((file1.getOriginalFilename().endsWith(".csv") && file2.getOriginalFilename().endsWith(".csv"))) {

                model.addAllAttributes(reconciliationService.getComparisonData(file1, file2));

                return "index";
            }

            redirectAttributes.addFlashAttribute("error", "Only CSV Files are allowed!!");
        }

        return "redirect:/index";

    }
}
