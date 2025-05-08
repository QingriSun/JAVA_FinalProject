// a huge constructor, set up the window of the game
// how the buttons on the frame work remain to be done

package src.view.game;

import src.controller.GameController;
import src.model.MapModel;
import src.view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton withdrawBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private MovementPanel movementPanel;
    private JPanel exitPanel;

    // constructor, new GameFrame(width, height, mapMadel)
    public GameFrame(int width, int height, MapModel mapModel) {
// the title can be modified
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null); // have no layout manager
        this.setSize(width, height);

        gamePanel = new GamePanel(mapModel);
        movementPanel = new MovementPanel(gamePanel);
        movementPanel.setLocation(width / 2 + 20, height / 2 - movementPanel.getHeight() / 2);
        this.add(movementPanel);
        int gamePanelX = 30;
        gamePanel.setLocation(gamePanelX, height / 2 - gamePanel.getHeight() / 2); // the y coordinate is dependent on the size of the frame and panel
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        // tell the user where the exit is
        exitPanel = new JPanel();
        exitPanel.setVisible(true);
        exitPanel.setSize(20, 2 * gamePanel.getGRID_SIZE());
        exitPanel.setLocation(gamePanel.getX() + gamePanel.getWidth(), gamePanel.getY() + gamePanel.getWidth() / 4);
        exitPanel.setBackground(Color.cyan);
        this.add(exitPanel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanelX, height / 2 - 150), 80, 40);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanelX + 80 + 20,height /2 -150), 80, 40);
        this.withdrawBtn = FrameUtil.createButton(this, "Withdraw", new Point(gamePanelX, height / 2 + 110), 80, 40);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> // e for event, induced by the system
        {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });

// this loadBtn.addActionListener remains to be complete (store the information, i.e. mapModel, path)
        this.loadBtn.addActionListener(e ->
        {
            // variable string stores the information of path
            String string = JOptionPane.showInputDialog(this /* the dialog will be at the centre of the parent component*/, "Input path:");
            System.out.println(string); //print to the terminal
            gamePanel.requestFocusInWindow();//enable key listener, 键盘
        });

        // get back to last state, and step --
        this.withdrawBtn.addActionListener(e ->{
            System.out.println("Withdraw");
            int[][] lastState = new int[gamePanel.getModel().getMatrix().length][gamePanel.getModel().getMatrix()[0].length];
            if (gamePanel.getStates().size() == 1) // the target last state is the initial state
            {
                lastState = gamePanel.getModel().getMatrixInitial();
            }
            else if (gamePanel.getStates().size() > 1)
            {
                // the last step is unwanted
                gamePanel.getStates().removeLast();
                lastState = gamePanel.getStates().getLast();
            }
            if (lastState != null) // when steps > 0
            {
                for ( int i = 0; i < gamePanel.getBoxes().size(); i++)
                {
                    gamePanel.remove(gamePanel.getBoxes().get(i));
                }
                gamePanel.getBoxes().clear();
                MapModel.copyMatrix(lastState, gamePanel.getModel().getMatrix());
                if (gamePanel.getSteps() > 0)
                {
                    gamePanel.setSteps(gamePanel.getSteps() - 1);
                    gamePanel.getStepLabel().setText(String.format("Step: %d", gamePanel.getSteps()));
                }
                gamePanel.initialGame();
            }
        });

        //todo: add other button here
        this.setLocationRelativeTo(null); // the window is placed in the centre of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // set the location of the victoryInterface
        getGamePanel().getVictoryInterface().setLocationRelativeTo(getMovementPanel().getBtnRight());
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public MovementPanel getMovementPanel()
    {
        return movementPanel;
    }
}
