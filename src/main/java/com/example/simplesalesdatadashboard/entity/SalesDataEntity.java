package com.example.simplesalesdatadashboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SalesDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long objectId;
    private Long courseId;
    private String courseName;
    private Long saleId;
    private LocalDateTime saleDate;
    private String reportDate;
    private Long itemId;
    private String itemDescription;
    private double actualPrice;
    private int quantity;
    private String salesTax;
    private String lmpField;
}