import java.util.HashSet;
import java.util.Set;

record Pos(int x, int y) {}

record Cluster(int x, int y, int walls, Set<Pos> spread) {}

class Solution {
    int m,n;
    public int containVirus(int[][] isInfected) {
        m = isInfected.length;
        n = isInfected[0].length;
        int totalWalls = 0;

        while (true) {
            printGrid(isInfected);
            Cluster largestCluster = getLargestCluster(isInfected, false);
            System.out.println("l: " + largestCluster);
            if (largestCluster.spread().size() == 0) {
                return totalWalls;
            }

            totalWalls += largestCluster.walls();
            visitCluster(isInfected, new boolean[m][n], largestCluster.x(), largestCluster.y(), false, true);
            getLargestCluster(isInfected, true);
        }
    }

    private Cluster getLargestCluster(int[][] isInfected, boolean applySpread) {
        boolean[][] visited = new boolean[m][n];
        Cluster largestCluster = new Cluster(0, 0, 0, Set.of());
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && isInfected[i][j] == 1) {
                    var cluster = visitCluster(isInfected, visited, i, j, applySpread, false);
                    System.out.println("c: " + cluster + " applySpread: " + applySpread);
                    if (cluster.spread().size() > largestCluster.spread().size()) {
                        largestCluster = cluster;
                    }
                }
            }
        }
        return largestCluster;
    }

    private static final int[] d = new int[]{-1, 0, 1, 0, -1};

    private Cluster visitCluster(int[][] isInfected, boolean[][] visited, int x, int y, boolean applySpread, boolean deactivate) {
        visited[x][y] = true;
        if (deactivate) {
            isInfected[x][y] = 2;
        }

        Set<Pos> spread = new HashSet<>();
        int walls = 0;
        for (int i = 0; i < 4; i++) {
            int dx = x + d[i];
            int dy = y + d[i + 1];
            if (!isValid(dx, dy)) continue;

            if (isInfected[dx][dy] == 0) {
                walls++;
                if (applySpread) {
                    visited[dx][dy] = true;
                    isInfected[dx][dy] = 1;
                }
                spread.add(new Pos(dx, dy));
            }
            else if (!visited[dx][dy] && isInfected[dx][dy] == 1) {
                var next = visitCluster(isInfected, visited, dx, dy, applySpread, deactivate);
                spread.addAll(next.spread());
                walls += next.walls();
            }
        }

        return new Cluster(x, y, walls, spread);
    }

    private boolean isValid(int x, int y) {
        return (x >= 0 && x < m && y >= 0 && y < n);
    }

    private void printGrid(int[][] grid) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}



// 50 x 50
// identify clusters: mn
// identify largest cluster: mn
// idetify no of walls: mn
// contaminate: mn
// repeat: mn * (mn);
// 6250000