package com.ata.salary.controller;

import com.ata.salary.model.PaginatedResponseDTO;
import com.ata.salary.model.SalaryDTO;
import com.ata.salary.service.SalaryService;
import com.ata.salary.utils.ConverterUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SalaryController {

    @Autowired
    private final SalaryService salaryService;

    @Autowired
    private final ConverterUtils converterUtils;

    public SalaryController(SalaryService salaryService, ConverterUtils converterUtils) {
        this.salaryService = salaryService;
        this.converterUtils = converterUtils;
    }

    @GetMapping("/salary")
    public ResponseEntity<PaginatedResponseDTO<SalaryDTO>> getSalary(HttpServletRequest request, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        HttpServletRequest wrappedRequest = (HttpServletRequest) request.getAttribute("wrappedRequest");

        PaginatedResponseDTO<SalaryDTO> salaryPage = salaryService.findSalaries(
                converterUtils.convertStringArrayToStringArrayList(wrappedRequest.getParameterValues("fields")),
                converterUtils.convertStringToDouble(wrappedRequest.getParameter("minSalary")),
                converterUtils.convertStringToDouble(wrappedRequest.getParameter("maxSalary")),
                wrappedRequest.getParameter("gender"),
                wrappedRequest.getParameter("jobTitle"),
                pageable
        );
        return ResponseEntity.ok(salaryPage);
    }

}
