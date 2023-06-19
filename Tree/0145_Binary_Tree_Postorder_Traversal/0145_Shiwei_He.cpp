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
    vector<int> answer;
    
    void dfs(TreeNode* cur) {
        if (cur == NULL) {
            return;
        }
        dfs(cur->left);
        dfs(cur->right);
        answer.push_back(cur->val);
    }

    vector<int> postorderTraversal(TreeNode* root) {
        dfs(root);
        return answer;
    }
};