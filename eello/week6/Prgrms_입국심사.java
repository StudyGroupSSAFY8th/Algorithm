public class Solution {
    
    public long solution(int n, int[] times) {
        int max = 0;
        for (int i = 0; i < times.length; i++) {
            if (max < times[i]) {
                max = times[i];
            }
        }
        
        long start = 1;
        long end = (long) max * n;

        long minTime = end;
        while (start <= end) {
            long mid = (start + end) / 2;

            if (inspect(times, mid) < n) {
                start = mid + 1;
            } else {
                minTime = mid;
                end = mid - 1;
            }
        }

        return minTime;
    }

    private long inspect(int[] times, long endTime) {
        long count = 0;
        for (int time : times) {
            count += endTime / time;
        }

        return count;
    }
}