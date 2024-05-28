package com.example.simplesalesdatadashboard.service;

import com.example.simplesalesdatadashboard.dto.BestSellingProductDTO;
import com.example.simplesalesdatadashboard.dto.MonthlySalesDTO;
import com.example.simplesalesdatadashboard.dto.TotalSalesDTO;

import java.util.List;

public interface SalesDataService {
    TotalSalesDTO getTotalSalesFromJson();

    BestSellingProductDTO getBestSellingProductFromJson();
    
    List<MonthlySalesDTO> getMonthlySalesFromJson();

    /* TODO: Delete
    TotalSalesDTO getTotalSales();
    BestSellingProductDTO getBestSellingProduct();
    List<MonthlySalesDTO> getMonthlySales();
    */
}
