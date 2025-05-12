package src.view.game;

import src.view.FrameUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LevelInterface extends JFrame{
    private JButton lastLevelBtn;
    private JButton nextLevelBtn;
    private JButton startGameBtn;
    private JPanel levelPanel;
    private JPanel levelNamePanel;
    private ArrayList<GameStartInterface.Location> locations;
    private ArrayList<LevelInterface> levelInterfaces;
    private int level;
    static int LevelNumber = 1; // level = 1, 2, 3, 4, 5

    // change the boxes' position
    private GamePanel gamePanel;
    private GameFrame gameFrame;

    public LevelInterface(GameStartInterface gameStartInterface) {
        this.setLayout(null);
        this.setSize(600, 450);
        this.setTitle("Level Choosing");
        this.gamePanel = gamePanel;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.locations = gameStartInterface.getLocations();
        this.levelInterfaces = gameStartInterface.getLevelInterfaces();
        this.level = LevelNumber;
        LevelNumber++;

        lastLevelBtn = FrameUtil.createButton(this, "", new Point(10, this.getHeight() / 2 - 40), 20, 40);
        nextLevelBtn = FrameUtil.createButton(this, "", new Point(getWidth() - 50, getHeight() / 2 - 40), 20, 40);
        startGameBtn = FrameUtil.createButton(this, "Start Game", new Point(getWidth() / 2 - 60, getHeight() / 2 + 135), 120, 40);
        levelPanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 250, getHeight() / 2 - 125), 480, 250, Color.LIGHT_GRAY);
        levelNamePanel = FrameUtil.createPanel(this, new Point(getWidth() / 2 - 200, getHeight() / 2 - 185), 400, 50, Color.DARK_GRAY);

        startGameBtn.addActionListener(e -> {
            if (this.gameFrame != null) {
                this.gameFrame.setVisible(true); // turn to the game-start frame
                this.setVisible(false); // hide the register page
                for (int i = 0; i < LevelNumber - 1; i++)
                {
                    gameStartInterface.getLevelInterfaces().get(i).setVisible(false);
                }
            }
        });

        nextLevelBtn.addActionListener(e ->{
        if (getLevel() != 5) // if there is a next level to choose
        {
            levelInterfaces.get(getLevel() - 1).setVisible(false);
            levelInterfaces.get(getLevel()).setVisible(false);
            levelInterfaces.get(getLevel() - 1).setVisible(true);
            levelInterfaces.get(getLevel()).setVisible(true);
        }
        });

        lastLevelBtn.addActionListener(e ->{
            if (getLevel() != 1)
            {
                levelInterfaces.get(getLevel() - 1).setVisible(false);
                levelInterfaces.get(getLevel() - 2).setVisible(false);
                levelInterfaces.get(getLevel() - 1).setVisible(true);
                levelInterfaces.get(getLevel() - 2).setVisible(true);
            }
        });
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public GameFrame getGameFrame() {return gameFrame;}

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public int getLevel()
    {
        return level;
    }

    public JPanel getLevelNamePanel()
    {
        return levelNamePanel;
    }

    public JPanel getLevelPanel()
    {
        return levelPanel;
    }
}
