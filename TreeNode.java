import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        fill(root, 0, result);
        return result;
    }

    private void fill(TreeNode root, int depth, List<Integer> result) {
        if (root == null) {
            return;
        }

        if (result.size() == depth) {
            result.add(root.val);
        }

        fill(root.right, depth + 1, result);
        fill(root.left, depth + 1, result);
    }
}