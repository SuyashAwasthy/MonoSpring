package com.techlabs.hibernate;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techlabs.hibernate.dao.CountryDao;
import com.techlabs.hibernate.entity.Country;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private CountryDao countryDao;
	
	public Application(CountryDao countryDao) {
		super();
		this.countryDao = countryDao;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	public void run(String... args) throws Exception {
		addCountry();
	//	getAllCountries();
	//	getCountryById();
	//	getCountryByName();
	//	updateCountry();
	//	updateCountryWithoutMerge();
	//	deleteCountry();
	}
	
	private void updateCountryWithoutMerge() {
		Country country = new Country("ABC","countryname",501);
		countryDao.updateCountry(country);
	}

	private void deleteCountry() {
		countryDao.deleteCountry("AU");
	}

	private void updateCountry() {
		Country country = new Country("XYZ","any",201);
		countryDao.updateCountry(country);
	}

	private void getCountryByName() {
		List<Country> countryList = countryDao.getCountryByName("India");
		System.out.println(countryList);
	}

	private void getCountryById() {
		Country country = countryDao.getCountryById("IN");
		if(country!=null)
			System.out.println(country);
		else
			System.out.println("Country not found");
	}

	private void getAllcountries() {
		List<Country> countryList = countryDao.getAllCountries();
		for(Country c : countryList) {
			System.out.println(c);
		}
	}

	private void addCountry() {
		Country country = new Country("DEF","country",201);
		countryDao.save(country);
	}

}
