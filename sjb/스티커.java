import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());

        for(int tc=1;tc<=T;tc++){
            int answer = 0;
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int[][] map = new int[2][n];
            for(int i=0;i<2;i++){
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<n;j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int dp[][] = new int[2][n];
            dp[0][0] = map[0][0];
            dp[1][0] = map[1][0];
            
            for(int j=1;j<n;j++){
                for(int i=0;i<2;i++){
                    //i,j = max(j-1 같은줄이 아닌, j-2 같은 줄이 아닌)
                    if(j-2>=0){
                        dp[i][j] = Math.max(dp[(i+1)%2][j-1],dp[(i+1)%2][j-2])+map[i][j];
                    }else{
                        dp[i][j] = map[i][j] + dp[(i+1)%2][j-1];
                    }
                }
            }

            System.out.println(Math.max(dp[1][n-1],dp[0][n-1]));
        }
    }
}
