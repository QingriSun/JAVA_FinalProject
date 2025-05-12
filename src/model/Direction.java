package model;

public enum Direction {
    LEFT(0, -1), UP(-1, 0), RIGHT(0, 1), DOWN(1, 0),
    ;//枚举常量
     //每个枚举常量后的括号 (row, col) 是构造函数的参数，用于初始化方向的行列增量。
    private final int row;
    private final int col;
    //final 修饰符确保字段初始化后不可修改，符合枚举常量的不可变性。

    Direction(int row, int col) {//枚举的构造函数默认是 private 的，无法从外部调用，确保枚举常量的唯一性和不可变性。
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}