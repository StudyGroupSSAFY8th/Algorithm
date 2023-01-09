import java.util.*;
class Solution {
    public int[] solution(String[] operations) {
        
        
        ArrayList<Integer> que = new ArrayList<Integer>();
        
        for(int i=0;i<operations.length;i++){
            String[] opt = operations[i].split(" ");
            if(opt[0].equals("I")){
                que.add(Integer.parseInt(opt[1]));
                //정렬
                Collections.sort(que);
            }else{
                if(que.size()==0){
                    continue;
                }
                //제거
                if(opt[1].equals("1")){
                    //최대값
                    que.remove(que.size()-1);
                }else{
                    que.remove(0);
                }
            }
        }
        
        int[] answer = {0,0};
        if(que.size()!=0){
            answer[0] = que.get(que.size()-1);
            answer[1] = que.get(0);
        }
        return answer;
    }
}