class Solution {
    // AD, 93.4%, although obvious, TreeMap is actually an overkill
    static class SnapshotArrayTreeMap {
        int snapId = 0;
        // <index, <snapId, val>>
        Map<Integer, TreeMap<Integer, Integer>> map = new HashMap<>();

        public SnapshotArrayTreeMap(int length) {}

        public void set(int index, int val) { map.computeIfAbsent(index, key -> new TreeMap<>()).put(snapId, val); }

        public int snap() { return snapId++; }

        // Several possible cases:
        // 1. the index has never been set, its corresponding map will be null and should return 0
        // 2. the index has been set, and the there is at least one associated snapId <= the input snap id, the largest one of them will be return by treeMap.floorEntry(snap_id)
        // 3. the index has been set, but its smallest associated snapId > the input snap id, treeMap.floorEntry(snap_id) will return null, in which case the index didn't associate with any value at the moment of the input snap id
        public int get(int index, int snap_id) {
            TreeMap<Integer, Integer> treeMap = map.get(index);
            if (treeMap == null) return 0;
            Map.Entry<Integer, Integer> entry = treeMap.floorEntry(snap_id);
            return entry == null ? 0 : entry.getValue();
        }
    }

    static class Pair {
        int snapId;
        int value;
        public Pair(int s, int v) { snapId = s; value = v; }
    }

    // The reason why we can use binary search: the insertion of snapId is incremental,
    // thus it forms a sorted array/list that can be binary searched, which incurs less overhead than a TreeMap
    // AD, 97.81%
    static class SnapshotArrayBinarySearch {
        int snapId = 0;
        List<Pair>[] snapShotList;

        // We have several other alternatives:
        List<int[]>[] snapShotArray;
        Map<Integer, List<Pair>> snapShotListMap;
        Map<Integer, List<int[]>> snapShotArrayMap;

        public SnapshotArrayBinarySearch(int length) { snapShotList = new List[length]; }

        public void set(int index, int val) {
            List<Pair> list = snapShotList[index];
            if (list == null) {
                list = new ArrayList<>();
                snapShotList[index] = list;
                list.add(new Pair(snapId, val));
            } else {
                if (list.get(list.size() - 1).snapId == snapId) {
                    list.get(list.size() - 1).value = val;
                } else {
                    list.add(new Pair(snapId, val));
                }
            }
        }

        public int snap() { return snapId++; }

        public int get(int index, int snap_id) {
            List<Pair> list = snapShotList[index];
            if (list == null) return 0;

            // edge case, otherwise there must be an floor entry
            if (list.get(0).snapId > snap_id) return 0;

            // binary search for floor entry using the predicate of the upper section
            int low = 0;
            int high = list.size() - 1;
            while (low < high) {
                int mid = low + (high - low + 1) / 2;
                if (list.get(mid).snapId > snap_id) high = mid - 1;
                else low = mid;
            }

            return list.get(low).value;
        }
    }

    // AD, 99.79%, inspired by online best solution
    // The core idea is to store every copy of the snap shot but reuse the same object if two snap shot are identical
    // This approach works well when max is relatively smaller, but will be too space consuming when it has a large max
    // and is updated and taken snapshot for many times
    static class SnapshotArrayCache {
        int snapId = 0;
        int max = 0;
        List<int[]> snapShotArrayCache;
        int[] workingList;
        boolean outdated;

        public SnapshotArrayCache(int length) {
            workingList = new int[length];
            snapShotArrayCache = new ArrayList<>();
        }

        // We maintain the following invariant:
        // 1. if outdated=false, workingList is the same as the last snapshot in [0...max]
        // 2. if outdated=true, workingList could be different from the last snapshot
        public void set(int index, int val) {
            if (val == workingList[index]) return; // optional but improve to 39ms
            workingList[index] = val;
            max = Math.max(max, index + 1);
            outdated = true;
        }

        public int snap() {
            if (outdated) {
                snapShotArrayCache.add(Arrays.copyOf(workingList, max));
            } else {
                if (snapId == 0) snapShotArrayCache.add(new int[0]); // better than new int[workingList.length]
                else snapShotArrayCache.add(snapShotArrayCache.get(snapShotArrayCache.size() - 1));
            }
            outdated = false;
            return snapId++;
        }

        public int get(int index, int snap_id) {
            int[] snapShotArray = snapShotArrayCache.get(snap_id);
            if (index >= snapShotArray.length) return 0;
            else return snapShotArray[index];
        }
    }

    // AD, 100%, optimal with regard to the online testcases but not necessarily always good
    // This solution builds on SnapshotArrayCache but takes the idea further
    static class SnapshotArrayCacheOpt {
        int snapId = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // We adapt a different view of the cache int[0] is the offset from 0, which is exactly the current min
        List<int[]> snapShotArrayCache;
        int[] workingList;
        boolean outdated;

        public SnapshotArrayCacheOpt(int length) {
            workingList = new int[length];
            snapShotArrayCache = new ArrayList<>();
        }

        public void set(int index, int val) {
            workingList[index] = val;
            min = Math.min(min, index);
            max = Math.max(max, index);
            outdated = true;
        }

        public int snap() {
            if (outdated) {
                int[] snapShotArray = new int[max - min + 2];
                snapShotArray[0] = min;
                System.arraycopy(workingList, min, snapShotArray, 1, max - min + 1);
                snapShotArrayCache.add(snapShotArray);
            } else {
                if (snapId == 0) snapShotArrayCache.add(new int[1]);
                else snapShotArrayCache.add(snapShotArrayCache.get(snapShotArrayCache.size() - 1));
            }
            outdated = false;
            return snapId++;
        }

        public int get(int index, int snap_id) {
            int[] snapShotArray = snapShotArrayCache.get(snap_id);
            // less than min
            if (index < snapShotArray[0]) return 0;
            // 0, 1...length - 1, thus length-1-1+1=length-1 is the number of entries between min and max
            // min + length - 1 - 1 = max
            // thus max = snapShotArray[0] + snapShotArray.length - 2
            // larger than max
            if (index > snapShotArray[0] + snapShotArray.length - 2) return 0;
            else return snapShotArray[index - snapShotArray[0] + 1];
        }
    }
}
