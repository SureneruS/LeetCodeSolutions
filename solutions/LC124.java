import java.util.List;
import java.util.ArrayList;

record PathSum(int nodeSum, int maxSum) {}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {

    public int maxPathSum(TreeNode root) {
        return calculatePathSum(root).maxSum();
    }

    private PathSum calculatePathSum(TreeNode node) {
        if (node == null) {
            return new PathSum(0, Integer.MIN_VALUE);
        }

        PathSum leftSum = calculatePathSum(node.left);
        PathSum rightSum = calculatePathSum(node.right);

        Integer maxPathUntilNode = Math.max(0, node.val + Math.max(leftSum.nodeSum(), rightSum.nodeSum()));
        Integer currentMaxSum = node.val + leftSum.nodeSum() + rightSum.nodeSum();
        currentMaxSum = Math.max(currentMaxSum, Math.max(leftSum.maxSum(), rightSum.maxSum()));
        System.out.println(node.val + " " + maxPathUntilNode + " " + currentMaxSum);
        
        return new PathSum(maxPathUntilNode, currentMaxSum);
    }

    public List<Object[]> getTestCases() {
        List<Object[]> testCases = new ArrayList<>();
        testCases.add(new Object[]{ new TreeNode(100)});
        testCases.add(new Object[]{ new TreeNode(-100)});
        testCases.add(new Object[]{ new TreeNode(-100,  new TreeNode(1),  new TreeNode(4))});

        return testCases;
    }
}