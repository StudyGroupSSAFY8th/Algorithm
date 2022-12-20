import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    
    private int n, m;
    private Deque<Deque<Integer>> center;
    private Deque<Integer> left, right;

    public int[][] solution(int[][] rc, String[] operations) {
        init(rc);

        for (String operation : operations) {
            if (operation.equals("ShiftRow")) {
                shiftRow();
            } else rotate();
        }

        for (int i = 0; i < n; i++) {
            Deque<Integer> row = center.pollFirst();
            for (int j = 0; j < m; j++) {
                if (j == 0) {
                    rc[i][j] = this.left.pollFirst();
                } else if (j == m - 1) {
                    rc[i][j] = this.right.pollFirst();
                } else {
                    rc[i][j] = row.pollFirst();
                }
            }
        }
        
        return rc;
    }

    private void init(int[][] rc) {
        this.n = rc.length;
        this.m = rc[0].length;

        this.center = new ArrayDeque<>();
        this.left = new ArrayDeque<>();
        this.right = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            Deque<Integer> row = new ArrayDeque<>();
            for (int j = 0; j < m; j++) {
                int elem = rc[i][j];
                if (j == 0) {
                    this.left.addLast(elem);
                } else if (j == m - 1) {
                    this.right.addLast(elem);
                } else {
                    row.add(elem);
                }
            }
            this.center.addLast(row);
        }
    }

    private void shiftRow() {
        this.center.addFirst(this.center.pollLast());
        this.left.addFirst(this.left.pollLast());
        this.right.addFirst(this.right.pollLast());
    }

    private void rotate() {
        this.center.getFirst().addFirst(left.pollFirst());
        this.right.addFirst(this.center.getFirst().pollLast());
        this.center.getLast().addLast(this.right.pollLast());
        this.left.addLast(this.center.getLast().pollFirst());
    }
}