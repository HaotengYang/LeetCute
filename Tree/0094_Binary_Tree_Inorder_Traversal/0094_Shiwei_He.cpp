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

    vector<int> inorderTraversal1(TreeNode* root) {
        dfs(root);
        return result;
    }

    // Approach 2: Iteration
    // Time complexity: O(n)
    // Space complexity: O(h), h is the depth of the tree (where a stack takes up)
    vector<int> inorderTraversal2(TreeNode* root) {
        vector<int> result;
        TreeNode* cur = root;
        stack<TreeNode*> s;

        while(!s.empty() || cur != nullptr) {
            while(cur != nullptr) {
                s.push(cur);
                cur = cur->left;
            }
            cur = s.top();
            s.pop();
            result.push_back(cur->val);
            cur = cur->right;
        }
        return result;
    }
};

