package com.example.simplesalesdatadashboard.repository;

import com.example.simplesalesdatadashboard.dto.BestSellingProductDTO;
import com.example.simplesalesdatadashboard.dto.MonthlySalesDTO;
import com.example.simplesalesdatadashboard.dto.TotalSalesDTO;

import java.util.List;

public interface SalesDataRepositoryCustom {

    TotalSalesDTO readTotalSalesFromJson(String jsonFilePath);

    BestSellingProductDTO readBestSellingProductFromJson(String jsonFilePath);

    List<MonthlySalesDTO> readMonthlySalesFromJson(String jsonFilePath);

    /* 
    TotalSalesDTO findTotalSales();
    BestSellingProductDTO findBestSellingProduct();
    List<MonthlySalesDTO> findMonthlySales();
    */
}
