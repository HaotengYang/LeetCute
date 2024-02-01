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

/**
 * Preorder traversal
 * Root -> All left child nodes -> All right child nodes
 *
 */
class Solution {

    // Method 1: Iterative, using Stack.
    // Time complexity: O(n) as each nodes are visited once.
    // Space complexity: O(n). The worst case is every node
    // has exactly one left sub tree (with both left and right children) 
    // and a right child (without any child), which will resulting in a 
    // visitedRoots stack of size (n - 1) / 2 .  
    // TODO: it contradicts editorial answer in LC
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> nodeValues = new ArrayList<>();
        Stack<TreeNode> visitedRoots = new Stack<>();
        visitedRoots.add(root);

        while(!visitedRoots.isEmpty()) {
            TreeNode currentRoot = visitedRoots.pop();
            if(currentRoot != null){
                // Add right sub tree before left sub tree
                // in order to keep left sub tree on the top
                // within a pair of siblings.
                nodeValues.add(currentRoot.val);
                visitedRoots.add(currentRoot.right);
                visitedRoots.add(currentRoot.left);
            }
        }
        return nodeValues;
    }

    // Method 2: Recursive calls.
    // Time complexity: O(n) as each nodes are visited once.
    // Space complexity: O(n). The worst case is every node
    // has exactly one child, which will resulting in a 
    // function calling stack of size n - 1. 
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> nodeValues = new ArrayList<>();
        dfs(nodeValues, root);
        return nodeValues;
    }
    private void dfs(List<Integer> nodeValues, TreeNode node){
        if(node == null) return;
        // Visit the left sub tree so that these nodes
        // are added to the nodeValues first.
        nodeValues.add(node.val);
        dfs(nodeValues, node.left);
        dfs(nodeValues, node.right);
    }

    // Method 3: Morris Traversal
    // TODO

}