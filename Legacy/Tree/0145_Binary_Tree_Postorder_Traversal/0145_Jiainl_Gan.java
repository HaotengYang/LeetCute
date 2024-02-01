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

// Posorder traversal:
// Left sub tree -> Right sub tree -> Root
class Solution {

    // Recursive method
    // Time Complexity: O(n), every nodes are visited once.
    // Space Complexity: O(n), As the worst case is
    // that the depth of the tree is n - 1, and resulting in
    // a function calling stack of n - 1;
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> nodeValues = new ArrayList<>();
        traverse(nodeValues, root);
        return nodeValues;
    }
    
    private void traverse(List<Integer> nodeValues, TreeNode node) {
        if(node == null) return;
        traverse(nodeValues, node.left);
        traverse(nodeValues, node.right);
        nodeValues.add(node.val);
    }
}