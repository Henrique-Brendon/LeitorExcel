package com.henrique.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henrique.backend.entities.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    
}
