package 알고리즘.dfs_bfs;

import java.util.LinkedList;
import java.util.Queue;

public class 경주로_건설 {

	class Loc {
		int r, c, dir, cost;

		public Loc(int r, int c, int dir, int cost) {
			super();
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.cost = cost;
		}
	}

	private int length;

	public int solution(int[][] board) {
		int answer = 0;

		length = board.length;
		answer = bfs(board, 0, 0);

		return answer;
	}

	private int bfs(int[][] board, int r, int c) {
		int[] dr = { -1, 1, 0, 0 };
		int[] dc = { 0, 0, -1, 1 };
		boolean[][] visited = new boolean[length][length];

		Queue<Loc> Q = new LinkedList<>();
		// 초기값 -1 방향, 0원
		Q.offer(new Loc(r, c, -1, 0));
		visited[r][c] = true;

		int min = Integer.MAX_VALUE;

		while (!Q.isEmpty()) {
			Loc cur = Q.poll();

			if (cur.r == length - 1 && cur.c == length - 1) {
				min = Math.min(min, cur.cost);
			}

			for (int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				int cost = cur.cost;

				if (isChecked(nr, nc) && board[nr][nc] != 1) {
					// 직선 방향
					if (cur.dir == -1 || cur.dir == d) {
						cost += 100;
					}
					// 코너
					else {
						cost += 600;
					}

					// 이전에 들어있는 값이 더 크다면 작은 값으로 갱신해준다.
					if(!visited[nr][nc] || board[nr][nc] >= cost) {
						visited[nr][nc] = true;
						board[nr][nc] = cost;
						Q.offer(new Loc(nr, nc, d, cost));
					}
				}
			}
		}

		return min;
	}

	private boolean isChecked(int nr, int nc) {
		if (nr >= 0 && nr < length && nc >= 0 && nc < length) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		경주로_건설 sol = new 경주로_건설();
		int ans = sol.solution(new int[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } });
		System.out.println(ans);
	}
}
