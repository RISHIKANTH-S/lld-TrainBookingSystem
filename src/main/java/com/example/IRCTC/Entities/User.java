package com.example.IRCTC.Entities;

import com.example.IRCTC.Util.UserServiceUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketList;
    private String userId;
    public User(String name, String password, List<Ticket> ticketList, String userId) { // for login
        this.name = name;
        this.password = password;
        this.ticketList = ticketList;
        this.userId = userId;
    }
    public User(String name, String password, String hashedPassword, List<Ticket> ticketList, String userId) { // for registration
        this.name = name;
        this.password = password;  // Plain password (for registration)
        this.hashedPassword = hashedPassword;  // Store the hashed password
        this.ticketList = ticketList;
        this.userId = userId;
    }
    public User(){}
    public void setName(String name) {
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }
    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", ticketList=" + ticketList +
                ", userId='" + userId + '\'' +
                '}';
    }

    public void printTickets(){
        for (int i = 0; i<ticketList.size(); i++){
            System.out.println(ticketList.get(i).getTicketInfo());
        }
    }

}
