// a frame with two bottom: New Game, Continue

package src.view.game;

import src.view.FrameUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GameStartInterface extends JFrame {
    private JButton btnChooseLevel;
    private JButton btnContinue;
    private GameFrame gameFrame;
    private ArrayList<LevelInterface> levelInterfaces;
    private ArrayList<Location> locations;
    int levelNum = 5;

    // create an inner class Location
    class Location
    {
        int x;
        int y;
    }

    // define the locations
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    int levelInterfaceWidth = 600;
    int levelInterfaceHeight = 450;
    int spacing = 40;
    int firstX = screenWidth / 2 - levelInterfaceWidth / 2 - spacing * 2;
    int firstY = screenHeight / 2 - levelInterfaceHeight / 2;


    // constructor
    public GameStartInterface(boolean isLoggedInUser,boolean haveGameSaving, int width, int height, GameFrame gameFrame)
    {
        // set the parameter of the window
        this.setTitle("Game Start Interface");
        this.setSize(width, height);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.gameFrame = gameFrame;

        // create levelInterfaces
        levelInterfaces = new ArrayList<>();
        for (int i = 0; i < levelNum; i++)
        {
            setLevelInterface();
        }

        // set the locations
        ArrayList<Location> locations = new ArrayList<>();
        int x = firstX;
        int y = firstY;
        Location location;
        for (int i = 0; i < levelNum; i++)
        {
            location = new Location();
            location.x = x;
            location.y = y;
            locations.add(location);
            x += spacing;
        }

        // The user logged in or not, there will be a "New Game" button
        btnChooseLevel = FrameUtil.createButton(this, "chooseLevel", new Point(85, 85), 100, 40);
        btnChooseLevel.addActionListener(e ->
        {
            this.setVisible(false);

            // set the location of the levelInterfaces
            for (int i = levelNum - 1; i >= 0; i--)
            {
                levelInterfaces.get(i).setLocation(new Point(locations.get(i).x, locations.get(i).y));
                levelInterfaces.get(i).setVisible(true);
            }

            // set image of levelInterfaces
            setPanelImage(levelInterfaces.get(0).getLevelPanel(), "beginerlevel.png");
            setPanelImage(levelInterfaces.get(1).getLevelPanel(), "hengdaolima.png");
            setPanelImage(levelInterfaces.get(2).getLevelPanel(), "jiangyongcaoying.png");
            setPanelImage(levelInterfaces.get(3).getLevelPanel(), "qitoubingjin.png");
            setPanelImage(levelInterfaces.get(4).getLevelPanel(), "zuoyoububing.png");

            // set the name of the level
//            setPanelImage(levelInterface1.getLevelNamePanel(), "black.png");

            // set the location of the VictoryFrame，why it acts as if this.getMovementPanel.getBtnRight = null
            this.gameFrame.getGamePanel().getVictoryInterface().setLocationRelativeTo(this.gameFrame.getMovementPanel().getBtnRight());
        });

        if (isLoggedInUser && haveGameSaving)
        {
            // if it is a logged-in user and there is a game saving, a "Continue" option should be available
            btnContinue = FrameUtil.createButton(this, "Continue", new Point(85, 145), 100, 40);

// add action listener for the continue button
        }
    }

    public void bringNextToFront()
    {


        for (int i = 0; i < levelInterfaces.size() / 2; i++)
        {
            levelInterfaces.get(i).setVisible(true);
            levelInterfaces.get(levelInterfaces.size() - 1 - i).setVisible(true);
        }
        levelInterfaces.get(levelInterfaces.size() / 2 + 1).setVisible(true);
    }

    public void setPanelImage(JPanel JPanelTargetPanel, String imageDocumentName)
    {
        // get the original image file from the resources document
        InputStream imageFile = getClass().getClassLoader().getResourceAsStream(imageDocumentName);
        try
        {
            BufferedImage originalImage = ImageIO.read(imageFile);

            // squeeze the original image
            int scale = Math.max(originalImage.getWidth() / JPanelTargetPanel.getWidth(), originalImage.getHeight() / JPanelTargetPanel.getHeight()) + 1;
            // if the original image is smaller than the panel, draw the original size image
            if (scale == 0)
            {
                scale = 1;
            }
            int targetWidth = originalImage.getWidth() / scale;
            int targetHeight = originalImage.getHeight() / scale;
            // put the image to the targetPanel
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imageContainer = new JLabel(imageIcon);
            JPanelTargetPanel.add(imageContainer);

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Location> getLocations()
    {
        return locations;
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setLevelInterface()
    {
        // let the LevelInterface have the variable of gameFrame
        LevelInterface ALevelInterface = new LevelInterface(this);
        ALevelInterface.setGameFrame(gameFrame);
        ALevelInterface.setGamePanel(gameFrame.getGamePanel());
        levelInterfaces.add(ALevelInterface);
    }

    public ArrayList<LevelInterface> getLevelInterfaces()
    {
        return levelInterfaces;
    }
}
