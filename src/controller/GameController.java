package controller;

import model.*;
import view.game.BoxComponent;
import view.game.GamePanel;

import java.nio.file.Paths;
import java.util.List;
import model.Direction;
import model.MapModel;

import javax.swing.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;
/**
 * import model.Direction;
 * import model.MapModel;
 * import view.game.BoxComponent;
 * import view.game.GamePanel;
 *
 * import java.io.IOException;
 * import java.nio.file.Files;
 * import java.nio.file.Path;
 * import java.util.ArrayList;
 * import java.util.List;
 */

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private int steps;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
        this.model.resetOriginalMatrix();
        this.view.clearAllBoxFromPanel();
        this.view.initialGame(model.getMatrix());
    }

//    public boolean loadGame(String path) {
//        try {
//            List<String> lines = Files.readAllLines(Path.of(path));
//            int[][] map = new int[lines.size()][lines.size()];
//            model.setMatrix(map);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        this.view.clearAllBoxFromPanel();
//        this.view.initialGame(model.getMatrix());
//        return false;
//    }

    public boolean doMove(int row, int col, Direction direction) {
        if (model.getId(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    steps++;
                    view.updateStepLabel(steps);
                    return true;
                }
            }
        }
        return false;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    //     public void saveGame(User user) {
//            try {
//                SaveData data = new SaveData(model.getMatrix(), view.getSteps());
//                Path saveDir = Paths.get("save", user.getUsername());
//                Files.createDirectories(saveDir);
//                try (ObjectOutputStream oos = new ObjectOutputStream(
//                        Files.newOutputStream(saveDir.resolve("save.dat")))
//                {
//                oos.writeObject(data);
//                }
//        } catch (IOException e) {
//                JOptionPane.showMessageDialog(view, "Save failed: " + e.getMessage());
//            }
//        }
//
//        public boolean loadGame(User user) {
//            Path saveFile = Paths.get("save", user.getUsername(), "save.dat");
//            if (!Files.exists(saveFile)) return false;
//
//            try (ObjectInputStream ois = new ObjectInputStream(
//                    Files.newInputStream(saveFile)))
//            {
//                SaveData data = (SaveData) ois.readObject();
//                model.setMatrix(data.getMapState());
//                steps = data.getSteps();
//                view.setSteps(steps);
//                view.refresh();
//                return true;
//            } catch (InvalidClassException | EOFException e) {
//                // 处理损坏文件
//                Files.delete(saveFile);
//                JOptionPane.showMessageDialog(view, "Corrupted save file!");
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(view, "Load failed: " + e.getMessage());
//            }
//            return false;
//        }
//public void saveGame(User user) {
//    if (user == null) {
//        JOptionPane.showMessageDialog(null, "Guests cannot save games!");
//        return;
//    }
//    // 1. 创建要保存的数据对象
//    int[][] map = model.getMatrix();
//    int steps = view.getSteps();
//    SaveData data = new SaveData(map, steps);
//
//    // 2. 创建用户存档目录
//    String userDirPath = "save/" + user.getUsername();
//    Path userDir = Path.of(userDirPath);
//    try {
//        Files.createDirectories(userDir);
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(null, "无法创建存档目录: " + e.getMessage());
//        return;
//    }
//
//    // 3. 序列化数据到文件
//    Path saveFile = userDir.resolve("save.dat");
//    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(saveFile))) {
//        oos.writeObject(data); // ✅ 正确调用 writeObject
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(null, "保存失败: " + e.getMessage());
//    }
//}
    public void saveGame(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Guests cannot save games!");
            return;
        }

        try {
            // 1. 准备数据
            int[][] map = model.getMatrix();
            SaveData data = new SaveData(map, steps);

            // 2. 创建用户存档目录
            Path saveDir = Path.of("save", user.getUsername());
            Files.createDirectories(saveDir);

            // 3. 写入序列化文件
            Path saveFile = saveDir.resolve("save.dat");
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(saveFile))) {
                oos.writeObject(data);
            }

            JOptionPane.showMessageDialog(null, "Game saved!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Save failed: " + e.getMessage());
        }
    }

    public boolean loadGame(User user) {
        if (user == null || user.getUsername() == null) {
            JOptionPane.showMessageDialog(null, "Invalid user data");
            return false;
        }

        Path saveFile = Paths.get("save", user.getUsername(), "save.dat");
        if (!Files.exists(saveFile)) {
            JOptionPane.showMessageDialog(null, "No saved game found");
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(saveFile))) {
            SaveData data = (SaveData) ois.readObject();
            // 更新模型
            model.setMatrix(data.getMapState());

            // 更新视图
            view.setSteps(data.getSteps());
            view.refresh();

            return true;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Save file format error");
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Load failed: " + e.getMessage());
            return false;
        }
    }




//public void doMove(...) {
//            // ... 移动成功后更新步数
//            steps++;
//            view.updateStepLabel(steps);
//        }
}




    //todo: add other methods such as loadGame, saveGame...


