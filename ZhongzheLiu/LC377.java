import java.util.Arrays;

public class LC377 {
    // AD, 8.06%, 3ms, interestingly, dp is not the fastest way
    // The optimal substructure of this problem is not at all obvious but rather tricky,
    // because unlike other knapsack-like problems, this problem counts the number of permutation
    // We need to focus our attention on the last/first number in the permutation of dp[i],
    // there are at most nums.length possibilities, and we can only consider the ones smaller than i
    // For each permutation that ends with num, by removing that last/first number, we have a permutation of i-num
    // It's obvious that this is a one-to-one correspondence and has no duplicates and doesn't have overlapping with permutations with other num
    public int combinationSum4Dp(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            int count = 0;
            for (int num : nums) {
                if (num > i) continue;
                count += dp[i - num];
            }
            dp[i] = count;
        }
        return dp[target];
    }

    // AD, 70.64%, 1ms, in forward direction there is still reasoning of no duplicates: dp[i+num] += dp[i] meaning appending num to the end of every permutation of dp[i] to get permutations of dp[i+num]. Notice that no other dp[j] with j!=i can lead to those permutations
    public int combinationSum4DpForward(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < target; i++) {
            for (int num : nums) {
                if (i + num > target) continue;
                dp[i + num] += dp[i];
            }
        }
        return dp[target];
    }

    // AD, 24.05%, 2ms, sorting+shortcut, not obviously faster than combinationSum4Dp()
    public int combinationSum4Dp2(int[] nums, int target) {
        Arrays.sort(nums);
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            int count = 0;
            for (int num : nums) {
                if (num > i) break;
                count += dp[i - num];
            }
            dp[i] = count;
        }
        return dp[target];
    }

    // AD, 8.06%, 8ms
    public int combinationSum4Dp2D(int[] nums, int target) {
        // dp[i][j] here is defined as: the number of combinations of j that ends with nums[i]
        int[][] dp = new int[nums.length][target + 1];

        // since we always count the sum of an entire column, we can use one dimensional cache and reduce to combinationSum4Dp
        for (int j = 1; j <= target; j++) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > j) continue;
                int count;
                if (nums[i] == j) {
                    count = 1;
                } else {
                    count = 0;
                    for (int k = 0; k < nums.length; k++) count += dp[k][j - nums[i]]; // line of redundancy
                }
                dp[i][j] = count;
            }
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) sum += dp[i][target];
        return sum;
    }

    // AD, beats 100% not sure why it's faster
    public int combinationSum4TopDownMemoization(int[] array, int t) {
        target = t;
        nums = array;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1); // -1 indicates uninitialized
        // A ver important edge case: this is a common but special handling, because array[i]>=1,
        // there is only one way to get 0 sum, that is picking nothing from the array.
        // Another way to understand this initialization is to notice that when we need dp[0]:
        // in the previous call, i=num, where we need to count dp[i], and there is number num=i,
        // thus picking that number gives a combination, we don't need to pick anything else, and we are at i=0
        dp[0] = 1;
        return combinationSum4TopDownMemoizationRec(target, dp);
    }

    public int combinationSum4TopDownMemoizationRec(int i, int[] dp) {
        if (i < 0) return 0;
        if (i == 0) return 1;
        if (dp[i] != -1) return dp[i];
        int count = 0;
        // we can always check if num>=i before we call
        for (int num : nums) count += combinationSum4TopDownMemoizationRec(i - num, dp);
        dp[i] = count;
        return count;
    }

    // Backtracking is still a correct way to solve this problem, because the combinations that sum up to target can still be generated by making pick/don't pick choices on the array. The issue is 1<=nums.length<=200, thus in the worst case 2^200 complexity is too high for backtracking.
    int count;
    int target;
    int[] nums;

    // Time Limit Exceeded
    // the complexity of backtracking is simply too high, especially when there are small numbers in array but target is very large
    public int combinationSum4Backtracking(int[] array, int t) {
        count = 0;
        target = t;
        nums = array;
        combinationSum4Rec(0);
        return count;
    }

    // Level-wise backtracking, the total level is unknown
    // Is element-wise backtracking applicable? I doubt it because a number can appear anywhere in a permutation and we don't the length/number of levels of it.
    // Although this method will be called with the same argument for many times, they still represent different combinations, because prior to that call, the ongoing(incomplete) permutation must be unique
    public void combinationSum4Rec(int currSum) {
        if (currSum > target) return;

        if (currSum == target) {
            count++;
            return;
        }

        for (int num : nums) {
            combinationSum4Rec(currSum + num);
        }
    }

    // New Experimentation of Backtracking+Memoization
    int[] memo;

    // AD, 100%
    public int combinationSum4PermutationBacktrackingMemo(int[] array, int t) {
        target = t;
        nums = array;
        memo = new int[target + 1];
        Arrays.fill(memo, -1);
        // starting from 0 makes sense, because all valid permutation starts from 0 and adds up to target
        return combinationSum4PermutationRecMemo(0);
    }

    // Does this method exploit optimal substructure? I think this is sort of a reversed recurrence.
    // The meaning of this method call: starting from currSum, how many ways to get a sum of target using the numbers in nums[]?
    // Conclusion: this is still dynamic programming, using top-down recursion + memoization, not backtracking. It still exploits optimal substructure, but in a unusual way, almost like a reversed recurrence of the typical one in combinationSum4TopDownMemoization(). Here currSum=0 is the biggest problem, currSum+num is actually the subproblem of currSum, which is quite against the intuition but is correct. This also tells us an important lesson: the relation between a problem and its subproblems is not always that simple and intuitive.
    // Nevertheless this is still backward update not forward update
    public int combinationSum4PermutationRecMemo(int currSum) {
        if (currSum > target) return 0;
        if (memo[currSum] != -1) return memo[currSum];
        if (currSum == target) return 1; // because nums[i]>=1, starting from target we have only one way: pick nothing

        int count = 0;
        for (int num : nums) {
            count += combinationSum4PermutationRecMemo(currSum + num);
        }

        memo[currSum] = count;
        return count;
    }

    public static void main(String[] args) {
        LC377 solution = new LC377();
//        int[] array = new int[]{2,1,3};
//        solution.combinationSum4Backtracking(array, 35);
    }
}
