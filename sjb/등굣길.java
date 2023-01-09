import java.util.*;
class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        //오른쪽, 아래로만 이동
        
        int[][] map = new int[n][m];
        
        for(int i=0;i<puddles.length;i++){
            map[puddles[i][1]-1][puddles[i][0]-1] = -1;
        }
        
        //초기화
        // for(int i=0;i<n;i++){
        //     for(int j=0;j<m;j++){
        //         map[i][j] = Integer.MAX_VALUE;
        //     }
        // }
        
        map[0][0] = 1;
        
        //현위치 = 왼쪽 + 위쪽
        
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i==0&&j==0){
                    map[i][j] = 1;
                    continue;
                }
                if(map[i][j]==-1){
                    continue;
                }
                
                int left = 0;
                int up = 0;
                
                if(i-1>=0&&j-1>=0){
                    left = map[i][j-1];
                    up = map[i-1][j];
                }else if(j-1>=0){
                    left = map[i][j-1];
                }else if(i-1>=0){
                    up = map[i-1][j];
                }
                
                
                if(left==-1&&up==-1){
                    map[i][j]= -1;   
                }else if(left==-1){
                    map[i][j]  = up;
                }else if(up == -1){
                    map[i][j]  = left;
                }else{
                    map[i][j] = (left+up)%1000000007;
                }
            }
        }
        
        // for(int i=0;i<map.length;i++){
        //     System.out.println(Arrays.toString(map[i]));
        // }
        answer = map[n-1][m-1]%1000000007;
        return answer;
    }
}