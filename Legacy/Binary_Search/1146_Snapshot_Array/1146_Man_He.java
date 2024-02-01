import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class S1_1146 {
    class SnapshotArray {
        int snap_id = 0;
        Map<Integer, TreeMap<Integer, Integer>> memo = new HashMap<>();

        public SnapshotArray(int length) {
            // 使用map数据结构不需要初始化长度
        }

        public void set(int index, int val) {
            TreeMap<Integer, Integer> map = new TreeMap<>();
            map.put(snap_id, val);
            memo.put(index, map);
        }

        public int snap() {
            //根据题目提示snap_id为调用snap()的次数
            return snap_id++;
        }

        public int get(int index, int snap_id) {
            TreeMap<Integer, Integer> map = memo.get(index);
            if (map == null) return 0;
            if (map.containsKey(snap_id))
                return map.get(snap_id);
            else {
                Integer key = map.lowerKey(snap_id);
                if (key == null) return 0;
                return map.get(key);
            }
        }
    }
}
