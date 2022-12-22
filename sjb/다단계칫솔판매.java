import java.util.*;
class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];
        
        //enroll은 판매원이름
        //referral은 enroll의 인원을 참여시킨 사람의 이름
            //-인사람은 위에 민호가 있음
            //referral은 enroll의 부모이름
        //판매는 개당 100원
        
        HashMap<String,String> getParent = new HashMap<>();
        HashMap<String,Integer> money = new HashMap<>();
        
        for(int i=0;i<enroll.length;i++){
            getParent.put(enroll[i],referral[i]);
            money.put(enroll[i],0);
        }
        
        
        for(int i=0;i<seller.length;i++){
            
            //-가 나올때까지 반복
            String cur = seller[i];
            int price = amount[i]*100;
            while(!cur.equals("-")){
                
                
                //현재 인원은 90프로만 
                int give = (int)Math.floor((double)price/10);
                
                if(price-give==0){
                    money.put(cur,money.get(cur)+price);
                    break;
                }
                money.put(cur,money.get(cur)+(price-give));
                
                //10미만이면 종료
                
                //위로 전달
                price=give;
                cur = getParent.get(cur);
            }
        }
        
        for(int i=0;i<answer.length;i++){
            answer[i] = money.get(enroll[i]);
        }
        
        
        
        
        return answer;
    }
}