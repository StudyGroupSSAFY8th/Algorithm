import java.util.*;
class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;
        
        int lockSize = lock.length;
        lock = expand(key.length-1,lock);
        
        for(int t=0;t<4;t++){//키 돌리는 횟수
            for(int i=0;i<lock.length-(key.length-1);i++){
                for(int j=0;j<lock.length-(key.length-1);j++){
                    //key삽입
                    for(int ki=0;ki<key.length;ki++){
                        for(int kj=0;kj<key.length;kj++){
                            lock[i+ki][j+kj]+=key[ki][kj];
                        }
                    }
                    
                    //lock확인 시작i,j
                    boolean keyCheck = true;
                    for(int li=key.length-1;li<key.length-1+lockSize;li++){
                        for(int lj=key.length-1;lj<key.length-1+lockSize;lj++){
                            if(lock[li][lj]!=1){
                                keyCheck=false;
                                break;
                            }
                        }
                        if(!keyCheck){
                            break;
                        }
                    }
                    
                    if(keyCheck){
                        return true;
                    }else{
                        //원상복구
                        for(int ki=0;ki<key.length;ki++){
                            for(int kj=0;kj<key.length;kj++){
                                lock[i+ki][j+kj]-=key[ki][kj];
                            }
                        }
                    }
                }
            }
            key = turn(key);
        }
        
        return false;
    }
    
    public void print(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]);
            }
            System.out.println("");
        }
    }
    public int[][] expand(int pad, int[][] lock){
        int width = lock.length+pad*2;
        int[][] nLock = new int[width][width];
        
        //가운데 채우기
        
        for(int i=0;i<lock.length;i++){
            for(int j=0;j<lock.length;j++){
                nLock[pad+i][pad+j] = lock[i][j];
            }
        }
        
        return nLock;
    }
    
    public int[][] turn(int[][] key){
        int[][] nKey = new int[key.length][key[0].length];
        
        //0,0 -> 2,0
        //0,1 -> 1,0
        for(int i=0;i<key.length;i++){
            for(int j=0;j<key.length;j++){
                nKey[key.length-1-j][i] = key[i][j];
            }
        }
        return nKey;
    }
}