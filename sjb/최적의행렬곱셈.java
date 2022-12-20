import java.util.*;
class Solution {
    public int solution(int[][] matrix_sizes) {
        int answer = 0;
        
        
        int[][] dp = new int[matrix_sizes.length][matrix_sizes.length];
        //초기화
        for(int i=0;i<dp.length;i++){
            Arrays.fill(dp[i],40000000);
        }
        
        for(int i=0;i<dp.length;i++){
            dp[i][i] = 0;
        }
        
        
        for(int step=0;step<dp.length;step++){
            for(int start=0;start<dp.length-step;start++){
                int a = start;
                int b = start+step;
                for(int k=0;k<b;k++){
                    dp[a][b] = Math.min(dp[a][b],dp[a][k]+dp[k+1][b] + (matrix_sizes[a][0]*matrix_sizes[k][1]*matrix_sizes[b][1]));
                }
                
            }
        }
        
        // for(int i=0;i<dp.length;i++){
        //     System.out.println(Arrays.toString(dp[i]));
        // }
        answer = dp[0][dp.length-1];
        return answer;
    }
}