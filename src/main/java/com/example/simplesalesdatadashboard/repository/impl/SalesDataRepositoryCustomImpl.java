package com.example.simplesalesdatadashboard.repository.impl;

import com.example.simplesalesdatadashboard.dto.BestSellingProductDTO;
import com.example.simplesalesdatadashboard.dto.MonthlySalesDTO;
import com.example.simplesalesdatadashboard.dto.TotalSalesDTO;
import com.example.simplesalesdatadashboard.entity.SalesDataEntity;
import com.example.simplesalesdatadashboard.repository.SalesDataRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.apache.commons.csv.*;

import java.io.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import com.fasterxml.jackson.core.type.TypeReference;


import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SalesDataRepositoryCustomImpl implements SalesDataRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public TotalSalesDTO readTotalSalesFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonData = objectMapper.readValue(Paths.get(jsonFilePath).toFile(), new TypeReference<Map<String, Object>>() {});
            double totalSales = (Double) jsonData.get("total_sales_amount");
            return new TotalSalesDTO(totalSales);
        } catch (IOException e) {
            e.printStackTrace();
            return new TotalSalesDTO(0);
        }
    }

    public BestSellingProductDTO readBestSellingProductFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonData = objectMapper.readValue(Paths.get(jsonFilePath).toFile(), new TypeReference<Map<String, Object>>() {});
            String productName = (String) jsonData.get("best_selling_product");
            double quantitySold = ((Number) jsonData.get("best_selling_quantity")).doubleValue();
            return new BestSellingProductDTO(productName, (int) quantitySold);
        } catch (IOException e) {
            //logger.error("Error reading best selling product from JSON", e);
            return new BestSellingProductDTO("Unknown", 0);
        }
    }

    public List<MonthlySalesDTO> readMonthlySalesFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> jsonData = objectMapper.readValue(Paths.get(jsonFilePath).toFile(), new TypeReference<Map<String, Object>>() {});
            Object monthlySalesObject = jsonData.get("monthly_sales");
            if (monthlySalesObject instanceof List<?>) {
                List<?> rawList = (List<?>) monthlySalesObject;
                List<Map<String, Object>> monthlySalesList = rawList.stream()
                    .filter(item -> item instanceof Map<?, ?>)
                    .map(item -> (Map<String, Object>) item)
                    .collect(Collectors.toList());
                return monthlySalesList.stream()
                    .map(sale -> new MonthlySalesDTO((String) sale.get("Month"), ((Number) sale.get("TotalPrice")).doubleValue()))
                    .collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
    

/* 
    public List<SalesDataEntity> loadDataFromCSV(String filePath) {
        List<SalesDataEntity> salesDataList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
            ) {
            for (CSVRecord csvRecord : csvParser) {
                SalesDataEntity salesData = new SalesDataEntity();
                salesData.setActualPrice(Double.parseDouble(csvRecord.get("ActualPrice")));
                salesData.setQuantity(Integer.parseInt(csvRecord.get("Quantity")));                // Set other fields as needed
                salesDataList.add(salesData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salesDataList;
    }

    @Override
    public TotalSalesDTO findTotalSales() {
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT SUM(s.actualPrice * s.quantity) FROM SalesDataEntity s", Double.class);
        Double totalSalesAmount = query.getSingleResult();
        return new TotalSalesDTO(totalSalesAmount);
    }

    @Override
    public BestSellingProductDTO findBestSellingProduct() {
        List<SalesDataEntity> salesDataList = entityManager.createQuery(
                "SELECT s FROM SalesDataEntity s", SalesDataEntity.class).getResultList();
        return salesDataList.stream()
                .collect(Collectors.groupingBy(SalesDataEntity::getItemDescription, Collectors.summingDouble(SalesDataEntity::getQuantity)))
                .entrySet().stream()
                .max((entry1, entry2) -> Double.compare(entry1.getValue(), entry2.getValue()))
                .map(entry -> new BestSellingProductDTO(entry.getKey(), entry.getValue()))
                .orElse(null);
    }

    @Override
    public List<MonthlySalesDTO> findMonthlySales() {
        List<SalesDataEntity> salesDataList = entityManager.createQuery(
                "SELECT s FROM SalesDataEntity s", SalesDataEntity.class).getResultList();
        return salesDataList.stream()
                .collect(Collectors.groupingBy(
                        salesData -> salesData.getSaleDate().toString().substring(0, 7),
                        Collectors.summingDouble(salesData -> salesData.getActualPrice() * salesData.getQuantity())
                ))
                .entrySet().stream()
                .map(entry -> new MonthlySalesDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
    */

