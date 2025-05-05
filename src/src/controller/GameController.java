// have methods to restart games, move boxes
// to have methods to reload games, save games

package src.controller;

import src.model.Direction;
import src.model.MapModel;
import src.view.game.BoxComponent;
import src.view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    // constructor of GameController
    // new GameController(view, madel);
    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

// needed extra code
    public void restartGame() {
        System.out.println("Do restart game here");
    }


    //reset the mapModel and the parameter of the box
    public boolean doMove(int row, int col, Direction direction) {
        // move the 1 * 1 rectangular
        if (model.getId(row, col) == 1) // if there is s 1 * 1 rectangular in that place
        {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) // check if the rectangular get out of the boundary
            {
                if (model.getId(nextRow, nextCol) == 0) // if the place to be occupied is empty (accessible)
                {
                    model.getMatrix()[row][col] = 0; // make the place where the rectangular occupied to be empty
                    model.getMatrix()[nextRow][nextCol] = 1; // move the rectangular to the accessible place
                    // change the parameter of the corresponding component
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    // ? what is the meaning of the magic number 2 ? : may be padding
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2); // GRID_SIZE is a final int 50
                    box.repaint(); // repaint
                    return true; // the rectangular is moved
                }
            }
        }
//
//        // move the 1 * 2 rectangular
//        if (model.getId(row, col) == 2)
//        {
//            // locate the left part of the rectangular
//            if
//        }

        return false; // the rectangular can not be moved to the intended place (due to boundary or other rectangular)
    }



    //todo: add other methods such as loadGame, saveGame...

}
