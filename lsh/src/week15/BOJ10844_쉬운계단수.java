package week15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 아이디어
 * 2차원 배열을 이용해서 해결
 * dp[i][j] => i번째 자리수가 j인경우
 * 예를들어 dp[3][6] 이라면 dp[2][5] + dp[2][7]이다. 이전 자리수가 하나 크거나 하나 작은 경우를 모두 더해주면 된다.
 * 주의할 점은 9일때와 0일때이다.
 * 이런식으로 해결하면 이전에 구해둔 값을 이용해서 해결 할 수 있다.
 */
public class BOJ10844_쉬운계단수 {

    static final int MOD_VALUE = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] dp = new int[n+1][10];

        for(int i = 1; i <= 9; i++){
            dp[1][i] = 1;
        }

        for(int i = 2; i <= n; i++){

            for(int j = 0; j <= 9; j++){

                //해당 자리수가 0일때,
                if(j == 0){
                    dp[i][j] += dp[i-1][1] % MOD_VALUE;
                }
                //9일때
                else if(j == 9){
                    dp[i][j] += dp[i-1][8] % MOD_VALUE;
                }
                //나머지 경우에 대해서
                else{
                    dp[i][j] += (dp[i-1][j-1] + dp[i-1][j+1]) % MOD_VALUE;
                }
            }
        }

        long total = 0;

        for(int i = 0; i <= 9; i++){
            total += dp[n][i];
        }

        System.out.println(total % MOD_VALUE);
    }

}
