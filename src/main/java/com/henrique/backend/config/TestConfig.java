/* 
package com.henrique.backend.config;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.henrique.backend.entities.Product;
import com.henrique.backend.entities.Sector;
import com.henrique.backend.repositories.ProductRepository;
import com.henrique.backend.repositories.SectorRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private SectorRepository sectorRepository;


@Override
public void run(String... args) throws Exception {


	List<Product> listProducts = Arrays.asList(
		new Product(null, "RX 560", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
		new Product(null, "RX 570", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
		new Product(null, "RX 580", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
		new Product(null, "RX 590", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null),
		new Product(null, "RTX 2070 TI", "", new BigDecimal("50.00"), new BigDecimal("50.00"), Instant.now(), Instant.now(), null, null)
	);
	
	List<Sector> listSectors = new ArrayList<>();
	
	for (Product product : listProducts) {
		// Obtém o setor correspondente ao produto
		Sector sector = new Sector().mapSetor(product.getName());
		
		// Associa o setor ao produto
		product.setSector(sector);
	
		// Adiciona o produto na lista de produtos do setor
		sector.getProducts().add(product);
	
		// Garante que o setor está na lista para ser salvo posteriormente
		if (!listSectors.contains(sector)) {
			listSectors.add(sector);
		}
	}
	
	// Salva os setores e os produtos associados no banco
	sectorRepository.saveAll(listSectors);
	productRepository.saveAll(listProducts);
}

}

*/