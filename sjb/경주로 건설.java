import java.util.*;

class Solution {
    class Car{
        int r,c,dir,cost;
        Car(int r, int c, int dir, int cost){
            this.r = r;
            this.c = c;
            this.dir = dir;//방향
            this.cost = cost;//비용
        }
    }
    
    public int solution(int[][] board) {
        int answer = bfs(board);
        return answer;
    }
    
    public int bfs(int[][] board){
        int[][][] memo = new int[board.length][board[0].length][2];
        //초기화
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                memo[i][j][0] = Integer.MAX_VALUE;
                memo[i][j][1] = Integer.MAX_VALUE;
            }
        }
        
        memo[0][0][0] = 0;//시작 위치 초기화
        memo[0][0][1] = 0;
        PriorityQueue<Car> que = new PriorityQueue<Car>((a,b)->a.cost-b.cost);
        
        que.offer(new Car(0,0,0,0));
        que.offer(new Car(0,0,1,0));
        //0은 좌우, 1은 상하
        //상하좌우로 이동한다.
        int[] dr = {-1,1,0,0};
        int[] dc = {0,0,-1,1};
        
        int result = Integer.MAX_VALUE;
        while(!que.isEmpty()){
            Car cur = que.poll();
            
            if(result<cur.cost){
                continue;
            }
            
            if(cur.r==board.length-1 && cur.c == board[0].length-1){
                //도착
                result = cur.cost;
                continue;
            }
            
            for(int d=0;d<4;d++){
                int nr = cur.r+dr[d];
                int nc = cur.c+dc[d];
                if(nr>=0&&nc>=0&&nr<board.length&&nc<board[0].length&&board[nr][nc]==0){
                    int ncost = cur.cost;
                    if(cur.dir==0){//좌우 방향일때
                        if(d<=1){
                            //방향이 상하
                            ncost+=600;
                        }else{
                            ncost+=100;
                        }
                    }else{//상하일때
                        if(d<=1){
                            //방향이 상하
                            ncost+=100;
                        }else{
                            ncost+=600;
                        }
                    }
                    
                    //이동할 비용과 저장된 비용 확인
                    if(d<=1){
                        //상하 방향
                        if(ncost<memo[nr][nc][1]){
                            memo[nr][nc][1] = ncost;
                            que.offer(new Car(nr,nc,1,ncost));
                        }
                    }else{
                        //좌우 방향
                        if(ncost<memo[nr][nc][0]){
                            memo[nr][nc][0] = ncost;
                            que.offer(new Car(nr,nc,0,ncost));
                        }
                    }
                }
            }
            
        }
        
        return result;
        
    }
}