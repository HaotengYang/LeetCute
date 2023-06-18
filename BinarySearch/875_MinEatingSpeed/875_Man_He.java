public class S1_875 {
    public int minEatingSpeed(int[] piles, int h) {
        //每小时吃香蕉的速度边界，最小是每小时吃1根，最大是香蕉堆里的最大元素
        int minSpeed = 1;
        int maxSpeed = 1;
        for (int pile : piles) {
            maxSpeed = Math.max(maxSpeed, pile);
        }

        while (minSpeed < maxSpeed) {
            int midSpeed = (minSpeed + maxSpeed) >>> 2;
            int timeSum = 0;
            for (int pile : piles) {
                //遍历香蕉堆，得到midSpeed下吃香蕉的总时间
                timeSum += pile % midSpeed > 0 ? pile / midSpeed + 1 : pile / midSpeed;
            }
            if(timeSum > h){
                minSpeed = midSpeed + 1;
            }else {
                maxSpeed = midSpeed;
            }
        }
        return minSpeed;
    }

}
