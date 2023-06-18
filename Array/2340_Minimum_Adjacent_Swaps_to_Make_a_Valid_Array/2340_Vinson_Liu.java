class Solution { // AD
    public int minimumSwaps(int[] nums) {
        if (nums.length == 1) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minIdx = 0;
        int maxIdx = 0;

        // invariant: after initialization, minIdx always points to the leftmost min and maxIdx the rightmost max
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num < min) {
                min = num;
                minIdx = i;
            }
            if (num >= max) {
                max = num;
                maxIdx = i;
            }
        }

        // the distance between maxIdx and nums.length - 1 is the number of swaps needed: nums.length - 1 - maxIdx
        // the distance between minIdx and 0 is the number of swaps needed: minIdx-0=minIdx
        if (maxIdx > minIdx) {
            return minIdx + nums.length - 1 - maxIdx;
        } else {
            // edge cases: minIdx = maxIdx, which is impossible since we have excluded the case where nums.length=1
            // if max=min meaning there is only one unique element in the array, minIdx and maxIdx could not be the same
            // thus minIdx > maxIdx here
            return minIdx + nums.length - 2 - maxIdx;
        }
    }
}