class Solution {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        
        
        //각숫자를 bit로 만든다.
        //2^h-1로 bit를 왼쪽에서 채운다.
        //중앙부터, 왼쪽, 오른쪽 가면서 root가 0인데 자식이 1인 경우가 있으면 표현X
        
        for(int i=0;i<numbers.length;i++){
            String bit = Long.toBinaryString(numbers[i]);
            int bitcnt = bit.length();
            int check = 2;
            while((check-1)<bitcnt){
                check*=2;
            }
            check = (check-1)-bitcnt;
            
            StringBuilder sb = new StringBuilder();
            
            for(int s =0;s<check;s++){
                sb.append("0");
            }
            bit = sb.toString()+bit;
            
            if(goCheck(bit,0,bit.length()-1,true)){
                answer[i] = 1;
            }else{
                answer[i] = 0;
            }
            
        }
        
        return answer;
    }
    public static boolean goCheck(String bit, int left, int right, boolean root){
        
        if(left==right){
            if(!root){
                if(bit.charAt(left)=='1') return false;
            }
            return true; 
        }
        
        if(right-left==1){
            if(!goCheck(bit,left,left,root)){
                return false;
            }
            if(!goCheck(bit,right,right,root)){
                return false;
            }
            return true;
        }
        
        
        int mid = (left+right)/2;
        boolean nRoot = false;
        if(bit.charAt(mid)=='1'){
            nRoot = true;
        }else{
            nRoot = false;
        }
        
        if(!root&&nRoot){
             return false;
        }
        
        
        if(!goCheck(bit,left,mid-1,nRoot)){
            return false;
        }
        
        if(!goCheck(bit,mid+1,right,nRoot)){
            return false;
        }
        
        
        return true;
        
    }
}