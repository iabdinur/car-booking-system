package com.iabdinur;

import com.iabdinur.booking.Booking;
import com.iabdinur.booking.BookingDAO;
import com.iabdinur.booking.BookingService;
import com.iabdinur.car.Car;
import com.iabdinur.car.CarDAO;
import com.iabdinur.car.CarService;
import com.iabdinur.user.User;
import com.iabdinur.user.UserDAO;
import com.iabdinur.user.UserFileDataAccessService;
import com.iabdinur.user.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {


        UserDAO userDAO = new UserFileDataAccessService();
        UserService userService = new UserService(userDAO);

        BookingDAO bookingDAO = new BookingDAO();
       CarDAO carDAO = new CarDAO();

       CarService carService= new CarService(carDAO);
        BookingService bookingService = new BookingService(bookingDAO, carService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            viewMenu();
            System.out.println("Hello and Welcome, What would you like to do?");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> bookCar(userService, bookingService, scanner);
                case "2" -> viewAllUserBookedCars(userService, bookingService, scanner);
                case "3" -> viewAllBookings(bookingService);
                case "4" -> viewAvailableCars(bookingService, false);
                case "5" -> viewAvailableCars(bookingService, true);
                case "6" -> viewAllUsers(userService);
                case "7" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println(input + " is not a valid option ❌");
            }
        }
    }



    private static void bookCar (UserService userService, BookingService bookingService, Scanner scanner){
        viewAvailableCars(bookingService, false);
        System.out.println("➡️ select car reg number");
        String regNumber = scanner.nextLine();
        viewAllUsers(userService);
        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();
        try {
            User user = userService.getUserById(UUID.fromString(userId));
            if (user == null) {
                System.out.println("❌ No user found with id " + userId);
            } else {
                UUID bookingId = bookingService.bookCar(user, regNumber);
                String confirmationMessage = """
                        🎉 Successfully booked car with reg number %s for user %s
                        Booking ref: %s
                        """.formatted(regNumber, user, bookingId);
                System.out.println(confirmationMessage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void viewAllUserBookedCars(UserService userService, BookingService bookingService, Scanner scanner) {
        viewAllUsers(userService);
        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();
        User user = userService.getUserById(UUID.fromString(userId));
        if (user == null) {
            System.out.println("❌ No user found with id " + userId);
            return;
        }

        List<Car> userBookedCars = BookingService.getUserBookedCars(user.getId());
        if (userBookedCars.isEmpty()) {
            System.out.printf("❌ user %s has no cars booked", user);
            return;
        }
        for (Car userBookedCar : userBookedCars) {
            System.out.println(userBookedCar);
        }
    }

    private static void viewAllBookings (BookingService bookingService){
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings available 😕");
            return;
        }
        for (Booking booking : bookings) {
            System.out.println("booking = " + booking);
        }
    }

    private static void viewAvailableCars(BookingService bookingService, boolean isElectric) {
        List<Car> availableCars = isElectric ? bookingService.getAvailableElectricCars() : bookingService.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("❌ No cars available for renting");
            return;
        }
        for (Car availableCar : availableCars) {
            System.out.println(availableCar);
        }
    }


    private static void viewAllUsers(UserService userService) {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            System.out.println("❌ No users in the system");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void viewMenu() {
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - View Available Cars");
        System.out.println("5️⃣ - View Available Electric Cars");
        System.out.println("6️⃣ - View all users");
        System.out.println("7️⃣ - Exit");
    }

}
