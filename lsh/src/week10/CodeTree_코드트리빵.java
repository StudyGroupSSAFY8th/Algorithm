package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
* 아이디어
* 1. 번호순대로 사람이 가고 싶은 편의점에서부터 pq를 이용한 bfs를 돌림.
* 2. bfs를 돌릴때는 시작시간부터 값을 증가시킴
* 3. bfs를 돌면서 베이스 캠프에 도착하게 되면, 이를 저장해둠
* 4. 저장한 베이스 캠프부터 bfs를 돌면서 목적지에 도달하면 종료함
*
* */


public class CodeTree_코드트리빵 {


    //그래프 탐색용 노드 - 좌표와 시간, 사람 번호 저장.
    static class Node implements Comparable<Node>{
        int x,y, peopleNum, time, dir, count;
        boolean check; //false이면 베이스 캠프로 가는 중. true 이면 편의점으로 가는중.

        Node(int x, int y){
            this.x = x;
            this.y = y;
        }
        Node(int x, int y,int peopleNum, int time, int dir, boolean check, int count){
            this.x = x;
            this.y = y;
            this.peopleNum = peopleNum;
            this.time = time;
            this.dir = dir;
            this.check = check;
            this.count = count;
        }
        Node(int x, int y,int peopleNum, int time, int dir, boolean check){
            this.x = x;
            this.y = y;
            this.peopleNum = peopleNum;
            this.time = time;
            this.dir = dir;
            this.check = check;
        }


        @Override
        public int compareTo(Node o) {

            if(this.time == o.time){

                if(this.peopleNum == o.peopleNum) {


//                    if(this.dir == o.dir){

//                        if(this.x == o.x) return this.y - o.y;
//                        return this.x - o.x;
//                    }
////

                    if(this.count == o.count){
                        if(this.x == o.x) return this.y - o.y;
                        return this.x - o.x;

                    }
                    return this.count - o.count;
                }

                return this.peopleNum - o.peopleNum;
            }

            return this.time - o.time;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", peopleNum=" + peopleNum +
                    ", time=" + time +
                    ", dir=" + dir +
                    '}';
        }
    }

    //격자크기
    static int n;
    //사람 수.
    static int m;
    //최대 시간 저장 변수
    static int maxTime;
    //격자판 선언
    static int[][] maps;
    //이동방향 정의
    static int[] dx = {-1,0,0,1};
    static int[] dy = {0,-1,1,0};
    //사람 별로 도착했는지 확인하는 배열
    static boolean[] finished;
    static boolean[] baseCampFinished;

    //편의점 정보리스트
    static List<Node> storeList;
    //사람별 출발 베이스 캠프 위치
    static Node[] startBaseCampList;

    static void print(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(maps[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("------------------");

    }


    //bfs 메서드 - 사람을 베이스 캠프에 배치후 돌리면서 찾는 bfs
    static void bfs_find_store(){

        //방문배열을 3차원으로 해결 - 사람마다 해당 위치를 방문할 수 있음. - 0이면 편의점 -> 베이스 캠프가능, 1이면 베이스 캠프 -> 편의점.
        int[][][] visited = new int[n][n][m];

        //방문처리 큐
        PriorityQueue<Node> needVisited = new PriorityQueue<>();

        for(int i = 0; i < storeList.size(); i++){
            Node node = storeList.get(i);
            visited[node.x][node.y][i] = 1;
            needVisited.add(new Node(node.x,node.y,i+1, i+1, -1, false, 0));

        }

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();




            //방문이 끝난 노드면 해당 번호의 노드는 탐색 안함.
            if(currentNode.check && finished[currentNode.peopleNum]) continue;

            //베이스 캠프로 가는 노드가 이미 베이스 캠프에 들어갔으면 탐색 안함.
            if(!currentNode.check && baseCampFinished[currentNode.peopleNum]) continue;

            //방향이 -1이면 초기 노드이므로 maps에 표시해줌.
//            if(currentNode.dir == -1){
//                maps[currentNode.x][currentNode.y] = -1;
//            }

//            if(!currentNode.check && currentNode.peopleNum == 2){
//                System.out.println(currentNode.x + ", " + currentNode.y);
//            }

            //목적지에 도달하면 시간저장하고 도착 체크 (편의점 -> 베이스 캠프.)
            if(!currentNode.check && maps[currentNode.x][currentNode.y] == 1){
                baseCampFinished[currentNode.peopleNum] = true;
                maps[currentNode.x][currentNode.y] = -1;
                currentNode.check = true;
                startBaseCampList[currentNode.peopleNum] = new Node(currentNode.x, currentNode.y);



            }
            // 베이스 캠프 -> 편의점.
            else if(currentNode.check && currentNode.x == storeList.get(currentNode.peopleNum-1).x && currentNode.y == storeList.get(currentNode.peopleNum-1).y){
                finished[currentNode.peopleNum] = true;
                maxTime = Math.max(maxTime, currentNode.time);
                maps[currentNode.x][currentNode.y] = currentNode.time;
//                System.out.println(currentNode.peopleNum + " : " + currentNode.x + "," + currentNode.y);
                continue;
            }

            //인접노드 추가.
            for(int i = 0 ; i < 4; i++){

                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                //현재 가는 방향이 편의점 -> 베이스 캠프.
                if(!currentNode.check){
                    //이동할 위치가 범위를 벗어나지 않고, 해당 maps에 시간이 현재시간보다 작다면 이동 가능.
                    if(nextX >= 0 && nextX < n && nextY >= 0 && nextY < n && visited[nextX][nextY][currentNode.peopleNum-1] == 0){

                        if(maps[nextX][nextY] == -1) continue;

                        //0이면 이동 가능.
                        if(maps[nextX][nextY] == 0 || maps[nextX][nextY] == 1 || (maps[nextX][nextY] > currentNode.time + 1)){

                            visited[nextX][nextY][currentNode.peopleNum-1] = 1;
                            needVisited.add(new Node(nextX,nextY, currentNode.peopleNum, currentNode.time, i, currentNode.check, currentNode.count + 1));
                        }
                    }
                }

                //현재 가는 방향이 베이스 캠프 -> 편의점.
                else {
                    //이동할 위치가 범위를 벗어나지 않고, 해당 maps에 시간이 현재시간보다 작다면 이동 가능.
                    if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < n && visited[nextX][nextY][currentNode.peopleNum - 1] != 2) {

                        if (maps[nextX][nextY] == -1) continue;

                        //0이면 이동 가능.
                        if (maps[nextX][nextY] == 0 || maps[nextX][nextY] == 1 || (maps[nextX][nextY] > currentNode.time + 1)) {

                            visited[nextX][nextY][currentNode.peopleNum - 1] = 2;
                            needVisited.add(new Node(nextX, nextY, currentNode.peopleNum, currentNode.time + 1, i, currentNode.check));
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        maps = new int[n][n];
        maxTime = -1;

        finished = new boolean[m+1];
        baseCampFinished = new boolean[m+1]; //베이스 캠프에 도달했을때.

        storeList = new ArrayList<>();
        startBaseCampList = new Node[m+1];

        //격자판 입력
        for(int i = 0; i < n;  i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //편의점 위치.
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            storeList.add(new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1));
        }


        bfs_find_store();

        System.out.println(maxTime);

    }
}
