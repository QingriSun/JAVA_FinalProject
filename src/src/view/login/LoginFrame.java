// initialize the register window
// function the "confirm" button (to be done), and the "restart" button.

package src.view.login;

import src.view.FrameUtil;
import src.view.game.GameFrame;
import src.view.game.GameStartInterface;

import javax.swing.*;
import java.awt.*;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private GameFrame gameFrame;
    private GameStartInterface gameStartInterface;

    //constructor, a window
    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);

        // after the summit button is pressed
        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());
            if (this.gameStartInterface != null)
            {
                this.gameStartInterface.setVisible(true); // turn to the game-start frame
                this.setVisible(false); // hide the register page
            }
            //todo: check login info
// if the user have registered or not; give out game record if this is a registered user
// a time composition, break record
        });

        // if the reset button is pressed,clean up the textField
        resetBtn.addActionListener(e ->
        {
            username.setText("");
            password.setText("");
        });

        this.setLocationRelativeTo(null); // the register frame is in the middle of the screen
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void setGameStartInterface(GameStartInterface gameStartInterface)
    {
        this.gameStartInterface = gameStartInterface;
    }
}
