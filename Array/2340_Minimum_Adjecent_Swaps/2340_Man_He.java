public class S1_2340 {

    public int minimumSwaps(int[] nums) {
        //遍历数组找出最大值和最小值对应的下标
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int min_index = -1;
        int max_index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= max) {
                max_index = i;
                max = nums[i];
            }
            if (nums[i] < min) {
                min_index = i;
                min = nums[i];
            }
        }
        int n = nums.length;
        if (min_index <= max_index) return min_index + n - 1 - max_index;
            //min_index在max_index左边，只用计算min_index往左交换的次数+max_index往右交换的次数
        else return min_index + n - 1 - (max_index + 1);
        //min_index在max_index右边，还要考虑min_index和max_index之间的交换
    }
}
