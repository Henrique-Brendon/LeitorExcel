package com.henrique.backend.dtos;

import com.henrique.backend.entities.emp.SectorType;

public record ProductDTO(
    Long id,
    String name,
    String characteristics,
    String cost,
    String price,
    String dateEntry,
    String dateExit,
    SectorType sector,
    String listCode) {
    
}
