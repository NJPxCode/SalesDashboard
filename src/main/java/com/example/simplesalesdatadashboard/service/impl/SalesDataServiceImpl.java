package com.example.simplesalesdatadashboard.service.impl;

import com.example.simplesalesdatadashboard.config.AppProperties;
import com.example.simplesalesdatadashboard.dto.BestSellingProductDTO;
import com.example.simplesalesdatadashboard.dto.MonthlySalesDTO;
import com.example.simplesalesdatadashboard.dto.TotalSalesDTO;
import com.example.simplesalesdatadashboard.repository.SalesDataRepository;
import com.example.simplesalesdatadashboard.service.SalesDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesDataServiceImpl implements SalesDataService {

    private static final Logger logger = LoggerFactory.getLogger(SalesDataServiceImpl.class);

    private final SalesDataRepository salesDataRepository;
    private final String jsonFilePath;

    // Constructor injection with configuration properties class
    public SalesDataServiceImpl(SalesDataRepository salesDataRepository, AppProperties properties) {
        this.salesDataRepository = salesDataRepository;
        this.jsonFilePath = properties.getJsonFilePath();
    }

    @Override
    public TotalSalesDTO getTotalSalesFromJson() {
        try {
            return salesDataRepository.readTotalSalesFromJson(jsonFilePath);
        } catch (Exception e) {
            logger.error("Error retrieving total sales from JSON", e);
            throw new RuntimeException("Error retrieving total sales from JSON", e);
        }
    }

    @Override
    public BestSellingProductDTO getBestSellingProductFromJson() {
        try {
            return salesDataRepository.readBestSellingProductFromJson(jsonFilePath);
        } catch (Exception e) {
            logger.error("Error retrieving best selling product from JSON", e);
            throw new RuntimeException("Error retrieving best selling product from JSON", e);
        }
    }

    @Override
    public List<MonthlySalesDTO> getMonthlySalesFromJson() {
        try {
            return salesDataRepository.readMonthlySalesFromJson(jsonFilePath);
        } catch (Exception e) {
            logger.error("Error retrieving monthly sales from JSON", e);
            throw new RuntimeException("Error retrieving monthly sales from JSON", e);
        }
    }
/* 
    @Override
    public TotalSalesDTO getTotalSales() {
        return salesDataRepository.findTotalSales();
    }

    @Override
    public BestSellingProductDTO getBestSellingProduct() {
        return salesDataRepository.findBestSellingProduct();
    }

    @Override
    public List<MonthlySalesDTO> getMonthlySales() {
        return salesDataRepository.findMonthlySales();
    }
    */
}
