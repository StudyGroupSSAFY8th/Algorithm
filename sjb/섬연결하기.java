//최소비용으로 모든섬이 통행 가능하도록 만들기
    //크루스칼!
import java.util.*;
class Solution {
    public int solution(int n, int[][] costs) {
        int answer = 0;
        
        //a,b,c
            //a와b가 연결됨, c의 비용으로
        
        //연결
        int[] parent = new int[n];
        for(int i=0;i<n;i++){
            parent[i] = i;
        }
        Arrays.sort(costs,(a,b)->a[2]-b[2]);
        
        for(int i=0;i<costs.length;i++){
            if(union(parent,costs[i][0],costs[i][1])){
                answer+=costs[i][2];
            }
        }
        
        return answer;
    }
    
    public int findParent(int[] parent,int idx){
        if(idx==parent[idx]){
            return idx;
        }
        parent[idx] = findParent(parent, parent[idx]);
        return parent[idx];
    }
    
    public boolean union(int[] parent, int a, int b){
        int aP = findParent(parent,a);
        int bP = findParent(parent,b);
        if(aP!=bP){
            parent[aP] = bP;
            return true;
        }
        return false;
    }
}