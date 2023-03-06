import java.util.*;
class Solution {
    public int solution(int[] a) {
        int answer = 0;
        //임의의 두 풍선중 하나 터뜨리기
        //빈공간은 중앙으로 밀착
        
        //인접한 두풍선은 번호가 큰거먼저
            //작은건 1번만
        
        //기준 번호를 기준으로 양옆으로 자른다.
            //양옆의 영역의 최대값을 구한다.
            //좌,우 양쪽다 기준번호보다 크다면
        
        int[][] dp = new int[2][a.length];
        //0은 왼쪽에서 시작해서 제일 작은값을 넣는다.
        dp[0][0] = a[0];
        for(int i=1;i<a.length;i++){
            dp[0][i] = dp[0][i-1]>a[i] ? a[i]:dp[0][i-1];
        }
        
        dp[1][a.length-1] = a[a.length-1];
        for(int i=a.length-2;i>=0;i--){
            dp[1][i] = dp[1][i+1]>a[i] ? a[i]:dp[1][i+1];
        }
    
        
        
        for(int i=0;i<a.length;i++){
            //i는 기준번호
            //왼쪽 최소값
            int leftMin = 1000000001;
            int rightMin = 1000000001;
            boolean leftCheck = false;
            boolean rightCheck = false;
            if(i-1>=0){
                leftMin = dp[0][i-1];
                if(leftMin<a[i]){
                    leftCheck = true;
                }
            }
            //오른족 최소값
            if(i+1<a.length){
                rightMin = dp[1][i+1];
                if(rightMin<a[i]){
                    rightCheck = true;
                }
            }
            
            if(leftCheck&&rightCheck){
                continue;
            }else{
                answer+=1;
            }
            
        }
        return answer;
    }
}