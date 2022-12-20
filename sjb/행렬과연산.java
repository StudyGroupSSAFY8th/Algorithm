import java.util.*;
class Solution {
    
    public int[][] solution(int[][] rc, String[] operations) {
        //가운데
        // LinkedList<LinkedList<Integer>> center = new LinkedList<LinkedList<Integer>>();
        Deque<Deque<Integer>> center = new ArrayDeque<Deque<Integer>>();
        //왼쪽
        // LinkedList<Integer> left = new LinkedList<Integer>();
        Deque<Integer> left = new ArrayDeque<Integer>();
        //오른쪽
        // LinkedList<Integer> right = new LinkedList<Integer>();
        Deque<Integer> right = new ArrayDeque<Integer>();
        
        for(int i=0;i<rc.length;i++){
            //LinkedList<Integer> tmp = new LinkedList<Integer>();
            Deque<Integer> tmp = new ArrayDeque<Integer>();
            for(int j=0;j<rc[0].length;j++){
                if(j==0){
                    left.add(rc[i][j]);
                }else if(j==rc[0].length-1){
                    right.add(rc[i][j]);
                }else{
                    tmp.add(rc[i][j]);
                }
            }
            center.add(tmp);
        }
        
        
        
        int[][] answer = new int[rc.length][rc[0].length];
        for(String op:operations){
            if(op.equals("Rotate")){
                turn2(center,left,right);
            }else{
                shift2(center,left,right);              
            }
        }
        
        for(int i=0;i<rc.length;i++){
            answer[i][0] = left.poll();
            answer[i][rc[0].length-1] = right.poll();
            
            Deque<Integer> tmp = center.poll();
            int idx = 1;
            while(!tmp.isEmpty()){
                answer[i][idx++] = tmp.poll();
            }
        }
        
        
        return answer;
    }
    
    public void shift2(Deque<Deque<Integer>> center,Deque<Integer> left,Deque<Integer> right){
        int tt = left.pollLast();
        left.addFirst(tt);
        
        tt = right.pollLast();
        right.addFirst(tt);
        
        Deque<Integer> tmp = center.pollLast();
        center.addFirst(tmp);
    }
    
    public void turn2(Deque<Deque<Integer>> center,Deque<Integer> left,Deque<Integer> right){
        int tmp = left.poll();
        
        center.getFirst().addFirst(tmp);
        tmp = center.getFirst().pollLast();
        
        right.addFirst(tmp);
        tmp = right.pollLast();
        
        center.getLast().add(tmp);
        tmp = center.getLast().poll();
        
        left.add(tmp);
        
    }
    
    
    //--------------------------
    
    
    public int[][] solution_timeout(int[][] rc, String[] operations) {
        for(String op:operations){
            if(op.equals("Rotate")){
                rotate(rc);
            }else{
                rc = shift(rc);                
            }
        }
        return rc;
    }
    
    public void print(int[][] map){
        for(int i=0;i<map.length;i++){
            System.out.println(Arrays.toString(map[i]));
        }
    }
    public int[][] shift(int[][] map){
        int[][] nMap = new int[map.length][map[0].length];
        
        for(int i=0;i<map.length-1;i++){
            nMap[i+1] = map[i];
        }
        nMap[0] = map[map.length-1];
        
        return nMap;
    }
    //바깥 원소 돌리기
    public void rotate(int[][] map){
        int[] dr = {1,0,-1,0};//하,우,상,좌
        int[] dc = {0,1,0,-1};
        
        int tmp = map[0][0];
        
        int r = 0;
        int c = 0;
        for(int d =0;d<4;d++){
            while(true){
                int zr = r+dr[d];
                int zc = c+dc[d];
                
                if(zr>=0&&zc>=0&&zr<map.length&&zc<map[0].length){
                    map[r][c] = map[zr][zc];
                    
                    r = zr;
                    c = zc;
                }else{
                    break;
                }
                
            } 
        }
        
        map[0][1] = tmp;
    }
}