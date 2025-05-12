package view.game;
import model.User;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.UserService;

//游戏界面的窗体
public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JLabel userLabel;
    private User user;

    public GameFrame(int width, int height, MapModel mapModel, User user) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        this.user = user;
        //this.gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(0,70), 180, 50);
        this.gamePanel = new GamePanel(mapModel,stepLabel);
        int panelX = 30;
        int panelY = height / 2 - gamePanel.getHeight() / 2;
        gamePanel.setLocation(panelX, panelY);
        this.add(gamePanel);
        stepLabel.setLocation(gamePanel.getWidth() + 80, 70);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 310), 80, 50);

        this.userLabel = FrameUtil.createJLabel(this,(user != null) ? user.getUsername() : "Guest", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 18), 180, 50);

        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
//        this.loadBtn.addActionListener(e -> {
//            //String string = JOptionPane.showInputDialog(this, "Input path:");
//            //System.out.println(string);
//
//            String path = String.format("save/%s/data.txt",user.getUsername());
//
//            controller.loadGame(path);
//
//            gamePanel.requestFocusInWindow();//enable key listener
//        });
        this.loadBtn.addActionListener(e -> {
//            if (isGuest()) {
//                JOptionPane.showMessageDialog(this, "Guests cannot load games!", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                if (controller.loadGame(user) {
//                    JOptionPane.showMessageDialog(this, "Game loaded!");
//                }
//            }
//        });
            if (controller.loadGame(user)) {
                JOptionPane.showMessageDialog(this, "Game loaded successfully");
            }
        });
//        this.saveBtn.addActionListener(e -> {
//            controller.saveGame(user);
//            JOptionPane.showMessageDialog(gamePanel, "Game saved!");
//            gamePanel.requestFocusInWindow();
//        });
        this.saveBtn.addActionListener( e -> {
            if (isGuest()) {
                JOptionPane.showMessageDialog(this, "Guests cannot save games!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                controller.saveGame(user);
                JOptionPane.showMessageDialog(this, "Game saved!");
            }
        });

        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.user = user;

        if (isGuest()) {
            saveBtn.setEnabled(false);
            saveBtn.setToolTipText("Guests cannot save games");
        }

    }




//     if (isGuest()) {
//        saveBtn.setEnabled(false);
//        saveBtn.setToolTipText("Guests cannot save games");
//    }


//        controller.saveGame(userService.getCurrentUser());
//        JOptionPane.showMessageDialog(this, "Game saved!");
        // 在 GameFrame 的构造函数中修改保存按钮监听器
//        this.saveBtn.addActionListener( e -> {
//            if (isGuest()) {
//                JOptionPane.showMessageDialog(this, "Guests cannot save games!", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                controller.saveGame(user);
//                JOptionPane.showMessageDialog(this, "Game saved!");
//            }
//        });


    // 在 GameFrame 的构造函数中修改加载按钮监听器
//loadBtn.addActionListener(e -> {
//        if (isGuest()) {
//            JOptionPane.showMessageDialog(this, "Guests cannot load games!", "Error", JOptionPane.ERROR_MESSAGE);
//        } else {
//            if (controller.loadGame(user)) {
//                JOptionPane.showMessageDialog(this, "Game loaded!");
//            }
//        }
//    });

        public boolean isGuest() {
            return user == null;
        }
}


