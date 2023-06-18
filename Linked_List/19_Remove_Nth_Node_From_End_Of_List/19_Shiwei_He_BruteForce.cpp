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
    int getLength(ListNode* head) {
        ListNode* cur = head;
        int count = 1;
        while(cur->next) {
            cur = cur->next;
            count++;
        }
        return count;
    }

    ListNode* removeNthFromEnd(ListNode* head, int n) {
        int count = getLength(head);
        ListNode* dummy = new ListNode(0, head);
        ListNode* cur = dummy;

        // cur points to the element prior to the target
        for (int i = 1; i < count - n + 1; i++) {
            cur = cur->next;
        }
        // cout << "The cur points to: "<< cur->val << endl;
        cur->next = cur->next->next;
        ListNode* res = dummy->next;
        delete dummy;
        return res;
    }
};

// [1] 1 cur == dummy
// [1,2] 2 
