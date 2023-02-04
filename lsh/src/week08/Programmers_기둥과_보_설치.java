package week08;

/**
 * @author : sh Lee
 * @date : 23. 1. 30.
 */

import java.util.*;

/**
 * 아이디어
 * 단순 구현문제이다.
 * 보가 설치된 배열과, 기둥이 설치된 배열 두개를 만들고, 표시를 해둔다.
 * 보나 기둥을 배치할때는 조건에 맞는지 확인하고, 삭제할때는 삭제후에 조건에 맞는지 확인하는 식으로 진행한다.
 * 주의할 점은, 일반적인 배열의 형태가 아닌, 왼쪽아래가 0,0이라는 점이다.
 */
public class Programmers_기둥과_보_설치 {

    static int N;
    static boolean[][] beams; //보
    static boolean[][] pillars; //기둥


    //배열 번위를 벗어나는지 확인.
    static boolean rangeCheck(int x, int y){
        if(x >= 0 && x <= N && y >= 0 && y <= N ) return true;

        return false;
    }

    //삭제 할 수 있는 지 확인 - 삭제 할 수 있으면 삭제까지, 삭제가 안되는 경우라면 복구
    static void removeBuilding(int x, int y, int buildType){

        //우선 삭제
        if(buildType == 0){
            pillars[x][y] = false;
        }
        else{
            beams[x][y] = false;
        }

        boolean delCheck = false;

        //삭제 후 반복문을 돌면서 확인
        loop:
        for(int i = 0; i <= N; i++){
            for(int j = 0; j <= N; j++){
                //해당 위치가 기둥이면 놓을 수 있는 지 확인.
                if(pillars[i][j]){
                    //놓을 수 없다면,
                    if(!setPillarCheck(i,j)){
                        delCheck = true;
                        break loop;
                    }
                }

                //해당위치가 보일때
                if(beams[i][j]){
                    if(!setBeamsCheck(i,j)){
                        delCheck = true;
                        break loop;
                    }
                }
            }
        }

        //중간에 break가 되었다면, 다시 복구해둬야 됨.
        if(delCheck){
            if(buildType == 0) pillars[x][y] = true;
            else beams[x][y] = true;
        }
    }

    //보를 놓을 수 있는 지 확인
    static boolean setBeamsCheck(int x, int y){
        //1. 한쪽 끝 부분이 기둥인지
        if((rangeCheck(x+1,y) && pillars[x+1][y]) || (rangeCheck(x+1,y+1) && pillars[x+1][y+1])) return true;

        //2. 양 쪽 끝부분이 다른보와 동시에 연결되는지.
        else if((rangeCheck(x,y-1) && beams[x][y-1]) && (rangeCheck(x,y+1) && beams[x][y+1])) return true;

        return false;
    }

    //기둥을 놓을 수 있는 지 확인.
    static boolean setPillarCheck(int x, int y){

        //바닥위에 있다면 무조건 놓을 수 있음.
        if(x == N) return true;

        //보의 한쪽 끝 부분 위에 있는지 확인.
        if((rangeCheck(x,y) && beams[x][y]) || (rangeCheck(x,y-1) && beams[x][y-1])) return true;

        //다른 기둥 위인지
        if(rangeCheck(x+1,y) && pillars[x+1][y]) return true;

        return false;
    }

    //주어진 정보로 보,기둥을 배치
    static void setBuilding(int[][] build_frame) {

        for (int[] buildInfo : build_frame) {

            int x = N - buildInfo[1];
            int y = buildInfo[0];
            int buildType = buildInfo[2]; // 설치 또는 삭제할 구조물 - 0은 기둥, 1은 보
            int setType = buildInfo[3]; // 삭제 또는 설치 - 0은 삭제, 1은 설치

            //설치 인 경우와 삭제인 경우를 나눔
            //설치
            if (setType == 1) {
                //기둥
                if (buildType == 0) {
                    //해당위치에 기둥을 놓을 수 있다면 놓기
                    if (setPillarCheck(x, y)) {
                        pillars[x][y] = true;
                    }
                }
                //보
                else {
                    if (setBeamsCheck(x, y)) {
                        beams[x][y] = true;
                    }
                }
            }
            //삭제
            else {
                removeBuilding(x, y, buildType);
            }
        }
    }

    public int[][] solution(int n, int[][] build_frame) {

        N = n;
        beams = new boolean[n+1][n+1]; //보를 표시할 배열
        pillars = new boolean[n+1][n+1]; //기둥을 표시할 배열

        setBuilding(build_frame);

        List<BuildingInfo> result = new ArrayList<>();

        for(int i = 0; i <= N; i++){
            for(int j = 0; j <= N; j++){
                //기둥인 경우
                if(pillars[i][j]) result.add(new BuildingInfo(j,N-i,0));
                //보인 경우
                if(beams[i][j]) result.add(new BuildingInfo(j,N-i,1));
            }
        }

        Collections.sort(result);

        int[][] answer = new int[result.size()][3];
        for(int i = 0; i < result.size(); i++){
            answer[i][0] = result.get(i).x;
            answer[i][1] = result.get(i).y;
            answer[i][2] = result.get(i).type;
        }

        return answer;
    }

    static class BuildingInfo implements Comparable<BuildingInfo>{
        int x,y,type;

        BuildingInfo(int x, int y, int type){
            this.x = x;
            this.y = y;
            this.type = type;
        }


        @Override
        public int compareTo(BuildingInfo o) {

            if(this.x == o.x){

                if(this.y == o.y) return this.type - o.type;

                return this.y - o.y;
            }

            return this.x - o.x;
        }
    }

    public static void main(String[] args) {
        Programmers_기둥과_보_설치 s = new Programmers_기둥과_보_설치();
        int n = 5;
        int [][] build_frame = {{1,0,0,1},{1,1,1,1},{2,1,0,1},{2,2,1,1},{5,0,0,1},{5,1,0,1},{4,2,1,1},{3,2,1,1}};

        System.out.println(Arrays.deepToString(s.solution(n, build_frame)));

    }


}
