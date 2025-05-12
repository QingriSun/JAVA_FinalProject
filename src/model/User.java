package model;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String savePath;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.savePath = "save/" + username + "/data.txt";
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSavePath() { return savePath; }
}



