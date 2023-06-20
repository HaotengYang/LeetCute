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
    // Method 1: Recursion
    // Time complexity: O(n).
    // Space complexity: O(n).
    vector<int> answer;
    void dfs(TreeNode * cur) {
        if (cur == NULL) {
            return;
        }
        answer.push_back(cur->val);
        dfs(cur->left);
        dfs(cur->right);
    }

    vector<int> preorderTraversal1(TreeNode* root) {
        dfs(root);
        return answer;
    }

    // Approach 2: Iteration
    // Space Complexity: O(n)
    // Time complexity: O(n)
    vector<int> preorderTraversal2(TreeNode* root) {
        vector<int> result;
        stack<TreeNode*> s;
        s.push(root);
        
        while(!s.empty()) {
            TreeNode* cur = s.top();
            s.pop();
            if (cur != nullptr) {
                result.push_back(cur->val);
                s.push(cur->right);
                s.push(cur->left);
            }
        }
        return result;
    }
};