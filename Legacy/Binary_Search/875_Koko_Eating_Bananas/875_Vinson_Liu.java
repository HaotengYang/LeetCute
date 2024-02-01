class Solution {
    // It's easy to see that faster speed means less time to eat all piles and slower speed means more time
    // The minimal speed is obviously 1, and the maximal speed is the maximal pile in piles, max(piles)
    // minimal speed corresponds to sum(piles) hours and maximal speed corresponds to piles.length hours
    // Two edge cases are obvious:
    // 1. if sum(piles)<h, 1 is the answer, otherwise speed of 1 won't meet the requirement
    // 2. if max(piles)>h, it's impossible to finish eating all bananas before the guard returns
    // If none of the edge cases are true, we have the range of the minimal speed : (1, max(piles))
    // The monotonicity is not hard to spot: [1,ans-1] will yield a total time larger than h
    // while [ans,max(piles)] will yield a total time no greater than h
    // Due to integer division and the problem property, the definite predicate of ans is rather cumbersome to write,
    // because at this speed, the time may be smaller or equal to h, and their may multiple speed that satisfy this condition
    // and since we want the minimal one, the predicate will be: time(ans)<=h and time(ans-1)>h
    // Predicate of the lower section: time(i)>h
    // Predicate of the upper section: time(ans-1)<=h

    // AD, 69.72%
    public int minEatingSpeed(int[] piles, int h) {
        int high = Integer.MIN_VALUE;
        long sum = 0;
        for (int pile : piles) {
            high = Math.max(high, pile);
            sum += pile;
        }
        int low = (int)(sum / h) + (sum % h == 0 ? 0 : 1);
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (calTime(piles, mid, h)) low = mid + 1;
            else high = mid;
        }
        return low;
    }

    private static boolean calTime(int[] piles, int speed, int h) {
        int time = 0;
        for (int pile : piles) {
            time = time + pile / speed + (pile % speed == 0 ? 0 : 1);
            if (time > h) return true;
        }
        return false;
    }
}
