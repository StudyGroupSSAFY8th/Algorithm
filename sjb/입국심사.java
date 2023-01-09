class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        //시간을 기준으로 이분법
        long left = 0;
        long right = 10000000000000000L;
        long mid = 0;
        
        while(true){
            if(left>right){
                break;
            }
            mid = (long)Math.floor((double)left+right)/2;
            
            //해당 시간동안 심사를 하는 인원
            long cnt =0;
            for(int i=0;i<times.length;i++){
                cnt+=(long)Math.floor((double)mid/times[i]);
            }
            
            if(cnt<n){
                left = mid+1;
            }else{
                right = mid-1;
            }
            
        }
        
        
        answer = left;
        return answer;
    }
}