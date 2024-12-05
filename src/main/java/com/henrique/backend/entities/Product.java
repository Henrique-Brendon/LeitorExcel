package com.henrique.backend.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String characteristics;
	private BigDecimal cost;
	private BigDecimal price;
	private Instant dateEntry;
	private Instant dateExit;
	
	private Sector sector;

	private ListCode listCode;
	
	public Product() {}

	private Product(String name, String characteristics, BigDecimal cost, BigDecimal price, Instant dateEntry,
			Instant dateExit, Sector sector, ListCode listCode) {
		this.name = name;
		this.characteristics = characteristics;
		this.cost = cost;
		this.price = price;
		this.dateEntry = dateEntry;
		this.dateExit = dateExit;
		this.sector = sector;
		this.listCode = listCode;
	}

	public Product(Long id, String name, String characteristics, BigDecimal cost, BigDecimal price, Instant dateEntry,
			Instant dateExit, Sector sector, ListCode listCode) {
		this.id = id;
		this.name = name;
		this.characteristics = characteristics;
		this.cost = cost;
		this.price = price;
		this.dateEntry = dateEntry;
		this.dateExit = dateExit;
		this.sector = sector;
		this.listCode = listCode;
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

	// Method that uses the utility to convert both cost and price
	public Product convertCurrency(String costCoin, String priceCoin) {
		BigDecimal cost = convertCurrencyToBigDecimal(costCoin);
		BigDecimal price = convertCurrencyToBigDecimal(priceCoin);
		return new Product(this.name, this.characteristics, cost, price, this.dateEntry, this.dateExit, this.sector, this.listCode);
	}

    // Method to set the dateEntry and dateExit using the string date
    public Product convertDate(String entryDateStr, String exitDateStr) {
        Instant entryDate = convertDateToInstant(entryDateStr);
        Instant exitDate = convertDateToInstant(exitDateStr);
        return new Product(this.name, this.characteristics, this.cost, this.price, entryDate, exitDate, this.sector, this.listCode);
    }

    // Private method to avoid code duplication
    private BigDecimal convertCurrencyToBigDecimal(String coin) {
        // Remove all non-numeric characters except for dots and commas
        String valueWithoutCurrency = coin.replaceAll("[^0-9.,]", "").trim();
        // Replace commas with dots if the number is in the "000,00" format
        valueWithoutCurrency = valueWithoutCurrency.replace(",", ".");
        return new BigDecimal(valueWithoutCurrency);
    }

	// New Method to convert date String to Instant (ISO 8601)
	private Instant convertDateToInstant(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(dateStr, formatter);
		return localDate.atStartOfDay().toInstant(java.time.ZoneOffset.UTC);
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