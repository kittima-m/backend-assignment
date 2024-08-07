package com.ata.salary.service;

import com.ata.salary.constant.ValidSalarySearchCriteria;
import com.ata.salary.model.PaginatedResponseDTO;
import com.ata.salary.entity.Salary;
import com.ata.salary.model.SalaryDTO;
import com.ata.salary.repository.SalaryRepository;
import jakarta.persistence.criteria.*;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    @Autowired
    private final SalaryRepository salaryRepository;

    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public PaginatedResponseDTO<SalaryDTO> findSalaries(List<String> fields, Double minSalary, Double maxSalary, String gender, String jobTitle, Pageable pageable) {
        List<String> processedFields = processDisplayFields(fields);
        Sort sort = getModifiedSort(pageable.getSort());

        Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Salary> salaryPage = salaryRepository.findAll((root, query, cb) -> {

            List<Predicate> predicates = getPredicateByCriteria(cb, root, processedFields, minSalary, maxSalary, gender, jobTitle);

            query.where(predicates.toArray((new Predicate[0])));
            query.orderBy(getSortByFields(cb, root, modifiedPageable.getSort()));

            return query.getRestriction();
        }, modifiedPageable);

        List<SalaryDTO> resultList = mapEntityToSalaryDto(salaryPage.getContent(), processedFields);
        PaginatedResponseDTO response = PaginatedResponseDTO.builder()
                .content(resultList)
                .totalElements(salaryPage.getTotalElements())
                .pageNumber(modifiedPageable.getPageNumber() + 1)
                .pageSize(modifiedPageable.getPageSize())
                .totalPages(salaryPage.getTotalPages())
                .isLastPage(salaryPage.isLast())
                .build();
        return response;

    }

    private List<Predicate> getPredicateByCriteria(CriteriaBuilder cb, Root<Salary> salaryRoot, List<String> fields, Double minSalary, Double maxSalary, String gender, String jobTitle) {
        List<Predicate> predicates = new ArrayList<>();

        fields.forEach(field -> {
            Path<Object> fieldPath = salaryRoot.get(field);
            predicates.add(cb.isNotNull(fieldPath));
        });

        if (StringUtil.isNotEmpty(jobTitle)) {
            predicates.add(cb.like(cb.upper(salaryRoot.get("jobTitle")), "%" + jobTitle.toUpperCase() + "%"));
        }

        if (StringUtil.isNotEmpty(gender)) {
            predicates.add(cb.like(cb.upper(salaryRoot.get("gender")), "%" + gender.toUpperCase() + "%"));
        }

        if (minSalary != null) {
            predicates.add(cb.greaterThanOrEqualTo(salaryRoot.get("salary"), minSalary));
        }

        if (maxSalary != null) {
            predicates.add(cb.lessThanOrEqualTo(salaryRoot.get("salary"), maxSalary));
        }

        return predicates;
    }

    private List<Order> getSortByFields(CriteriaBuilder cb, Root<Salary> rootSalary, Sort sortFields) {
        List<Order> orderBy = new ArrayList<>();

        for (Sort.Order sortOrder : sortFields) {
            String property = sortOrder.getProperty();
            Sort.Direction direction = sortOrder.getDirection();

            if (direction.equals(Sort.Direction.DESC)) {
                orderBy.add(cb.desc(rootSalary.get(property)));
            } else {
                orderBy.add(cb.asc(rootSalary.get(property)));
            }
        }

        return orderBy;
    }


    private List<SalaryDTO> mapEntityToSalaryDto(List<Salary> salaries, List<String> fields) {
        return salaries.stream().map((salary -> {
            SalaryDTO dto = new SalaryDTO();

            if (fields.contains("employerName")) {
                dto.setEmployerName(salary.getEmployerName());
            }
            if (fields.contains("location")) {
                dto.setEmployerName(salary.getLocation());
            }
            if (fields.contains("salary")) {
                dto.setSalary(salary.getSalary());
            }
            if (fields.contains("jobTitle")) {
                dto.setJobTitle(salary.getJobTitle());
            }
            if (fields.contains("gender")) {
                dto.setGender(salary.getGender());
            }
            if (fields.contains("yearsAtEmployer")) {
                dto.setYearsAtEmployer(salary.getYearsAtEmployer());
            }
            if (fields.contains("yearsOfExperience")) {
                dto.setYearsOfExperience(salary.getYearsOfExperience());
            }
            if (fields.contains("signingBonus")) {
                dto.setSigningBonus(salary.getSigningBonus());
            }
            if (fields.contains("annualBonus")) {
                dto.setAnnualBonus(salary.getAnnualBonus());
            }
            if (fields.contains("annualStockValue")) {
                dto.setAnnualStockValue(salary.getAnnualStockValue());
            }
            if (fields.contains("additionalComments")) {
                dto.setAdditionalComments(salary.getAdditionalComments());
            }

            return dto;
        })).collect(Collectors.toList());
    }

    private List<String> processDisplayFields(List<String> fields) {
        List<String> processedFields = new ArrayList<>();
        fields.forEach((f) -> {
            Arrays.stream(f.split(",")).forEach((splitField) -> {
                String validField = ValidSalarySearchCriteria.getValidFieldName(splitField);
                if (StringUtil.isNotEmpty(validField)) {
                    processedFields.add(validField);
                }
            });
        });
        return processedFields;
    }

    public Sort getModifiedSort(Sort sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort != null && sort.iterator().hasNext()) {
            for (Sort.Order order : sort) {
                String sortProperty = order.getProperty();
                String validFieldName = ValidSalarySearchCriteria.getValidFieldName(sortProperty);

                if (validFieldName != null) {
                    Sort.Order newOrder = new Sort.Order(order.getDirection(), validFieldName);
                    orders.add(newOrder);
                }
            }
        }
        return Sort.by(orders);
    }
}