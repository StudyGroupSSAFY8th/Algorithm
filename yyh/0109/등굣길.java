package 알고리즘.dfs_bfs;

public class 등굣길 {

	private int[] dr = { 1, 0 };
	private int[] dc = { 0, 1 };
	private int M, N;
	private int[][] map;
	private int answer = 0;

	public int solution(int m, int n, int[][] puddles) {

		M = m;
		N = n;
		map = new int[M][N];

		for (int i = 0; i < puddles.length; i++) {
			map[puddles[i][0] - 1][puddles[i][1] - 1] = 1;
		}

		dfs(0, 0);

		return answer % 1000000007;
	}

	private void dfs(int r, int c) {
		if (r == M - 1 && c == N - 1) {
			answer++;
			return;
		}

		for (int d = 0; d < 2; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];

			if (isChecked(nr, nc) && map[nr][nc] != 1) {
				dfs(nr, nc);
			}
		}
	}

	private boolean isChecked(int nr, int nc) {
		if (nr >= 0 && nr < M && nc >= 0 && nc < N) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		등굣길 sol = new 등굣길();
		int ans = sol.solution(4, 3, new int[][] { { 2, 2 } });
		System.out.println(ans);
	}
}
