package ru.rishat.service;

import org.openqa.selenium.WebDriver;
import ru.rishat.entity.User;
import ru.rishat.login.LogIn;
import ru.rishat.repository.UserRepository;
import ru.rishat.repository.UserRepositoryImpl;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    @Override
    public User getUser() {
        return userRepository.getUser();
    }

    @Override
    public void logIn(WebDriver driver, User user) {
        LogIn.logIn(driver, user);
    }
}
