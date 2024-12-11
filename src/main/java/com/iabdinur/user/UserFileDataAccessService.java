package com.iabdinur.user;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO {
    @Override
    public User[] getUsers() {

        // Provide the file path where the data.csv file is located
        File file = new File("src/users.csv");

        // List to store the Person objects
        User[] users = new User[4];

        try {
            int index = 0;
            // Create a new scanner class  to read the file
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                String[] fields = scan.nextLine().split(",");
                users[index] = new User(UUID.fromString(fields[0]), fields[1]);
                index++;
            }
            return users;
        } catch (
                IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
