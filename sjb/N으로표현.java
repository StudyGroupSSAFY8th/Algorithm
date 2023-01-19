import java.util.*;
class Solution {
    public void cal(HashSet<Integer> save,HashSet<Integer> aSet, HashSet<Integer> bSet){
        
        for(int a: aSet){
            for(int b: bSet){
                
                save.add(a+b);
                if(a-b>0){
                    save.add(a-b);
                }
                
                if(b-a>0){
                    save.add(b-a);
                }
                
                save.add(a*b);
                save.add((int)Math.floor((double)a/b));
                save.add((int)Math.floor((double)b/a));
            }
        }
        
    }
    public int solution(int N, int number) {
        int answer = 0;
        
        if(N==number) return 1;
        HashSet<Integer>[] save = new HashSet[9];
        //초기화
        for(int i=0;i<9;i++){
            save[i] = new HashSet<>();
        }
        
        save[1].add(N);
        
        for(int i=2;i<9;i++){
            int tmp = 0;
            for(int j=0;j<i;j++){
                tmp = tmp*10+N;
            }
            save[i].add(tmp);
            
            for(int t=1;t<i;t++){
                cal(save[i],save[t],save[i-t]);
            }
            
            if(save[i].contains(number)){
                return i;
            }
        }
        
        return -1;
    }
}