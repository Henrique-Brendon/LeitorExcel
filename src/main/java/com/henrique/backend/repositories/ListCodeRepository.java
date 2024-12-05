package com.henrique.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henrique.backend.entities.ListCode;

public interface ListCodeRepository extends JpaRepository<ListCode, Long> {
    
}
