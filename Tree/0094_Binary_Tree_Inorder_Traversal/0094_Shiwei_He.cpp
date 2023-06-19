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
public:
    vector<int> result;
    void dfs(TreeNode * cur) {
        if (!cur) return;
        dfs(cur->left);
        result.push_back(cur->val);
        dfs(cur->right);
    }

    vector<int> inorderTraversal(TreeNode* root) {
        dfs(root);
        return result;
    }
};

