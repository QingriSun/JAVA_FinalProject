package service;

import model.User;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final String USER_DATA = "users.dat";
    private Map<String, User> users = new HashMap<>();
    private User currentUser;

    public UserService() {
        loadUsers();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_DATA))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // 首次运行无文件
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setGuest() {
        currentUser = null;
    }

    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) return false;
        users.put(user.getUsername(), user);
        saveUsers();
        return true;
    }

//    public boolean login(String username, String password) {
//        User user = users.get(username);
//        if (user != null && user.getPassword().equals(password)) {
//            currentUser = user;
//            return true;
//        }
//        return false;
//    }
public boolean validateUser(String username, String password) {
    User user = users.get(username);
    return user != null && user.getPassword().equals(password);
}

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getCurrentUser() { return currentUser; }
    public boolean isGuest() { return currentUser == null; }
}