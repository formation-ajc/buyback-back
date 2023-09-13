package com.projet.buyback.schema.request.jwt;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.SportCategory;
import com.projet.buyback.model.User;

public class SportRequest {

	private String name;
	private Double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private String addressName;
	private String addressZipcode;
	private Long sportcategoryId;
	private Long userId;

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

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressZipcode() {
		return addressZipcode;
	}

	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}

	public Long getSportcategoryId() {
		return sportcategoryId;
	}

	public void setSportcategoryId(Long sportcategoryId) {
		this.sportcategoryId = sportcategoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SportRequest [name=" + name + ", price=" + price + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", addressName=" + addressName + ", addressZipcode=" + addressZipcode + ", sportcategoryId="
				+ sportcategoryId + ", userId=" + userId + "]";
	}

}