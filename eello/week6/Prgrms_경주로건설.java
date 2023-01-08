import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    
    private static final int U = 0, R = 1, D = 2, L = 3;

    private static class Pos {
        int y;
        int x;
        int d;

        public Pos(int y, int x, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }
    }

    public int solution(int[][] board) {
        int n = board.length;
        int[][][] costs = new int[4][n][n];
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < n; i++) {
                Arrays.fill(costs[d][i], Integer.MAX_VALUE);
            }
        }

        Queue<Pos> q = new ArrayDeque<>();
        if (board[0][1] == 0) {
            costs[L][0][1] = 100;
            q.add(new Pos(0, 1, L));
        }
        if (board[1][0] == 0) {
            costs[D][1][0] = 100;
            q.add(new Pos(1, 0, D));
        }

        int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int d = 0; d < 4; d++) {
            costs[d][0][0] = 0;
        }

        while (!q.isEmpty()) {
            Pos cur = q.poll();

            for (int d = 0; d < 4; d++) {
                int ny = cur.y + dir[d][0];
                int nx = cur.x + dir[d][1];

                if (checkRange(board, ny, nx) && board[ny][nx] == 0
                        && costs[cur.d][cur.y][cur.x] + getBuildCost(cur.d, d) < costs[d][ny][nx]) {
                    costs[d][ny][nx] = costs[cur.d][cur.y][cur.x] + getBuildCost(cur.d, d);
                    q.add(new Pos(ny, nx, d));
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int d = 0; d < 4; d++) {
            ans = Math.min(ans, costs[d][n - 1][n - 1]);
        }
        return ans;
    }

    private boolean checkRange(int[][] board, int y, int x) {
        int len = board.length;
        return 0 <= y && y < len && 0 <= x && x < len;
    }

    private int getBuildCost(int d1, int d2) {
        int cost = 100;
        if (d1 % 2 != d2 % 2)
            cost += 500;
        return cost;
    }
}