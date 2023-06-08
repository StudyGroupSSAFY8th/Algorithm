import java.io.*;
import java.util.*;

public class 전깃줄 {
    static class Line{
        int index;
        int a,b;
        int cross;
        Set<Integer> set = new HashSet<>();
        Line(int index, int a, int b){
            this.index = index;
            this.a = a;
            this.b = b;
        }
        
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        int answer = 0;
        Line[] list = new Line[n];
        for(int i=0;i<n;i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list[i] = new Line(i,a,b);
        }

        //그리디
            //가장 많은 교차지점 우선으로 삭제 - 안됨
        //그리디2
            //각 선과 안겹치는 선들을 카운트
            //제일 큰것을 찾으면 n-카운트로 정답
            //- 안겹친 녀석들이 교차된건지 알수 없음
        //dp
            //n번선에서 뒤녀석들중 겹치지 않은 최대 선들 갯수
        //dp 2
            //뒤녀석중에 교차되지 않은 최대 선들 갯수 + 1로 갱신한다.
            //dp[i]는 해당 줄과 겹치지 않는 선들들
        //dp 3
            //a,b순으로 정렬하고 진행
            //a를 기준으로 이전 선들에 대해서 누적된 안겹치는 선갯수+1을 max로 저장한ㄷ.
        int[] dp = new int[n];
        Arrays.sort(list,(a,b)->{
            if(a.a==b.a){
                return a.b-b.b;
            }
            return a.a-b.a;
        });
        int max = 0;
        for(int i=0;i<n;i++){
            dp[i] = 1;
            for(int j=0;j<i;j++){
                if(list[i].b>list[j].b ){
                    dp[i] = Math.max(dp[i],dp[j] +1);
                }
                max = Math.max(dp[i],max);
            }
            //System.out.println(dp[i]);
        }
        System.out.println(n-max);
    }
}

/*
10
1 6
2 8
3 2
4 9
5 5
6 10
7 4
8 1
9 7
10 3

answer 6
 */
