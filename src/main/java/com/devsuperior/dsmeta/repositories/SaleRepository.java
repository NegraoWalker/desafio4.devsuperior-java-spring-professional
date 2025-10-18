package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SaleSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDto(sale.id, sale.date, sale.amount, seller.name) " +
            "FROM Sale sale " +
            "INNER JOIN sale.seller seller " +
            "WHERE sale.date BETWEEN :startDate AND :endDate " +
            "AND LOWER(seller.name) LIKE LOWER(CONCAT('%', :sellerName, '%'))")
    Page<SaleReportDto> findSalesReport(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("sellerName") String sellerName, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDto(seller.name, SUM(sale.amount)) " +
            "FROM Sale sale " +
            "INNER JOIN sale.seller seller " +
            "WHERE sale.date BETWEEN :startDate AND :endDate " +
            "GROUP BY seller.name")
    List<SaleSummaryDto> findSalesSummary(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
