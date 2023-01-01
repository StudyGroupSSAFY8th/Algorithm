class Solution {
    
    public int solution(int sticker[]) {
        int n = sticker.length;

        if (n == 1) return sticker[0];

        int[] dp = new int[n];
        dp[0] = sticker[0];
        dp[1] = sticker[0];
        for (int i = 2; i < n - 1; i++) {
            dp[i] = Math.max(dp[i - 2] + sticker[i], dp[i - 1]);
        }

        int ans = dp[n - 2];

        dp[0] = 0;
        dp[1] = sticker[1];
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + sticker[i], dp[i - 1]);
        }

        ans = Math.max(ans, dp[n - 1]);
        return ans;
    }
}