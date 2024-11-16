package com.henrique.backend.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String characteristics;
	private String imgUrl;
	private BigDecimal cost;
	private BigDecimal price;
	private Instant dateEntry;
	private Instant dateExit;
	
	private Sector sector;

	private ListCode listCode;
	
	public Product() {}

	public Product(Long id, String name, String characteristics, String imgUrl, BigDecimal cost, BigDecimal price,
			Instant dateEntry, Instant dateExit) {
		this.id = id;
		this.name = name;
		this.characteristics = characteristics;
		this.imgUrl = imgUrl;
		this.cost = cost;
		this.price = price;
		this.dateEntry = dateEntry;
		this.dateExit = dateExit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Instant getDateEntry() {
		return dateEntry;
	}

	public void setDateEntry(Instant dateEntry) {
		this.dateEntry = dateEntry;
	}

	public Instant getDateExit() {
		return dateExit;
	}

	public void setDateExit(Instant dateExit) {
		this.dateExit = dateExit;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public ListCode getListCode() {
		return listCode;
	}

	public void setListCode(ListCode listCode) {
		this.listCode = listCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
	
}