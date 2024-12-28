package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchReport(String iniDate, String finDate, String name, Pageable pageable) {
		LocalDate finDate1 = null;
		LocalDate iniDate1 = null;

		if (finDate.isEmpty()) {
			finDate1 = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finDate1 = LocalDate.parse(finDate, formatter);
		}

		if (iniDate.isEmpty()) {
			iniDate1 = finDate1.minusYears(1);
		} else {
			iniDate1 = LocalDate.parse(iniDate, formatter);
		}
		
		Page<SaleMinDTO> dto = repository.searchReport(iniDate1, finDate1, name, pageable);

		return dto;
	}

	public Page<SaleSummaryDTO> searchSummary(String iniDate, String finDate, Pageable pageable) {
		LocalDate finDate1 = null;
		LocalDate iniDate1 = null;
		
		if (iniDate.isEmpty()) {
			finDate1 = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finDate1 = LocalDate.parse(finDate, formatter);
		}

		if (iniDate.isEmpty()) {
			iniDate1 = finDate1.minusYears(1);
		} else {
			iniDate1 = LocalDate.parse(iniDate, formatter);
		}
		
		Page<SaleSummaryDTO> dto = repository.searchSummary(iniDate1, finDate1, pageable);
		return dto;
	}
	
	
}
