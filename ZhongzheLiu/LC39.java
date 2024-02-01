import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

public class LC39 {
    int[] nums;
    List<Integer> combination;
    List<List<Integer>> results;
    int target;

    // sorting is not necessary
    public List<List<Integer>> combinationSum(int[] inputCandidates, int inputTarget) {
        nums = inputCandidates;
        target = inputTarget;
        results = new ArrayList<>();
        combination = new ArrayList<>();
        combinationSumRecOpt(0, 0);
        return results;
    }

    // level-wise backtracking, although the current level is implicit(the length of combination)
    public void combinationSumRecLevel(int sum, int startIdx) {
        if (sum == target) {
            results.add(new ArrayList<>(combination));
            return;
        }
        if (sum > target || startIdx == nums.length) return;
        for (int i = startIdx; i < nums.length; i++) {
            combination.add(nums[i]);
            combinationSumRecLevel(sum + nums[i], i); // passing i so that candidates[i] can be used for multiple times
            combination.remove(combination.size() - 1);
        }
    }

    // We deal with one number in the array for each method call and don't use it anymore in the subsequent call(by pass startIdx + 1)
    // We can think of this as an element-wise backtracking, but involves all duplicate
    public void combinationSumRecElement(int sum, int startIdx) {
        if (sum == target) {
            results.add(new ArrayList<>(combination));
            return;
        }
        if (startIdx == nums.length) return;
        // we don't use this candidate
        combinationSumRecElement(sum, startIdx + 1);
        int candidate = nums[startIdx];
        int count = 0;
        // we can use one candidate as many times as we can
        while (true) {
            sum += candidate;
            if (sum > target) break;
            combination.add(candidate);
            combinationSumRecElement(sum, startIdx + 1);
            count++;
        }
        for (int i = 0; i < count; i++) combination.remove(combination.size() - 1);
    }

    public void combinationSumRecElementAlt(int sum, int startIdx) {
        if (startIdx == nums.length) return;
        combinationSumRecElementAlt(sum, startIdx + 1);
        int candidate = nums[startIdx];
        int count = 0;
        while (true) {
            sum += candidate;
            if (sum > target) break;
            combination.add(candidate);
            count++;
            if (sum == target) {
                results.add(new ArrayList<>(combination));
                break;
            }
            combinationSumRecElementAlt(sum, startIdx + 1);
        }
        for (int i = 0; i < count; i++) combination.remove(combination.size() - 1);
    }

    // Contrary to the previous two methods, this method only add one number to the list(if we add at all) for each method call
    // Thus we may add the same number to the list for multiple times in multiple consecutive calls
    // The two different calls correspond to
    // 1. don't add this number any more in subsequent method call
    // 2. add this number to the list, and may or may not add it again
    // Doing this covers the case of using the same number for multiple time
    // Also avoids duplicate in an elegant way: if i<j, we never add candidates[j] to the list until we are done with candidates[i]
    // Thus all occurrence of candidates[i] come before all occurrence of candidates[j], which eliminates duplicate combinations
    // Therefore the main idea is still "order matters"
    // We can think of this as a element-wise backtracking, which involves a single element and doesn't necessarily add an entry to the combination list
    public void combinationSumRecOpt(int sum, int startIdx) {
        if (sum == target) {
            results.add(new ArrayList<>(combination));
            return;
        }
        if (startIdx == nums.length) return;
        combinationSumRecOpt(sum, startIdx + 1);
        int candidate = nums[startIdx];
        if (sum + candidate > target) return;
        combination.add(candidate);
        combinationSumRecOpt(sum + candidate, startIdx);
        combination.remove(combination.size() - 1);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // The following methods only counts combination
    int count;

    // Essentially the same as LC39, the number in array can be chosen unlimited number of times, but we only counts combinations,
    // thus we can use the code of LC39 to test the following two methods
    public int combinationSum4CombinationBacktracking(int[] array, int t) {
        count = 0;
        target = t;
        nums = array;
        combinationSum4CombinationRecElement(0, 0);
        return count;
    }

    // Element-wise backtracking
    public void combinationSum4CombinationRecElement(int index, int currSum) {
        if (currSum == target) {
            // nums[i]>=1, we don't have to continue DFS, although we may not have a complete combination by now
            count++;
            return;
        }
        if (index == nums.length || currSum > target) return;
        combinationSum4CombinationRecElement(index + 1, currSum); // skip nums[index]
        combinationSum4CombinationRecElement(index, currSum + nums[index]); // use nums[index]
    }

    // also Element-wise backtracking and since target>0 and initial currSum=0, it's actually equivalent to combinationSum4CombinationRec()
    public void combinationSum4CombinationRecElementAlt(int index, int currSum) {
        if (currSum == target) {
            count++;
            return;
        }
        if (index == nums.length) return;
        combinationSum4CombinationRecElementAlt(index + 1, currSum);
        if (currSum + nums[index] > target) return;
        combinationSum4CombinationRecElementAlt(index, currSum + nums[index]);
    }

    public int combinationSum4CombinationBacktrackingAlt(int[] array, int t) {
        count = 0;
        target = t;
        nums = array;
        combinationSum4CombinationRecLevel(0, 0);
        return count;
    }

    // Level-wise backtracking, each method call corresponds to a complete combination of certain length
    public void combinationSum4CombinationRecLevel(int index, int currSum) {
        if (currSum == target) {
            count++;
            return;
        }
        if (index == nums.length || currSum > target) return;
        for (int i = index; i < nums.length; i++) {
            combinationSum4CombinationRecLevel(i, currSum + nums[i]);
        }
    }
}
