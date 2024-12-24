package com.henrique.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henrique.backend.entities.emp.SectorType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Sector implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private SectorType name;
	
    @JsonIgnore
	@OneToMany(mappedBy = "sector")
	private List<Product> products = new ArrayList<>();
	
	public Sector() {

	}
	
	private Sector(Long id, SectorType name) {
		this.id = id;
		this.name = name;
	}

	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public SectorType getName() {
		return name;
	}
	
	public void setName(SectorType name) {
		this.name = name;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public Sector mapSetor(String aux) {
		// Criação do mapa com os IDs associados a cada tipo de setor
		Map<SectorType, Long> sectorTypeToIdMap = Map.of(
			SectorType.DEFAULT, 0L,
			SectorType.HARDWARE, 1L,
			SectorType.PERIPHELRALS, 2L,
			SectorType.ELETRONICS, 3L,
			SectorType.SMARTHPHONES, 4L
		);
	
		// Criação do mapa com os produtos e seus respectivos setores
		Map<String, Sector> productToSectorMap = new HashMap<>();
		productToSectorMap.put("RX 550", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RX 560", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RX 570", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RX 580", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RX 590", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RYZEN 3 3200G", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RYZEN 5 3500", new Sector(null, SectorType.HARDWARE));
		productToSectorMap.put("RYZEN 5 3600", new Sector(null, SectorType.HARDWARE));
	
		// Setor padrão (DEFAULT) caso o produto não seja encontrado no mapa
		Sector sector = new Sector(null, SectorType.DEFAULT);
	
		// Verifica se o produto está no mapa
		if (productToSectorMap.containsKey(aux)) {
			sector = productToSectorMap.get(aux);
		}
	
		// Configura o ID de acordo com o tipo do setor
		Long id = sectorTypeToIdMap.get(sector.getName());
		sector.setId(id);
	
		return sector;
	}
	

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sector other = (Sector) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}