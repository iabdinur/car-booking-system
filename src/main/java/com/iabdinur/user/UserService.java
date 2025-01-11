package com.iabdinur.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(@Qualifier("userFileDataAccessService") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }


    public User getUserById(UUID id) {
        for (User user : getUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }


}