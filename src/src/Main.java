package src;

import src.model.MapModel;
import src.view.game.GameFrame;
import src.view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);

// only one mapModel; more levels should be added
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 2, 2, 1},
                    {1, 3, 2, 2},
                    {1, 3, 4, 4},
                    {0, 0, 4, 4}
            });

            // initialize the game frame
            GameFrame gameFrame = new GameFrame(600, 450, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);
        });
    }
}
