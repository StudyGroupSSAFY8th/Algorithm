package week07;

/**
 * @author : sh Lee
 * @date : 23. 1. 13.
 */
import java.util.Arrays;

/**
 * 아이디어
 * 1. 일반적인 dp
 * 2. 위에부터 내려가면서 값을 누적시킴.
 * 3. 아래에 누적할때는 합이 더 클떄만 저장함
 * 4. 맨 왼쪽, 맨 오른쪽 두가지 예외를 분기해서 처리함.
 */
public class Programmers_정수_삼각형 {
    public int solution(int[][] triangle) {
        int answer = 0;

        int N = triangle.length;

        int[][] dp = new int[N][N];
        for(int i = 0; i < N; i++){
            Arrays.fill(dp[i], -1);
        }

        // 맨 위와, 그 다음 값은 미리 계산해둠
        dp[0][0] = triangle[0][0];
        dp[1][0] = triangle[1][0] + dp[0][0];
        dp[1][1] = triangle[1][1] + dp[0][0];

        for(int i = 1; i < N-1; i++){
            for(int j = 1; j < N-1; j++){

                //왼쪽 끝일경우
                if(j == 0){
                    dp[i+1][j] = triangle[i+1][j] + dp[i][j];
                    dp[i+1][j+1] = Math.max(triangle[i+1][j+1] + dp[i][j], dp[i+1][j+1]);
                }
                // 오른쪽 끝일 경우.
                else if(triangle[i][j+1] == -1){
                    dp[i+1][j] = Math.max(triangle[i+1][j] + dp[i][j], dp[i+1][j]);
                    dp[i+1][j+1] = triangle[i+1][j+1] + dp[i][j];
                }
                else{
                    dp[i+1][j] = Math.max(triangle[i+1][j] + dp[i][j],dp[i+1][j]);
                    dp[i+1][j+1] = Math.max(triangle[i+1][j] + dp[i][j],dp[i+1][j+1]);
                }
            }
        }
        int result = Integer.MIN_VALUE;

        for(int i = 0; i < N; i++){
            result = Math.max(result, dp[N-1][i]);
        }

        return result;
    }

}

