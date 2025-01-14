package com.iabdinur;

import com.iabdinur.booking.Booking;
import com.iabdinur.booking.BookingService;
import com.iabdinur.car.Car;
import com.iabdinur.car.CarService;
import com.iabdinur.user.User;
import com.iabdinur.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleApp {

    private final UserService userService;
    private final CarService carService;
    private final BookingService bookingService;

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ConsoleApp(UserService userService, CarService carService, BookingService bookingService) {
        this.userService = userService;
        this.carService = carService;
        this.bookingService = bookingService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            viewMenu();
            System.out.println("Hello and Welcome, What would you like to do?");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> bookCar();
                case "2" -> viewAllUserBookedCars();
                case "3" -> viewAllBookings();
                case "4" -> viewAvailableCars(false);
                case "5" -> viewAvailableCars(true);
                case "6" -> viewAllUsers();
                case "7" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println(input + " is not a valid option ❌");
            }
        }
    }

    private void bookCar() {
        viewAvailableCars(false);
        System.out.println("➡️ select car reg number");
        String regNumber = scanner.nextLine();
        viewAllUsers();
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

    private void viewAllUserBookedCars() {
        viewAllUsers();
        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();
        User user = userService.getUserById(UUID.fromString(userId));
        if (user == null) {
            System.out.println("❌ No user found with id " + userId);
            return;
        }

        List<Car> userBookedCars = bookingService.getUserBookedCars(user.getId());
        if (userBookedCars.isEmpty()) {
            System.out.printf("❌ user %s has no cars booked%n", user);
            return;
        }
        for (Car userBookedCar : userBookedCars) {
            System.out.println(userBookedCar);
        }
    }

    private void viewAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings available 😕");
            return;
        }
        for (Booking booking : bookings) {
            System.out.println("booking = " + booking);
        }
    }

    private void viewAvailableCars(boolean isElectric) {
        List<Car> availableCars = isElectric ? bookingService.getAvailableElectricCars() : bookingService.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("❌ No cars available for renting");
            return;
        }
        for (Car availableCar : availableCars) {
            System.out.println(availableCar);
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            System.out.println("❌ No users in the system");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void viewMenu() {
        System.out.println("1️⃣ - Book Car");
        System.out.println("2️⃣ - View All User Booked Cars");
        System.out.println("3️⃣ - View All Bookings");
        System.out.println("4️⃣ - View Available Cars");
        System.out.println("5️⃣ - View Available Electric Cars");
        System.out.println("6️⃣ - View all users");
        System.out.println("7️⃣ - Exit");
    }
}
