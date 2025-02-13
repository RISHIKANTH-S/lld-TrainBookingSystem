package com.example.IRCTC.Service;

import com.example.IRCTC.Entities.Train;
import com.example.IRCTC.Entities.User;
import com.example.IRCTC.Util.UserServiceUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserService {
	private ObjectMapper objectMapper = new ObjectMapper();
	private List<User> userList;

	private User user;

	private final String USER_FILE_PATH = "src/main/java/com/example/IRCTC/LocalDb/Users.json";

	public UserService(User user) throws IOException {
		this.user = user;
		loadUserListFromFile();
	}

	public UserService() throws IOException {
		loadUserListFromFile();
	}

	private void loadUserListFromFile() throws IOException {
			userList = objectMapper.readValue(new File(USER_FILE_PATH),new TypeReference<List<User>>() {});
		}

	public Boolean loginUser(){
		//System.out.println("Attempting login for user: " + user.getName());
		//System.out.println(user.getPassword());
		//System.out.println(userList);
		Optional<User> foundUser = userList.stream().filter(user1 -> {
			return user1.getName().equals(user.getName()) &&
					UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
		}).findFirst();
		return foundUser.isPresent();
	}

	public Boolean signUp(User user1){
		try{
			userList.add(user1);
			saveUserListToFile();
			return Boolean.TRUE;
		}catch (IOException ex){
			return Boolean.FALSE;
		}
	}

	private void saveUserListToFile() throws IOException {
		File usersFile = new File(USER_FILE_PATH);
		objectMapper.writeValue(usersFile, userList);
	}

	public void fetchBookings(){
		System.out.println("entered fetch");
		Optional<User> userFetched = userList.stream().filter(user1 -> {
			return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
		}).findFirst();
		if(userFetched.isPresent()){
			userFetched.get().printTickets();
		}
	}

	public Boolean cancelBooking(String ticketId){
		if (user == null || user.getTicketList() == null) {
			System.out.println("User or ticket list is not available.");
			return false;
		}
		if (ticketId == null || ticketId.isEmpty()) {
			System.out.println("Ticket ID cannot be null or empty.");
			return Boolean.FALSE;
		}
		boolean removed = user.getTicketList().removeIf(ticket -> ticket.getTicketId().equals(ticketId));
		if (removed) {
			System.out.println("Ticket with ID " + ticketId + " has been canceled.");
			return Boolean.TRUE;
		}else{
			System.out.println("No ticket found with ID " + ticketId);
			return Boolean.FALSE;
		}
	}


	public List<Train> getTrains(String source, String destination){
		try{
			TrainService trainService = new TrainService();
			return trainService.searchTrains(source, destination);
		}catch(IOException ex){
			return new ArrayList<>();
		}
	}

	public List<List<Integer>> fetchSeats(Train train){
		return train.getSeats();
	}

	public Boolean bookTrainSeatByNumber(Train train, int seatNumber) {
		try {
			// Validate seat number (must be between 1-25)
			if (seatNumber < 1 || seatNumber > 25) {
				System.out.println("Invalid seat number. Choose a seat number between 1 and 25.");
				return Boolean.FALSE;
			}

			List<List<Integer>> seats = train.getSeats();

			int row = (seatNumber - 1) / 5;
			int col = (seatNumber - 1) % 5;

			// Check if the seat is available
			if (seats.get(row).get(col) == 0) {
				seats.get(row).set(col, 1);
				train.setSeats(seats);// Mark the seat as booked
				//train.setSeats(seats); // Update seat matrix

				System.out.println("Seat " + seatNumber + " (Row: " + row + ", Col: " + col + ") successfully booked!");
				return Boolean.TRUE;
			} else {
				System.out.println("Seat " + seatNumber + " is already booked. Please choose another seat.");
				return Boolean.FALSE;
			}
		} catch (Exception ex) {
			System.err.println("Error while booking seat: " + ex.getMessage());
			return Boolean.FALSE;
		}
	}

}
