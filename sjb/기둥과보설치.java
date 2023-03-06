import java.util.*;
class Solution {
    public int[][] solution(int n, int[][] build_frame) {
        
        //기둥은 바닥, 보의 한쪽끝, 다른 기둥위
        //보는 한쪽끝이 기둥위, 양쪽 끝부분이 다른 보와 동시 연결
        ArrayList<int[]> result = new ArrayList<>();
        
        //추가할때 검증
        //삭제할때도 검증
        boolean[][][] map = new boolean[n+1][n+1][2];
        
        for(int i=0;i<build_frame.length;i++){
            //c,r,a,b
                //a 0은기둥,1은 보
                //b 0은삭제,1은 설치
            int c = build_frame[i][0];
            int r = build_frame[i][1];
            int a = build_frame[i][2];
            int b = build_frame[i][3];
            
            if(b==1){
                map[r][c][a]=true;
                if(!check(map)){
                    // System.out.println("-못함---");
                    map[r][c][a] = false;
                }else{
                    // System.out.println("---------");
                    // print(map);
                }
            }else{
                map[r][c][a]=false;
                if(!check(map)){
                    // System.out.println("---못해--");
                    map[r][c][a] = true;
                }else{
                    // System.out.println("---------");
                    // print(map);
                }
            }
        }
        
        
        
        int height = map.length;
        int width = map[0].length;
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(map[i][j][0]){
                    result.add(new int[]{j,i,0});
                }
                if(map[i][j][1]){
                    result.add(new int[]{j,i,1});
                }
                
            }
        }
        
        Collections.sort(result,(a,b)->{
            if(a[0]==b[0]){
                return a[1]-b[1];
            }
            return a[0]-b[0];
        });
        
        int[][] answer = new int[result.size()][3];
        
        for(int i=0;i<result.size();i++){
            answer[i] = result.get(i);
        }
        
        return answer;
    }
    
    public void print(boolean[][][] map){
        int height = map.length;
        int width = map[0].length;
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                System.out.print("["+map[i][j][0]+","+map[i][j][1]+"]");
            }
            System.out.println();
        }
    }
    public boolean check(boolean[][][] map){
        int height = map.length;
        int width = map[0].length;
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(map[i][j][0]){//기둥체크
                    if(!pillarCheck(map,i,j)){
                        return false;
                    }
                }
                
                if(map[i][j][1]){//보 체크
                    if(!boCheck(map,i,j)){
                        return false;
                    }
                }
                
            }
        }
        
        return true;
        
    }
    
    public boolean boCheck(boolean[][][] map,int r, int c){
        //한쪽이 기둥위, 한쪽이 보이면 가능
        
        boolean leftCheck = false;
        boolean rightCheck = false;
        
        //바로아래에 기둥
        if(map[r-1][c][0]){
            leftCheck = true;
            return true;
        }
        //왼쪽에 보
        if(c-1>=0&&map[r][c-1][1]){
            leftCheck = true;
        }
            
            
        //오른쪽에 보
        if(map[r][c+1][1]){
            rightCheck = true;
        }
        //오른쪽 아래에 기둥
        if(map[r-1][c+1][0]){
            rightCheck=true;
            return true;
        }
        
        if(leftCheck&&rightCheck){
            return true;
        }
        return false;
    }
    
    public boolean pillarCheck(boolean[][][] map,int r, int c){
        //바닥 확인
        if(r==0){
            return true;
        }
        
        //보의 한쪽 끝인지 -> 기둥밑에 보가 존재하면 됨
        if(map[r][c][1] || (c-1>=0 && map[r][c-1][1])){
            return true;
        }
        
        //아래에 기둥이 있는지
        if(map[r-1][c][0]){
            return true;
        }
            
        return false;
        
    }
}