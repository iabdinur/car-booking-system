package com.iabdinur.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO {
    @Override
    public List<User> getUsers() {

        // Provide the file path where the data.csv file is located
        File file = new File("src/com/iabdinur/users.csv");

        // List to store the Person objects
        List<User> users = new ArrayList<>();

        try {
            // Create a new scanner class  to read the file
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                String[] fields = scan.nextLine().split(",");
                users.add(new User(UUID.fromString(fields[0]), fields[1]));
            }
            return users;
        } catch (
                IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
