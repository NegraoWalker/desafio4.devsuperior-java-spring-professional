package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SaleSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDto;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDto findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDto(entity);
	}

	public Page<SaleReportDto> findSalesReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
		LocalDate now = LocalDate.now();
		LocalDate startDate;
		LocalDate endDate;

		if (minDate == null || minDate.isEmpty()) {
			startDate = now.minusMonths(12);
		} else {
			startDate = LocalDate.parse(minDate);
		}

		if (maxDate == null || maxDate.isEmpty()) {
			endDate = now;
		} else {
			endDate = LocalDate.parse(maxDate);
		}

		if (sellerName == null) {
			sellerName = "";
		}

		return repository.findSalesReport(startDate, endDate, sellerName, pageable);
	}

	public List<SaleSummaryDto> findSalesSummary(String minDate, String maxDate) {
		LocalDate now = LocalDate.now();
		LocalDate startDate;
		LocalDate endDate;

		if (minDate == null || minDate.isEmpty()) {
			startDate = now.minusMonths(12);
		} else {
			startDate = LocalDate.parse(minDate);
		}

		if (maxDate == null || maxDate.isEmpty()) {
			endDate = now;
		} else {
			endDate = LocalDate.parse(maxDate);
		}
		return repository.findSalesSummary(startDate, endDate);
	}
}