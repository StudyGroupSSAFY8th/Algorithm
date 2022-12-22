import java.util.*;
class Solution {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;

        
        //빈것 찾기
        
        ArrayList<Integer> spaces = new ArrayList<>();
        int start = 1;
        //범위는 1부터 시작이다.
        for(int i=0;i<stations.length;i++){
            //좌우 범위찾기
            int left = stations[i]-w;
            int right = stations[i]+w;
            
            //start부터 left까지 빈것이다.
            
            if(left>start){
                //1이상 left미만
                //spaces.add(left-start);
                answer+= (int)Math.ceil((double)(left-start)/(w*2+1));
            }
            
            start = right+1;
            
            if(i==stations.length-1){
                //start에서 N까지 빈것 확인
                if(start<=n){
                    //spaces.add(n+1-start);
                    answer+= (int)Math.ceil((double)(n+1-start)/(w*2+1));
                }
            }
            
        }
        
        
        //space들을 채울수있는 기지국수를 카운트
        //for(int space : spaces){
            //올림으로 진행
            //answer+= (int)Math.ceil((double)space/(w*2+1));
        //}
        
        return answer;
    }
}