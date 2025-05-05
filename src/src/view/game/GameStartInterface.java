// a frame with two bottom: New Game, Continue

package src.view.game;

import src.view.FrameUtil;
import src.view.game.GameFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameStartInterface extends JFrame {
    private JButton btnNewGame;
    private JButton btnContinue;
    private GameFrame gameFrame;

    // constructor
    public GameStartInterface(boolean isLoggedInUser,boolean haveGameSaving, int width, int height)
    {
        // set the parameter of the window
        this.setTitle("Game Start Interface");
        this.setSize(width, height);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // The user logged in or not, there will be a "New Game" button
        btnNewGame = FrameUtil.createButton(this, "New Game", new Point(85, 85), 100, 40);
        btnNewGame.addActionListener(e ->
        {
            this.setVisible(false);
            this.gameFrame.setVisible(true);
        });

        if (isLoggedInUser && haveGameSaving)
        {
            // if it is a logged-in user and there is a game saving, a "Continue" option should be available
            btnContinue = FrameUtil.createButton(this, "Continue", new Point(85, 145), 100, 40);

// add action listener for the continue button
        }
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }
}
