import java.util.PriorityQueue;

class Solution {
    
     public int[] solution(String[] operations) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        int cnt = 0;
        for (String operation : operations) {
            String[] oper = operation.split(" ");
            int num = Integer.parseInt(oper[1]);
            if (oper[0].equals("I")) {
                minHeap.add(num);
                maxHeap.add(num);
                cnt++;
            } else {
                if (cnt != 0) {
                    if (num == -1) {
                        minHeap.poll();
                    } else maxHeap.poll();
                    cnt--;
                }
                
                if (cnt == 0) {
                    minHeap.clear();
                    maxHeap.clear();
                }
            }
        }

        if (cnt == 0) return new int[]{0, 0};
        else return new int[]{maxHeap.poll(), minHeap.poll()};
    }
}