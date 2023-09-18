package com.projet.buyback.model.sport;

import java.time.LocalDate;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sport")
public class Sport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "address_id")
	private Address address;
	@ManyToOne
	@JoinColumn(name = "sportCategory_id")
	private SportCategory sportCategory;
	@ManyToOne
	@JoinColumn(name = "forsale_user_id")
	private User forsaleUserId;
	@ManyToOne
	@JoinColumn(name = "purshase_user_id")
	private User purshaseUserId;

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


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public SportCategory getSportCategory() {
		return sportCategory;
	}

	public void setSportCategory(SportCategory sportCategory) {
		this.sportCategory = sportCategory;
	}


	public User getForsaleUserId() {
		return forsaleUserId;
	}

	public void setForsaleUserId(User forsaleUserId) {
		this.forsaleUserId = forsaleUserId;
	}

	public User getPurshaseUserId() {
		return purshaseUserId;
	}

	public void setPurshaseUserId(User purshaseUserId) {
		this.purshaseUserId = purshaseUserId;
	}

	@Override
	public String toString() {
		return "Sport [id=" + id + ", name=" + name + ", price=" + price + ", startDate=" + startDate + ", endDate="
				+ endDate + ", address=" + address + ", sportCategory=" + sportCategory + ", forsaleUserId="
				+ forsaleUserId + ", purshaseUserId=" + purshaseUserId + "]";
	}



}
