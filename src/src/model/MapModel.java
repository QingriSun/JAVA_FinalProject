package src.model;

import java.sql.Array;


/**
 * This class is to record the map of one game. For example:
 */
public class MapModel {
    int[][] matrix;
    int[][] matrixInitial;

    // constructor; input a two-dimensional array; new MaoModel(twoDimensionalMatrix)
    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.matrixInitial = new int[matrix.length][matrix[0].length];
        copyMatrix(matrix, this.matrixInitial);
    }
    
    public static void copyMatrix(int[][] initialMatrix, int[][] destinationMatrix)
    {
        for (int i = 0; i < initialMatrix.length; i++)
        {
            for (int j = 0; j < initialMatrix[0].length; j++)
            {
                destinationMatrix[i][j] = initialMatrix[i][j];
            }
        }
    }

    public void setMatrix(int matrix[][])
    {
        copyMatrix(matrix, this.matrix);
    }

    public  int[][] getMatrixInitial()
    {
        return matrixInitial;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }
}
