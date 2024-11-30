package com.henrique.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListCode implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	private Long id;
	private String code;
	
	private List<Product> products = new ArrayList<>();

	public ListCode(String code) {
		this.code = code;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public List<Product> getProducts() {
		return products;
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
		ListCode other = (ListCode) obj;
		return Objects.equals(id, other.id);
	}
	
}