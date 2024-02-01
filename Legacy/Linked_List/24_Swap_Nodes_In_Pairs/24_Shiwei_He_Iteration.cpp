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
    ListNode* swapPairs(ListNode* head) {
        ListNode* dummyHead = new ListNode(0);
        dummyHead->next = head;
        ListNode* tmp = dummyHead;

        while(tmp && tmp->next && tmp->next->next) {
            ListNode* node1 = tmp->next;
            ListNode* node2 = tmp->next->next;

            tmp->next = node2;
            node1->next = node2->next;
            node2->next = node1;

            tmp = node1;
        }
        return dummyHead->next;
    }
};
// Time O(n), Space O(1)
