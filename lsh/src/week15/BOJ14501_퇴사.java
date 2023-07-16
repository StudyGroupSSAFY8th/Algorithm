package week15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ14501_퇴사 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        int[] timeArray = new int[n+1];
        int[] moneyArray = new int[n+1];
        int[] dp = new int[n+16];

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());
            timeArray[i] = Integer.parseInt(st.nextToken());
            moneyArray[i] = Integer.parseInt(st.nextToken());
        }

        int max = 0;

        for(int i = 1; i <= n; i++){


            dp[i] = Math.max(dp[i], max);
            dp[i + timeArray[i]] = Math.max(dp[i + timeArray[i]], dp[i] + moneyArray[i]);

            max = Math.max(dp[i], max);

        }


        System.out.println(Math.max(max,dp[n+1]));
    }
}
