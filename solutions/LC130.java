import java.util.List;

class Solution {
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        // printBoard(board, m, n);

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // System.err.println(i + " " + j + " " + visited[i][j]);
                if (board[i][j] == 'O' && !visited[i][j]) {
                    boolean isSurrounded = !isNotSurrounded(board, i, j, visited);
                    // System.out.println(i + " " + j + " s:" + isSurrounded);
                    if (isSurrounded) {
                        markRegionAsX(board, i, j);
                    }
                }
            }
        }

        // printBoard(board, m, n);

        return;
    }

    private void printBoard(char[][] board, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isNotSurrounded(char[][] board, int row, int col, boolean[][] visited) {
        visited[row][col] = true;

        if (board[row][col] == 'X') {
            return false;
        }

        boolean hasEdge = false;

        for (int[] dir : new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }) {
            int i = dir[0];
            int j = dir[1];

            if (isEdge(board, row + i, col + j) || (!visited[row + i][col + j] && isNotSurrounded(board, row + i, col + j, visited))) {
                hasEdge = true;
            }
        }

        return hasEdge;
    }

    private boolean isEdge(char[][] board, int row, int col) {
        return row < 0 || row >= board.length || col < 0 || col >= board[0].length;
    }

    private void markRegionAsX(char[][] board, int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] == 'X') {
            return;
        }

        board[row][col] = 'X';

        for (int[] dir : new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }) {
            int i = dir[0];
            int j = dir[1];
            markRegionAsX(board, row + i, col + j);
        }
    }

    public List<Object[]> getTestCases() {
        return List.of(
                new Object[][] {
                        { new char[][] { { 'X', 'X', 'X', 'X' }, { 'X', 'O', 'O', 'X' }, { 'X', 'X', 'O', 'X' },
                                { 'X', 'O', 'X', 'X' } } },
                        { new char[][] { { 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X' }, { 'X', 'X', 'X', 'X' },
                                { 'X', 'O', 'X', 'X' } } },
                        { new char[][] { { 'X' } } },
                        { new char[][] { { 'O' } } },
                        { convertStringToCharMatrix("[[\"O\",\"O\",\"O\"],[\"O\",\"O\",\"O\"],[\"O\",\"O\",\"O\"]]") },
                        { convertStringToCharMatrix(
                                "[[\"O\",\"O\",\"O\",\"O\",\"X\",\"X\"],[\"O\",\"O\",\"O\",\"O\",\"O\",\"O\"],[\"O\",\"X\",\"O\",\"X\",\"O\",\"O\"],[\"O\",\"X\",\"O\",\"O\",\"X\",\"O\"],[\"O\",\"X\",\"O\",\"X\",\"O\",\"O\"],[\"O\",\"X\",\"O\",\"O\",\"O\",\"O\"]]") }
                });
    }

    public char[][] convertStringToCharMatrix(String str) {
        str = str.substring(1, str.length() - 1); // Remove outer brackets
        String[] rows = str.split("],\\[");
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;
        char[][] matrix = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            String[] elements = rows[i].split(",");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = elements[j].replaceAll("[^a-zA-Z0-9]", "").charAt(0);
            }
        }
        return matrix;
    }

}
