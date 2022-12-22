/**
 * 연쇄행렬 최소곱셈 알고리즘 참고
 * https://source-sc.tistory.com/24
 */
class Solution {
    
    public int solution(int[][] matrix_sizes) {
        int n = matrix_sizes.length;
        int[][] dp = new int[n + 1][n + 1];

        for (int size = 0; size < n; size++) {
            for (int start = 0; start < n - size; start++) {
                int end = start + size;

                if (start == end) {
                    dp[start][end] = 0;
                } else {
                    dp[start][end] = Integer.MAX_VALUE;
                    for (int k = start; k <= end; k++) {
                        dp[start][end] = Math.min(dp[start][end], dp[start][k] + dp[k + 1][end] + matrix_sizes[start][0] * matrix_sizes[k][1] * matrix_sizes[end][1]);
                    }    
                }
            }
        }

        return dp[0][n - 1];
    }
}