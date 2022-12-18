import java.util.*;

//후보키의 최대 갯수
    //모든 컬럼의 조합을 구한다.
        //예외 학번, 이름 각각 후보키이면, 학번+주민은 만들어지면 안된다.
    //만들어진 후보키내에 조합에서 또다른 후보키가 있으면 안된다.
class Solution {
    int cnt = 0;    
    HashSet<String> set = new HashSet<String>();
    public int solution(String[][] relation) {
        int answer = 0;
        //컬럼은 8개
            //컬럼의 조합
        for(int size = 1;size<=relation[0].length;size++){
            combi(new int[size],relation,0,0);
        }
        answer = cnt;
        return answer;
    }
    
    
    public String arr2str(int[] columns,int end){
        String tmp=""+columns[0];
        for(int i=1;i<end;i++){
            tmp += "-"+columns[i];
        }
        return tmp;
    }
    
    public void combi(int[] sel, String[][] relation,int idx, int start){
        if(idx==sel.length){
            if(checkUniq(sel,relation)){
                set.add(arr2str(sel,sel.length));
                cnt+=1;
            }
            return;
        }
        for(int i=start;i<relation[0].length;i++){
            sel[idx] = i;
            if(checkMini(new int[idx+1],sel,0,0,idx+1)){
                combi(sel,relation,idx+1,i+1);
            }
        }
    }
    
    //집합에서 중복이 있는지 확인    
    public boolean checkMini(int[] sel, int[] columns,int idx, int start,int size){
        if(idx==sel.length){
            return true;
        }
        for(int i=start;i<size;i++){
            sel[idx] = columns[i];
            if(set.contains(arr2str(sel,idx+1))){
                return false;
            }
            if(!checkMini(sel,columns,idx+1,i+1,size)){
                return false;
            }
        }
        return true;
    }
    
    public boolean checkUniq(int[] columns,String[][] relation){
        HashSet<String> check = new HashSet<String>();
        
        for(int i=0;i<relation.length;i++){
            String row = "";
            for(int colIdx:columns){
                row += relation[i][colIdx];
            }
            
            if(check.contains(row)){
                return false;
            }
            check.add(row);
        }
        return true;
    }
}