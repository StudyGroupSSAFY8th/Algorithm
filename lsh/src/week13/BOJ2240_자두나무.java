package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 2차원 배열을 이용해서 풀이
 * 1.각 초마다 자두가 움직인 횟수에 따른 자두개수를 저장하는 식으로 계산
 * 2.자두가 움직인 횟수가 짝수면 그대로 1번, 홀수면 2번이므로 움직인 횟수만 있어도 위치를 알 수 있음.
 * 3. 2차원 배열을 만들고, 행은 움직인횟수, 열은 시간으로 해서 최대 개수를 구한다.
 */
public class BOJ2240_자두나무  {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());

		//행은 움직인 횟수, 열은 시간
		int dp[][] = new int[W + 1][T + 1];


		//자두가 떨어지는 나무 저장.
		int[] location = new int[T + 1];
		for(int i = 1; i <= T; i++){
			location[i] = Integer.parseInt(br.readLine());

			//dp 초기 값인 움직인 횟수가 0일때,저장
			if(location[i] == 1) dp[0][i] = dp[0][i - 1] + 1;
		}

		for(int i = 1; i <= W; i++){

			for(int j = 1; j <= T; j++){

				//현재 위치가 1일때
				if(i % 2 == 0){
					//1번나무에서 자두 떨어질때 - 이전시간에 이전 움직임에서 받은 자두와, 움직인 위치에서 받게 될 자두 비교
					if(location[j] == 1) dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i][j - 1] + 1);

					//2번나무에서 자두 떨어질때.
					else dp[i][j] = Math.max(dp[i - 1][j - 1] + 1, dp[i][j - 1]);
				}
				//현재위치가 2일때
				else{
					//1번나무에서 자두 떨어질때
					if(location[j] == 1) dp[i][j] = Math.max(dp[i - 1][j - 1] + 1, dp[i][j - 1]);

					//2번나무에서 자두 떨어질때.
					else dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i][j - 1] + 1);
				}

			}
		}

		int maxCount = 0;
		for(int i = 0; i <= W; i++){
			maxCount = Math.max(maxCount, dp[i][T]);
		}

		System.out.println(maxCount);

	}
}
