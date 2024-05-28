package com.example.simplesalesdatadashboard.controller;

import com.example.simplesalesdatadashboard.dto.BestSellingProductDTO;
import com.example.simplesalesdatadashboard.dto.MonthlySalesDTO;
import com.example.simplesalesdatadashboard.dto.TotalSalesDTO;
import com.example.simplesalesdatadashboard.service.SalesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesDataController {

    private final SalesDataService salesDataService;

    @Autowired
    public SalesDataController(SalesDataService salesDataService) {
        this.salesDataService = salesDataService;
    }

    @GetMapping("/")
    public ResponseEntity<String> getApiRoot() {
        return ResponseEntity.ok("API Root. Use specific endpoints for data.");
    }

    @GetMapping("/total")
    @Operation(summary = "Get total sales data", description = "Retrieve the total sales data from JSON source")
    public ResponseEntity<TotalSalesDTO> getTotalSales() {
        TotalSalesDTO totalSales = salesDataService.getTotalSalesFromJson();
        return ResponseEntity.ok(totalSales);
    }

    @GetMapping("/product/best-selling")
    @Operation(summary = "Get best-selling product", description = "Retrieve the best-selling product data from JSON source")
    public ResponseEntity<BestSellingProductDTO> getBestSellingProduct() {
        BestSellingProductDTO bestSellingProduct = salesDataService.getBestSellingProductFromJson();
        return ResponseEntity.ok(bestSellingProduct);
    }

    @GetMapping("/monthly")
    @Operation(summary = "Get monthly sales data", description = "Retrieve sales data aggregated by month from JSON source")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales() {
        List<MonthlySalesDTO> monthlySales = salesDataService.getMonthlySalesFromJson();
        return ResponseEntity.ok(monthlySales);
    }
/* 
    @GetMapping("/monthly/")
    public ResponseEntity<TotalSalesDTO> getMonthlySales() {
        TotalSalesDTO totalSales = salesDataService.getMonthlySales();
        return ResponseEntity.ok(totalSales);
    }
/*
    @GetMapping("/total")
    public ResponseEntity<TotalSalesDTO> getTotalSales() {
        return ResponseEntity.ok(salesDataService.getTotalSales());
    }

    @GetMapping("/best-selling-product")
    public ResponseEntity<BestSellingProductDTO> getBestSellingProduct() {
        return ResponseEntity.ok(salesDataService.getBestSellingProduct());
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlySalesDTO>> getMonthlySales() {
        return ResponseEntity.ok(salesDataService.getMonthlySales());
    }
    */
 
}
