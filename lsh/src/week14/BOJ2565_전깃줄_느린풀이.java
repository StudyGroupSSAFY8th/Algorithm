package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author : sh Lee
 * @date : 23. 6. 8.
 */
public class BOJ2565_전깃줄_느린풀이 {


    //전기줄 정보를 저장할 객체
    static class Connect implements Comparable<Connect>{
        int a,b;

        Connect(int a, int b){
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Connect o) {
            return this.a - o.a;
        }
    }

    //주어진 배열에서 최대값 구하기
    static int maxValue(int[] dp){

        int result = 0;

        for(int i = 1; i < dp.length; i++){
            result = Math.max(result, dp[i]);
        }

        return result;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int n = Integer.parseInt(br.readLine()); //전기줄의 개수

        //전기줄 상태를 저장할 배열
        Connect[] connectInfoArray = new Connect[n + 1];

        //최대 연결 전깃줄 수를 저장할 dp
        int[] dp = new int[n + 1];

        //초기값 - 정렬을 위해
        connectInfoArray[0] = new Connect(0,0);

       for(int i = 1; i <= n; i++){
            st = new StringTokenizer(br.readLine());

            //전기줄 정보 받아서 저장
            connectInfoArray[i] = new Connect(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

       //A를 기준으로 정렬.
        Arrays.sort(connectInfoArray);

        //A는 인덱스에 순차적으로 접근하면 오름차순으로 되어있기 때문에 겹치지 않음, B만 오름차순으로 정렬되면 문제 없음
        //즉, B가 오름차순이 되는 최대 길이를 구하면 됨 -> 최장 증가 수열(LIS)
        //B가 오름차순이 되는 최대 길이를 구하고, 전체 전깃줄 수에서 빼면, 없애야 되는 전깃줄의 최소개수가 된다.

        //초기 값
        dp[0] = 1;

        for(int i = 1; i <= n; i++){

            int temp = 0;

            //i번째 값부터 이전에 있는 값들을 확인하면서, i번째보다 작은 값들 중에서 길이가 최대인 값을 가져옴
            for(int j = 1; j < i; j++){

                if(connectInfoArray[j].b < connectInfoArray[i].b) temp = Math.max(temp, dp[j]);
            }

            dp[i] = temp + 1;

        }

        //없애야 되는 전기줄은 (최대 갯수 - 연결할 전기줄)
        System.out.println(n - maxValue(dp));
    }
}
