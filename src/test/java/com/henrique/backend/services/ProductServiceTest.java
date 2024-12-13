package com.henrique.backend.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.henrique.backend.dtos.ProductDTO;
import com.henrique.backend.entities.Product;
import com.henrique.backend.entities.emp.SectorType;
import com.henrique.backend.repositories.ProductRepository;
/**
 * Unit test class for service layer {@link ProductService}.
 * <p>
 * This class uses JUnit5 and Mockito to validate behaviors
 * service methods including search, insertion
 * updating and removing products. Also covers failure scenarios
 * and custom exception handling.
 * </p>
 *
 * @author Henrique
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
    /**
     * List of simulated products used in test.
     */
	private List<Product> listProducts; 

    /**
     * List of simulated products DTO objects used as input in tests.
     */
	private List<ProductDTO> listProductsDTOs;
    
    /**
     * Service instance {@link ProductService} with mocks injected.
     */
	@InjectMocks
    private  ProductService service;

    /**
     * Product repository mockup used to simulate persistence operations.
     */
    @Mock
    private ProductRepository repository;
    
    /**
     * Initialization method performed before each tests.
     * <p>
     * Configures mocks and initializes product lists and simulated DTOs.
     * </p>
     */
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);
    	startList();
    }


    /**
     * Initializes lists of products and DTOs for use in testing.
     * <p>
     * This method is automatically called in the method {@link #setUp()}.
     * </p>
     */
    void startList() {
		listProducts = Arrays.asList(
			    new Product(1L, "RX 560", "", new BigDecimal("200.00"), new BigDecimal("300.00"), Instant.now(), Instant.now(), null, null),
			    new Product(2L, "RX 570", "", new BigDecimal("300.00"), new BigDecimal("40.00"), Instant.now(), Instant.now(), null, null),
			    new Product(3L, "RX 580", "", new BigDecimal("350.00"), new BigDecimal("450.00"), Instant.now(), Instant.now(), null, null),
			    new Product(4L, "RX 590", "", new BigDecimal("400.00"), new BigDecimal("500.00"), Instant.now(), Instant.now(), null, null)	    
		);
		
		listProductsDTOs = Arrays.asList(
			    new ProductDTO(1L, "RX 560", "", "200.00", "300.00", "02/05/2019", "22/05/2019", SectorType.HARDWARE, "teste"),
			    new ProductDTO(2L, "RX 570", "", "300.00", "40.00", "15/02/2019", "28/03/2029", SectorType.HARDWARE, "teste"),
			    new ProductDTO(3L, "RX 580", "", "350.00", "450.00", "12/08/2020", "20/08/2020", SectorType.HARDWARE, "teste"),
			    new ProductDTO(4L, "RX 590", "", "400.00", "500.00", "15/07/2020", "13/08/2020", SectorType.HARDWARE, "teste")	    
		);
    }
}
