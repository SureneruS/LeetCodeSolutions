class Solution {
    public void rotate(int[][] matrix) {
        transpose(matrix);
        flipUpDown(matrix);
    }

    private void flipUpDown(int[][] matrix) {
        for(int col = 0; col < matrix.length; col++) {
            for (int row = 0; row < matrix.length / 2; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[matrix.length - row - 1][col];
                matrix[matrix.length - row - 1][col] = temp;
            }
        }
    }

    private void transpose(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}
/*
 * 0 1 0
 * 0 1 0
 * 1 1 1
 * 
 * 1 1 1
 * 0 1 0
 * 0 1 0
 * 
 */