import java.util.PriorityQueue;

class Solution {
    
    private class Bridge implements Comparable<Bridge> {
        int node1;
        int node2;
        int weight;

        public Bridge(int node1, int node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Bridge o) {
            return weight - o.weight;
        }
    }

    public int solution(int n, int[][] costs) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        PriorityQueue<Bridge> pq = new PriorityQueue<>();
        for (int[] cost : costs) {
            pq.add(new Bridge(cost[0], cost[1], cost[2]));
        }

        int totalCost = 0;
        while (!pq.isEmpty()) {
            totalCost += union(parent, pq.poll());
        }

        return totalCost;
    }

    private int findParent(int[] parent, int target) {
        if (parent[target] != target) {
            parent[target] = findParent(parent, parent[target]);
        }

        return parent[target];
    }

    private int union(int[] parent, Bridge bridge) {
        int p1 = findParent(parent, bridge.node1);
        int p2 = findParent(parent, bridge.node2);

        if (p1 != p2) {
            if (p1 > p2) {
                parent[p1] = p2;
            } else parent[p2] = p1;
            
            return bridge.weight;
        }

        return 0;
    }
}