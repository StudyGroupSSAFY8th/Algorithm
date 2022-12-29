import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class Solution {
    
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        int[] arrivals = new int[n];
        for (int i = 0; i < n; i++)
            arrivals[i] = 540 + (t * i);

        PriorityQueue<Integer> crews = new PriorityQueue<>();
        crews.addAll(Arrays.stream(timetable).map(this::toMinute).collect(Collectors.toList()));

        int last = 540;
        for (int i = 0; i < n; i++) {
            if (i == n - 1)
                m -= 1;

            for (int j = 0; j < m; j++) {
                if (!crews.isEmpty() && crews.peek() <= arrivals[i]) {
                    last = crews.poll();
                }
            }
        }

        if (crews.isEmpty()) {
            answer = toStringTime(arrivals[n - 1]);
        } else {
            if (crews.peek() <= arrivals[n - 1])
                answer = last == crews.peek() ? toStringTime(last - 1) : toStringTime(crews.peek() - 1);
            else answer = toStringTime(arrivals[n - 1]);
        }

        return answer;
    }

    private int toMinute(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }

    private String toStringTime(int minute) {
        int h = minute / 60;
        minute %= 60;

        return String.format("%02d:%02d", h, minute);
    }
}