package com.techlabs.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="country_id")
	private String countryId;
	
	@Column(name="country_name")
	private String countryName;
	
	@Column(name="region_id")
	private int regionId;
	
	public Country() {
		
	}
	
	public Country(String countryName, int regionId) {
		this("", countryName, regionId);
	}
	
	public Country(String countryId, String countryName, int regionId) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
		this.regionId = regionId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryName=" + countryName + ", regionId=" + regionId + "]";
	}
	
}
