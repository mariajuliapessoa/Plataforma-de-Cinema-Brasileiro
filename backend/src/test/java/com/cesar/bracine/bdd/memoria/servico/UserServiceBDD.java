package com.cesar.bracine.bdd.memoria.servico;

import com.cesar.bracine.model.user.User;
import com.cesar.bracine.model.user.UserRole;

import java.util.HashMap;
import java.util.Map;

public class UserServiceBDD {

    private final Map<String, User> users = new HashMap<>();
    private User loggedUser;

    public User createAdmin(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("senha123");
        user.setEmail(username + "@teste.com");
        user.setRole(UserRole.ROLE_ADMIN);

        users.put(username, user);
        return user;
    }

    public void authenticate(String username) {
        loggedUser = users.get(username);
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void promoteToEspecialist(String username) {
        User user = users.get(username);
        if (user != null) {
            user.setRole(UserRole.ROLE_ESPECIALIST);
        }
    }

    public boolean isEspecialist(String username) {
        User user = users.get(username);
        return user != null && user.getRole() == UserRole.ROLE_ESPECIALIST;
    }

    public void cadastrarUsuarioCritico(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("senha123");
        user.setEmail(username + "@teste.com");
        user.setRole(UserRole.ROLE_USER);

        users.put(username, user);
    }
}
