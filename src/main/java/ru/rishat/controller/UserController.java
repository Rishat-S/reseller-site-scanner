package ru.rishat.controller;

import org.openqa.selenium.WebDriver;
import ru.rishat.entity.User;
import ru.rishat.service.UserService;
import ru.rishat.service.UserServiceImpl;

public class UserController {
    UserService userService = new UserServiceImpl();
    public User getUser() {
        return userService.getUser();
    }
    public void logInUser(WebDriver driver, User user) {
        userService.logIn(driver, user);
    }
}
