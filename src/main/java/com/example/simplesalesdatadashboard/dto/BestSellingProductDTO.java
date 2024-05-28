// src/main/java/com/example/salesdashboard/model/TotalSales.java
package com.example.simplesalesdatadashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellingProductDTO {
    private String productName;
    private double quantitySold;
}


