import java.io.*;
import java.util.*;

//2240
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());
		
		//W번 움직인다.
		int[] arr = new int[T+1];
		for(int i=1;i<=T;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		//
		//dp[자두나무위치][떨어지는시간][최대움직이는 횟수]
		
		//1에 떨어지면
		//dp[1][T][W] = max (1그대로인경우, 2에서 이동한경우) 
		//= max(dp[1][T-1][W]+1, dp[2][T-1][W-1]+1)
		//2에 떨어지면
		//dp[2][T][W] = max(2그대로인경우, 1에서 이동한 경우)
		//= max(dp[2][T-1][W]+1,dp[1][T-1][W-1]+1)
		int dp[][][] = new int[3][T+2][W+2];
		//전체 한바퀴돈다.
		for(int i=1;i<=T;i++) {
			//W는 최대 이동 횟수
			//1이 실제로는 0이나 마찬가지인것
			for(int j=1;j<=W+1;j++) {
				if(arr[i]==1) {
					dp[1][i][j] = Math.max(dp[1][i - 1][j] + 1, dp[2][i - 1][j - 1] + 1);
		            dp[2][i][j] = Math.max(dp[1][i - 1][j - 1], dp[2][i - 1][j]);
				}else {
					if (i == 1 && j == 1) {
		                continue;
		            }
					dp[1][i][j] = Math.max(dp[2][i - 1][j - 1], dp[1][i - 1][j]);
		            dp[2][i][j] = Math.max(dp[1][i - 1][j - 1] + 1, dp[2][i - 1][j] + 1);
				}
			}
		}
		
		System.out.println(Math.max(dp[1][T][W+1],dp[2][T][W+1]));
		
	}

}
