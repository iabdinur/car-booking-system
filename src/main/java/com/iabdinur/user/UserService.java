package com.iabdinur.user;

import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User[] getUsers() {
        return userDAO.getUsers();
    }

    public User getUserById(UUID id) {
        for (User user : userDAO.getUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }


}
