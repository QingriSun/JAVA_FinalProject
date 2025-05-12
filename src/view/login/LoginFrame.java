package view.login;


import controller.UserController;
import model.MapModel;
import model.User;
import service.UserService;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//如何实现登录
public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private GameFrame gameFrame;//?
    private JButton guestBtn = FrameUtil.createButton(this, "Guest", new Point(100, 200), 100, 40);
    private JButton registerBtn = FrameUtil.createButton(this, "Register", new Point(100, 260), 100, 40);
    private UserService userService = new UserService();



    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJPasswordField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);

        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            char[] passwordChars = password.getPassword();
            String passwordText = new String(passwordChars);
            System.out.println("Password = " + passwordText);
            if (this.gameFrame != null) {
                this.gameFrame.setVisible(true);
                this.setVisible(false);
            }
            //todo: check login info
            if(UserController.validateUser(username.getText(), password.getText())){
                User user = new User(username.getText(), password.getText());
                MapModel mapModel = new MapModel(new int[][]{
                        {1, 2, 2, 1},
                        {1, 3, 2, 2},
                        {1, 3, 4, 4},
                        {0, 0, 4, 4},
                });
                GameFrame gameFrame = new GameFrame(600, 450, mapModel, user);
                gameFrame.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        guestBtn.addActionListener(e -> {
            userService.setGuest();
            launchGame();
        });


//        submitBtn.addActionListener(e -> {
//            String username = username.getText();
//            String password = new String(password.getPassword());
//
//            if (userService.login(username, password)) {
//                launchGame();
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid credentials!");
//            }
//        });

        registerBtn.addActionListener(e -> showRegisterDialog());
    }

    private void showRegisterDialog() {
        JTextField regUser = new JTextField();
        JPasswordField regPass = new JPasswordField();
        Object[] fields = {"Username:", regUser, "Password:", regPass};

        int option = JOptionPane.showConfirmDialog(this, fields, "Register",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = regUser.getText().trim();
            String password = new String(regPass.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty!");
                return;
            }
            User newUser = new User(username, password);
            if (userService.register(newUser)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!");
            }
        }
    }


    private void launchGame() {
        User currentUser = userService.getCurrentUser();
        int[][] initialMatrix = {
                {1, 2, 2, 1, 1},
                {3, 4, 4, 2, 2},
                {3, 4, 4, 1, 0},
                {1, 2, 2, 1, 0},
                {1, 1, 1, 1, 1}
        };
        MapModel mapModel = new MapModel(initialMatrix);
        GameFrame gameFrame = new GameFrame(800, 600, mapModel, currentUser);
        GameFrame gameFrame1 = new GameFrame(800, 600, mapModel, null);
        gameFrame.setVisible(true);
        this.dispose();
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
}
