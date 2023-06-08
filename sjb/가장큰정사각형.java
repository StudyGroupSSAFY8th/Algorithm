import java.io.*;
import java.util.*;

class Main{

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        int[][] map = new int[h][w];
        for(int i=0;i<h;i++){
            String line = br.readLine();
            for(int j=0;j<w;j++){
                map[i][j] = line.charAt(j)-'0';
            }
        }

        int[][] dp = new int[h][w];

        int max = 0;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                if(i==0){
                    if(map[i][j]==1){
                        dp[i][j] = 1;
                    }
                    max = Math.max(max,dp[i][j]);
                    continue;
                }
                if(j==0){
                    if(map[i][j]==1){
                        dp[i][j] = 1;
                    }
                    max = Math.max(max,dp[i][j]);
                    continue;
                }

                if(map[i][j]==1){
                    dp[i][j] = Math.min(Math.min(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1])+1;
                }
                max = Math.max(max,dp[i][j]);
            }
        }

        System.out.println(max*max);
    }
}