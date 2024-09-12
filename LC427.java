
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}

class Solution {
    public Node construct(int[][] grid) {
        return constructSubGrid(grid, 0, 0, grid.length, grid.length);
    }

    private Node constructSubGrid(int[][] grid, int startx, int starty, int endx, int endy) {
        if (startx == endx || starty == endy) {
            return null;
        }

        if (endx == startx + 1 && endy == starty + 1) {
            return new Node(grid[startx][starty] == 1, true);
        }

        // System.out.println(startx + " " + starty + " " + endx + " " + endy);
        int centrex = startx + (endx - startx) / 2;
        int centrey = starty + (endy - starty) / 2;
        Node topLeft = constructSubGrid(grid, startx, starty, centrex, centrey);
        Node topRight = constructSubGrid(grid, startx, centrey, centrex, endy);
        Node bottomLeft = constructSubGrid(grid, centrex, starty, endx, centrey);
        Node bottomRight = constructSubGrid(grid, centrex, centrey, endx, endy);

        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf && topLeft.val == topRight.val
                && topLeft.val == bottomLeft.val && topLeft.val == bottomRight.val) {
            return topLeft;
        }

        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
}