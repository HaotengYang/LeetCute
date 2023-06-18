/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* removeNthFromEnd(ListNode* head, int n) {
        ListNode* dummy = new ListNode(0, head);
        ListNode* fast = dummy;
        ListNode* slow = dummy;
        
        // Set the distance bettwen fast and slow "n" nodes
        for (int i = 0; i < n; i++) {
            fast = fast->next;
        }

        // Set the slow to the element before target
        while(fast->next) {
            fast = fast->next;
            slow = slow->next;
        }

        // Deleting process
        ListNode* tmp = slow->next;
        slow->next = slow->next->next;
        delete tmp;
        return dummy->next;
    }
};

// One Pass
// [1] 1 
// [1,2] 1
// [1,2] 2