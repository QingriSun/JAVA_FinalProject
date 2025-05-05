package src;

import src.model.MapModel;
import src.view.game.GameFrame;
import src.view.game.GameStartInterface;
import src.view.login.LoginFrame;
import src.view.game.GameStartInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // initialize the login frame
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);

// only one mapModel; more levels should be added (for advanced point)
            MapModel mapModel = new MapModel(new int[][]{
//                    {1, 2, 2, 1},
//                    {1, 3, 2, 2},
//                    {1, 3, 4, 4},
//                    {0, 0, 4, 4}
                    {2, 2, 1, 3, 0},
                    {1, 2, 2, 3, 1},
                    {4, 4, 0, 2, 2},
                    {4, 4, 2, 2, 1}

            });

            // initialize the game frame
            GameFrame gameFrame = new GameFrame(600, 450, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);

            // turn the first two parameter into corresponding method output, when the log-in and game-saving are done
            GameStartInterface gameStartInterface = new GameStartInterface(true,true,280,280);
            gameStartInterface.setGameFrame(gameFrame);
            // the initial state (should be only the log-in interface)
            gameStartInterface.setVisible(false);
            loginFrame.setGameStartInterface(gameStartInterface);
        });
    }
}
