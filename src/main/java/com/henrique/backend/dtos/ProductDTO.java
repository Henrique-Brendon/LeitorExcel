package com.henrique.backend.dtos;

public record ProductDTO(
    String name,
    String characteristics,
    String cost,
    String price,
    String dateEntry,
    String dateExit,
    String sector,
    String listCode) {
    
}
