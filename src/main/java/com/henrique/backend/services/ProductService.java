package com.henrique.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.henrique.backend.dtos.ProductDTO;
import com.henrique.backend.entities.Product;
import com.henrique.backend.repositories.ProductRepository;
import com.henrique.backend.services.execeptions.ServiceException;
import com.henrique.backend.util.ProductMapper;

@Service
public class ProductService extends BaseService{
    
    @Autowired
    private ProductRepository repository;

    public Product findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ServiceException("Product not found with ID: " + id));
	}

    public void deleteById(Long id) {
        execute(() -> repository.deleteById(id), "Error deleting product with id: " + id);
    }

    public List<ProductDTO> findAll() {
        return ProductMapper.toDTOList(repository.findAll());
    }

    public List<ProductDTO> getAllProductsSort(String campo, String direcao) {
		Sort.Direction direction = "DESC".equalsIgnoreCase(direcao) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Sort sort = Sort.by(direction, campo);
		return ProductMapper.toDTOList(repository.findAll(sort));
	}

    public  Product insert(ProductDTO productDTO) {
        return execute(() -> repository.save(ProductMapper.toEntity(productDTO)), "Error inserting product");
    }

    public List<Product> insertProducts(List<ProductDTO> productDTOs) {
        return execute(() -> {
            List<Product> products = productDTOs.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
            return repository.saveAll(products);
        }, "Error saving products");
    }

    public Product update(Long id, ProductDTO productDTO) {
    	return execute(() -> {
    		Product entity = repository.findById(id).orElseThrow(() ->
    			new ServiceException("Product not found with ID: " + id)
			);
    		ProductMapper.updateEntity(entity, productDTO);
    		return repository.save(entity);
    	}, "Error updating product with ID:" + id);
    	
    }
    
}
