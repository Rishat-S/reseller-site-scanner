package ru.rishat.service;

import org.openqa.selenium.WebDriver;
import ru.rishat.entity.User;

public interface UserService {
    User getUser();

    void logIn(WebDriver driver, User user);
}
