package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author : sh Lee
 * @date : 23. 6. 15.
 *참고해서 품
 * 아이디어
 * 1. 각 위치의 누적합을 구함
 * 2. 반복문을 돌면서 1이 아닌 값을 발견하면, 해당위치(i,j)에서 행,열 방향으로 x만큼 이동했을때의 위치까지의 합을 구함
 * 3. 기존에 구해둔 누적합을 이용해서 일부 구간의 부분합을 구할 수 있음
 * 4. 이렇게 구한 부분합과 X*X의 값이 같다면 해당 부분은 정사각형이라고 할 수 있음
 * 5. 값이 작다면 해당 부분은 정사각형이 되지 않음.
 *
 * 각 위치의 부분합(or 누적합)은 다음과 같다. =>  마지막에 dp[i - 1][j - 1]를 빼주는 이유는 앞서 두값(dp[i - 1][j], dp[i][j - 1])에서 중복이 발생하기 때문이다
 * dp[i][j] = maps[i][j] + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1]
 *
 * (i,j) ~ (i+x,j+x)의 부분합은 다음과 같이 구할 수 있다 => 마지막에 dp[i - 1][j - 1]를 더해주는 이휴는 앞서 두값(dp[i+x][j - 1],  dp[i - 1][j + x])에서 중복되게 빼주었기 때문에 더해주는 것이다.
 * area = dp[i+x][j+x] - dp[i+x][j - 1] - dp[i - 1][j + x] + dp[i - 1][j - 1]
 *
 */
public class BOJ1915_가장_큰_정사각형 {

    //누적합 배열을 만들 메서드
    static void makeDP(int n, int m, int[][] dp, int[][] maps){

        //이중 반복문을 돌면서 각 위치에 누적되는 합을 구함.
        for(int i = 1; i <= n ; i++){
            for(int j = 1; j <= m ; j++){
                dp[i][j] = maps[i][j] + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
            }
        }
    }

    //시작위치와 끝 위치를 받아서 해당 구간의 누적합을 구하고 체크후 최대 값 전달.
    static int totalMaxArea(int n, int m, int[][] dp, int[][] maps){

        int maxAreaValue = 0;

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){

                if(maps[i][j] == 0) continue;
                //1이면 +x만큼씩 증가한 좌표를 끝점으로 잡음.
                maxAreaValue = Math.max(maxAreaValue, maxArea(n, m, dp, i, j));
            }
        }

        return maxAreaValue;
    }

    //각 시작값 별 최대값 구하기.
    static int maxArea(int n , int m, int[][] dp, int startI, int startJ){

        int maxArea = 1;

        for(int rectangleLength = 1; startI + rectangleLength <= Math.min(n,m); rectangleLength++){

            if(startI + rectangleLength > n || startJ + rectangleLength > m) break;

            int tempPartSum = dp[startI+rectangleLength][startJ+rectangleLength] - dp[startI + rectangleLength][startJ - 1] - dp[startI - 1][startJ + rectangleLength] + dp[startI - 1][startJ - 1];

            //정사각형이 아니라면 패스
            if((rectangleLength + 1) * (rectangleLength + 1) > tempPartSum) continue;


            maxArea = Math.max(maxArea, (rectangleLength + 1) * (rectangleLength + 1));
        }

        return maxArea;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        //누적합, 부분합을 위해서 값을 하나 크게 함.
        int[][] maps = new int[n+1][m+1];

        for(int i = 1; i <= n ; i++){

            char[] temp = br.readLine().toCharArray();
            for(int j = 1; j <= m; j++){
                maps[i][j] = Character.getNumericValue(temp[j - 1]);
            }
        }

        //누적합을 저장하고 있을 배열
        int[][] dp = new int[n+1][m+1];

        //dp배열 채우기
        makeDP(n,m,dp,maps);

        //최대 넓이 구하는 메서드
        System.out.println(totalMaxArea(n, m, dp, maps));
    }
}
