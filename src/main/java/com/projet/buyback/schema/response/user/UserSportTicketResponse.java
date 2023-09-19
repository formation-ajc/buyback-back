package com.projet.buyback.schema.response.user;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.sport.Sport;

import java.time.LocalDate;

public class UserSportTicketResponse {
    private Long id;
    private String name;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address address;
    private UserPublicResponse seller;

    public static UserSportTicketResponse getUserSportTicketResponse(Sport sportTicket) {
        //info ticket
        UserSportTicketResponse userSportTicketResponse = new UserSportTicketResponse();
        userSportTicketResponse.setId(sportTicket.getId());
        userSportTicketResponse.setName(sportTicket.getName());
        userSportTicketResponse.setPrice(sportTicket.getPrice());
        userSportTicketResponse.setStartDate(sportTicket.getStartDate());
        userSportTicketResponse.setEndDate(sportTicket.getEndDate());
        userSportTicketResponse.setAddress(sportTicket.getAddress());

        //info seller
        UserPublicResponse userPublicResponse = new UserPublicResponse();
        userPublicResponse.setId(sportTicket.getForsaleUserId().getId());
        userPublicResponse.setFirstname(sportTicket.getForsaleUserId().getFirstname());
        userPublicResponse.setLastname(sportTicket.getForsaleUserId().getLastname());
        userPublicResponse.setEmail(sportTicket.getForsaleUserId().getEmail());
        userSportTicketResponse.setSeller(userPublicResponse);

        return userSportTicketResponse;
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

    public UserPublicResponse getSeller() {
        return seller;
    }

    public void setSeller(UserPublicResponse seller) {
        this.seller = seller;
    }
}
