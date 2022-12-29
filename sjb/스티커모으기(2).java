class Solution {
    public int solution(int sticker[]) {
        int answer = 0;

        
        
        if(sticker.length==1){
            return sticker[0];
        }
        //시작을 뜯었을때
        int[] dp1 = new int[sticker.length];
        System.arraycopy(sticker, 0, dp1, 0, dp1.length);
        
        int max1=0;
        dp1[0]=sticker[0];
        dp1[1]=dp1[0];
        for(int i=2;i<sticker.length-1;i++){
            dp1[i]=dp(dp1,i);
        }
        for(int i=0;i<sticker.length;i++){
            if(max1<dp1[i]){
               max1=dp1[i]; 
            }
        }
        

        //시작을 안뜯었을때
        int[] dp2 = new int[sticker.length];
        System.arraycopy(sticker, 0, dp2, 0, dp1.length);
        int max2=0;
        dp2[0]=0;
        dp2[1]=sticker[1];
        for(int i=2;i<sticker.length;i++){
            dp2[i]=dp(dp2,i);
        }
        for(int i=0;i<sticker.length;i++){
            if(max2<dp2[i]){
               max2=dp2[i]; 
            }
        }
        
        answer = Math.max(max1,max2);

        return answer;

    }
    public int dp(int sticker[], int i){
        return Math.max(sticker[i-1],sticker[i-2]+sticker[i]);
    }
}
