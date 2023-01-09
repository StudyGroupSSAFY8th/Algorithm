class Solution {
    
    private static final int P = 1000000007;

    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n + 1][m + 1];
        for (int[] puddle : puddles) {
            dp[puddle[1]][puddle[0]] = -1;
        }

        dp[1][1] = 1;
        for (int y = 1; y <= n; y++) {
            for (int x = 1; x <= m; x++) {
                if (dp[y][x] == -1) continue;

                if (dp[y - 1][x] != -1) {
                    dp[y][x] = (dp[y][x] + dp[y - 1][x]) % P;
                }

                if (dp[y][x - 1] != -1) {
                    dp[y][x] = (dp[y][x] + dp[y][x - 1]) % P;
                }
            }
        }

        return dp[n][m];
    }
}