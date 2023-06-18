package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author : sh Lee
 * @date : 23. 6. 5.
 *
 * 아이디어
 * 0행 i열 위치는 1행 i-1위치까지 누적된 값에 (0행 i열)을 추가한 값이거나, 1행 i-2까지 누적된 값에 (0행 i열)을 추가한 값이 점수가 된다.
 * 맞닿은 변은 스티커를 뜯을 수 없기 때문에 같은 행이라면 바로 이전열의 스티커와 같은 열의 다른 행의 스티커는 뗄 수 없다.
 * 점화식 : 행 == 0 -> dp[0][i] += Max(dp[1][i-1], dp[1][i-2])
 *         행 == 1 -> dp[1][i] = Max(dp[0][i-1], dp[0][i-2])
 *

 */
public class BOJ9465_스티커 {

    //최대값 찾아서 반환
    static int maxScore(int[][] stickerArray){
        return Math.max(stickerArray[0][stickerArray[0].length - 1], stickerArray[1][stickerArray[0].length - 1]);
    }

    //디비 배열 누적 - 기존 스티커 점수 있는 배열에 점화식을 이용해서 점수 누적
    static void makeDp(int[][] stickerArray){

        for(int i = 2; i <= stickerArray[0].length - 1; i++){

            stickerArray[0][i] += Math.max(stickerArray[1][i - 1], stickerArray[1][i - 2]);
            stickerArray[1][i] += Math.max(stickerArray[0][i - 1], stickerArray[0][i - 2]);
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int T = Integer.parseInt(br.readLine());

        //최종 정답 출력
        StringBuilder result = new StringBuilder();

        for(int testCase = 0; testCase < T; testCase++){
            int n = Integer.parseInt(br.readLine());
            int[][] stickerArray = new int[2][n + 1];

            //스티커 값 입력
            for(int i = 0; i < 2; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 1; j <= n; j++){
                    stickerArray[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            //dp배열 만들기
            makeDp(stickerArray);

            //정답 스트링 빌더에 누적
            result.append(maxScore(stickerArray)).append("\n");
        }

        System.out.println(result);
    }
}
