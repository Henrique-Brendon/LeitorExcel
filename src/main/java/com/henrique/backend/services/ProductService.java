package com.henrique.backend.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henrique.backend.dtos.ProductDTO;
import com.henrique.backend.entities.ListCode;
import com.henrique.backend.entities.Product;
import com.henrique.backend.entities.Sector;
import com.henrique.backend.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;

    public  Product insert(ProductDTO productDTO) {
        return repository.save(convertProductDtoToProduct(productDTO));
    }

    private Product convertProductDtoToProduct(ProductDTO productDTO) {
        return new Product(null, productDTO.name(), productDTO.characteristics(), new BigDecimal(productDTO.cost().trim()),
            new BigDecimal(productDTO.price().trim()), formatInstant(productDTO.dateEntry()), formatInstant(productDTO.dateExit()),
            new Sector().mapSetor(productDTO.name()), new ListCode(productDTO.listCode())
        );
    }

    private Instant formatInstant(String date) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate localDate = LocalDate.parse(date, fmt);
		return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	}
    
}
