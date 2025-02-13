package com.example.IRCTC;

import com.example.IRCTC.Entities.Train;
import com.example.IRCTC.Entities.User;
import com.example.IRCTC.Service.UserService;
import com.example.IRCTC.Util.UserServiceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class IrctcApplication {

	public static void main(String[] args) {
		System.out.println("Running Train Booking System");
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		UserService userService;
		try{
			userService = new UserService();
		}catch(IOException ex){
			System.out.println("There is something wrong");
			return;
		}
		Train trainSelectedForBooking = new Train();
		while(option!=7){
			System.out.println("Choose option");
			System.out.println("1. Sign up");
			System.out.println("2. Login");
			System.out.println("3. Fetch Bookings");
			System.out.println("4. Search Trains");
			System.out.println("5. Book a Seat");
			System.out.println("6. Cancel my Booking");
			System.out.println("7. Exit the App");
			option = scanner.nextInt();
			switch (option){
				case 1:
					scanner.nextLine();
					System.out.println("Enter the username to signup");
					String nameToSignUp = scanner.nextLine();
					scanner.nextLine();
					System.out.println("Enter the password to signup");
					String passwordToSignUp = scanner.nextLine();
					User userToSignup = new User(nameToSignUp, passwordToSignUp,UserServiceUtil.hashPassword(passwordToSignUp),new ArrayList<>(), UUID.randomUUID().toString());
					userService.signUp(userToSignup);
					break;
				case 2:
					System.out.println("Enter the username to Login");
					String nameToLogin = scanner.next();
					System.out.println("Username entered: " + nameToLogin);
					System.out.println("Enter the password to Login");
					String passwordToLogin = scanner.next();
					System.out.println("pwd entered: " + passwordToLogin);
					User userToLogin = new User(nameToLogin, passwordToLogin,new ArrayList<>(), UUID.randomUUID().toString());
					try{
						userService = new UserService(userToLogin);
						if (userService.loginUser()) {
							System.out.println("Login successful");
						} else {
							System.out.println("Invalid credentials");
						}
					}catch (IOException ex){
						return;
					}
					break;
				case 3:
					System.out.println("Fetching your bookings");
					userService.fetchBookings();
					break;
				case 4:
					System.out.println("Type your source station");
					String source = scanner.next();
					System.out.println("Type your destination station");
					String dest = scanner.next();
					List<Train> trains = userService.getTrains(source, dest);
					int index = 1;
					for (Train t: trains){
						System.out.println(index+" Train id : "+t.getTrainId());
						for (Map.Entry<String, String> entry: t.getStationTimes().entrySet()){
							System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
						}
					}
					System.out.println(trains);
					System.out.println("Select a train by typing 1,2,3...");
					trainSelectedForBooking = trains.get(scanner.nextInt()-1);
					System.out.println("Train selected for booking: "+trainSelectedForBooking);
					break;
				case 5:
					System.out.println("Select a seat out of these seats");
					System.out.println(trainSelectedForBooking);
					List<List<Integer>> seats = userService.fetchSeats(trainSelectedForBooking);
					for (List<Integer> row: seats){
						for (Integer val: row){
							System.out.print(val+" ");
						}
						System.out.println();
					}
					System.out.println("Select the seatNumber between 1-25");
					int seatNumber = scanner.nextInt();
					System.out.println("Enter the seatNumber");
					Boolean booked = userService.bookTrainSeatByNumber(trainSelectedForBooking,seatNumber);
					System.out.println(trainSelectedForBooking.getSeats());
					if(booked.equals(Boolean.TRUE)){
						System.out.println("Booked! Enjoy your journey");
					}else{
						System.out.println("Can't book this seat");
					}
					break;
				case 6:
					System.out.println("Enter the ticket id to cancel");
					String ticketId = scanner.next();
					userService.cancelBooking(ticketId);
					break;
				default:
					break;
			}
		}
	}

}
