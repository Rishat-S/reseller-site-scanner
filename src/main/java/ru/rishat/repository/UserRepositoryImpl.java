package ru.rishat.repository;

import ru.rishat.entity.User;

import static ru.rishat.constants.Constants.DATA_FROM_FILE;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getUser() {
        String[] authData = DATA_FROM_FILE.get(0).split(",");
        return new User(authData[0], authData[1]);
    }
}
