package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) FROM Sale obj "
			+ "WHERE obj.date BETWEEN :iniDate AND :finDate "
			+ "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
	Page<SaleMinDTO> searchReport( LocalDate iniDate, LocalDate finDate, String sellerName, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj "
			+ "WHERE obj.date BETWEEN :iniDate AND :finDate "
			+ "GROUP BY obj.seller.name")
	Page<SaleSummaryDTO> searchSummary(LocalDate iniDate, LocalDate finDate, Pageable pageable);

}
