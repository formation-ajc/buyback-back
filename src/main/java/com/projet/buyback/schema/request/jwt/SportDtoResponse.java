package com.projet.buyback.schema.request.jwt;

import java.time.LocalDate;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.sport.SportCategory;

public class SportDtoResponse {

	private Long id;
	private String name;
	private Double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private Address address;
	private SportCategory category;
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;

	
	public SportDtoResponse() {
		super();
	}


	public SportDtoResponse(Long id, String name, Double price, LocalDate startDate, LocalDate endDate, Address address,
			SportCategory category, Long userId, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.category = category;
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public SportCategory getCategory() {
		return category;
	}

	public void setCategory(SportCategory category) {
		this.category = category;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		return "SportDtoResponse [id=" + id + ", name=" + name + ", price=" + price + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", address=" + address + ", category=" + category + ", userId=" + userId
				+ ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	

}
