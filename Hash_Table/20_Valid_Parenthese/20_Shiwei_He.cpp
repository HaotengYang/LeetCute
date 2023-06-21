class Solution {
public:
    // Time complexity: O(n), n is the chars in string
    // Space complexity: O(n+|∑|), space taken by stack and hash table
    bool isValid(string s) {
        int n = s.size();
        if(n % 2 != 0) return false;
        
        stack<char> stk;
        unordered_map<char, char> myMap = {
            {')','('}, // close bracket is the key
            {']','['},
            {'}','{'}
        };

        for(char c : s) {
            if(myMap.count(c)) { // count() tracks key, not value, thus ']', ']', ']'
                if(stk.empty() || stk.top() != myMap[c]) {
                    // no open bracket in the stack or no matching brackets
                    return false;
                }
                stk.pop(); // once matched, pop out the open bracket in the stack
            }else {
                // c is a value, thus c == '(', '{', '['
                stk.push(c);
            }
        }
        return stk.empty();
    }
};