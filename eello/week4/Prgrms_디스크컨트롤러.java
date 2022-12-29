import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    private class Task {
        int reqTime;
        int time;

        public Task(int reqTime, int time) {
            this.reqTime = reqTime;
            this.time = time;
        }
    }

    public int solution(int[][] jobs) {
        List<Task> tasks = new ArrayList<>();
        for (int[] job : jobs)
            tasks.add(new Task(job[0], job[1]));

        PriorityQueue<Task> timePriority = new PriorityQueue<>(Comparator.comparingInt(o -> o.time));
        PriorityQueue<Task> reqTimePriority = new PriorityQueue<>(Comparator.comparingInt(o -> o.reqTime));

        reqTimePriority.addAll(tasks);

        int totalCompleteTime = 0;
        int time = 0;
        while (!reqTimePriority.isEmpty() || !timePriority.isEmpty()) {
            if (timePriority.isEmpty() && reqTimePriority.peek().reqTime > time) {
                time++;
                continue;
            }

            while (!reqTimePriority.isEmpty() && reqTimePriority.peek().reqTime <= time)
                timePriority.add(reqTimePriority.poll());

            Task task = timePriority.poll();
            time += task.time;
            totalCompleteTime += time - task.reqTime;
        }

        return totalCompleteTime / jobs.length;
    }
}