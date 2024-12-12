package com.henrique.backend.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.henrique.backend.dtos.ProductDTO;
import com.henrique.backend.entities.ListCode;
import com.henrique.backend.entities.Product;
import com.henrique.backend.entities.Sector;
import com.henrique.backend.entities.emp.SectorType;

public class ProductMapper  {
    
    public static List<ProductDTO> toDTOList(List<Product> products) {
    List<ProductDTO> productDTOs = new ArrayList<>();

    products.forEach(product -> {
        SectorType sectorType = (product.getSector() != null) ? product.getSector().getName() : SectorType.DEFAULT;
        String listCode = (product.getListCode() != null) ? product.getListCode().getCode() : "Unknown";

        productDTOs.add(new ProductDTO(
            product.getId(),
            product.getName(),
            product.getCharacteristics(),
            formatCurrency(product.getCost()),
            formatCurrency(product.getPrice()),
            formatDate(product.getDateEntry()),
            formatDate(product.getDateExit()),
            sectorType,
            listCode
        ));
    });

        return productDTOs;
    }

    public static Product toEntity(ProductDTO productDTO) {
        return new Product(
            null,
            productDTO.name(),
            productDTO.characteristics(),
            new BigDecimal(productDTO.cost().trim()),
            new BigDecimal(productDTO.price().trim()),
            formatInstant(productDTO.dateEntry()),
            formatInstant(productDTO.dateExit()),
            new Sector().mapSetor(productDTO.name()),
            new ListCode(productDTO.listCode())
        );
    }

    public static void updateEntity(Product entity, ProductDTO productDTO) {
        entity.setName(productDTO.name());
        entity.setCharacteristics(productDTO.characteristics());
        entity.setCost(new BigDecimal(productDTO.cost().trim()));
        entity.setPrice(new BigDecimal(productDTO.price().trim()));
        entity.setDateEntry(formatInstant(productDTO.dateEntry()));
        entity.setDateExit(formatInstant(productDTO.dateExit()));
        entity.setSector(new Sector().mapSetor(productDTO.name()));
        entity.setListCode(new ListCode(productDTO.listCode()));
    }

    private static String formatCurrency(BigDecimal value) {
        return String.format("%.2f R$", value);
    }

    private static String formatDate(Instant date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
        return formatter.format(date);
    }

    private static Instant formatInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
