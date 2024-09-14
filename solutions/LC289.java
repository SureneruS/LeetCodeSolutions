class Solution {
    /*
     * 0 -> dead, dead to dead
     * 1 -> alive, alive to alive
     * 2 -> alive to dead
     * 3 -> dead to alive
     * 
     */
    public void gameOfLife(int[][] board) {
        int[] d = {1, 0, -1, -1, 1, -1, 0, 1, 1};
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                for (int x = 0; x < 8; x++) {
                    int di = i + d[x];
                    int dj = j + d[x + 1];
                    if (di >= 0 && dj >= 0 && di < m && dj < n) {
                        if (board[di][dj] == 1 || board[di][dj] == 2) {
                            count++;
                        }
                    }
                }

                System.out.println(i + " " + j + " " + count);

                if (board[i][j] == 1 && (count < 2 || count > 3)) {
                    board[i][j] = 2;
                }
                else if (board[i][j] == 0 && count == 3) {
                    board[i][j] = 3;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] %= 2;
            }
        }
    }
}