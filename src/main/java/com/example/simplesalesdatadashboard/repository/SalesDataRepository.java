package com.example.simplesalesdatadashboard.repository;

import com.example.simplesalesdatadashboard.entity.SalesDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesDataRepository extends JpaRepository<SalesDataEntity, Long>, SalesDataRepositoryCustom {
    
}
