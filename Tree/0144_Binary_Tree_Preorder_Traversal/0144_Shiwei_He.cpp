/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
// Method 1: Recursion
// Time complexity: O(n) as each nodes are visited once.
// Space complexity: O(n). The worst case is every node
// has exactly one left sub tree (with both left and right children) 
// and a right child (without any child), which will resulting in a 
// visitedRoots stack of size (n - 1) / 2 .  
// TODO: it contradicts editorial answer in LC
public:
    vector<int> answer;
    void dfs(TreeNode * cur) {
        if (cur == NULL) {
            return;
        }
        answer.push_back(cur->val);
        dfs(cur->left);
        dfs(cur->right);
    }

    vector<int> preorderTraversal(TreeNode* root) {
        dfs(root);
        return answer;
    }
};

