package ru.rishat.entity;

public class User {
    private Long id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.id = generateID();
        this.name = name;
        this.password = password;
    }

    private Long generateID() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
