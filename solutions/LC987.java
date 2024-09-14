import java.util.List;
import java.util.stream.Collectors;

class NodePos implements Comparable<NodePos> {
    int row;
    int col;
    int val;

    public NodePos(int r, int c, int v) {
        row = r;
        col = c;
        val = v;
    }

    @Override
    public int compareTo(NodePos that) {
        if (this.col != that.col) {
            return this.col - that.col;
        }
        else if (this.row != that.row) {
            return this.row - that.row;
        }
        else {
            return this.val - that.val;
        }
    }

    public String toString() {
        return String.format("%d, %d, %d", row, col, val);
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<NodePos> nodes = new ArrayList<>();
        traverse(root, 0, 0, nodes);

        Collections.sort(nodes);
        // System.out.println(nodes);

        // List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentCol = new ArrayList<>();
        int prevCol = nodes.get(0).col;

        for (var nodePos : nodes) {
            if (nodePos.col != prevCol) {
                result.add(currentCol);
                currentCol = new ArrayList<>();
                prevCol = nodePos.col;
            }
            currentCol.add(nodePos.val);
        }

        var result = nodes.stream().collect(Collectors.groupingBy(node -> node.col, Collectors.mapping(node -> node.val, Collectors.toList()))).values();

        // result.add(currentCol);
        return result;
    }

    private void traverse(TreeNode node, int row, int col, List<NodePos> nodes) {
        if (node == null) {
            return;
        }

        nodes.add(new NodePos(row, col, node.val));
        traverse(node.left, row + 1, col - 1, nodes);
        traverse(node.right, row + 1, col + 1, nodes);
    }
}