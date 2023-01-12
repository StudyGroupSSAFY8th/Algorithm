package week06;

/**
 * @author : sh Lee
 * @date : 23. 1. 13.
 */
import java.util.*;

/**
 * 아이디어
 * 완탐문제이다.
 * 직선이 100, 커브가 500이라고 해서, 최소비용을 구하기 위해서 현재 위치에서 최소비용으로 가면, 제대로된 경로가 나올 수가 없다
 * 또한, 언제 커브를 해야 최소가 나오는지, 해당 위치에서 직선으로 갔을 때, 최소같지만, 실제로 다 구해보면 커브일때가 최소 일 수도 있다.
 * 따라서 완탐을 해야하고, 목적지까지 탐색하면서 비용을 구해야 하기 때문에 dfs를 사용한다.
 * 매번 다시 dfs로 다시 구하면 중복된 연산을 계속하게 된다.
 * 따라서 메모이제이션으로 한번 목적지에 도달하면, 재귀함수에서 리턴을 하면서, 값을 저장해준다.
 *
 * dp + dfs로 모든경우를 탐색하면서 했더니, 3개가 틀리고, 2개가 시간초과가 발생헀다.
 *
 * 아이디어2. bfs를 이용하여 풀어보려고 한다
 * 현재 위치에서 다음 위치로 이동할때 드는 비용을 계산하고, 이전에 저장된 값보다 더 작은 경우에만 값을 넣어준다.
 *
 * 아이디어3.
 * bfs를 사용한 탐색은 어떻게 보면 그리디한 탐색이 된다.
 * 따라서 완벽하게 모든 경우를 커버할 수 없기 때문에, 25번 테케가 틀리게 된다.
 * 현재의 풀이는, bfs로 다음 위치보다 현재 누적된 값이 작거나 같으면 업데이트 하도록 했는데, 현재 누적된 값이 다음 위치보다 크다고 하더라도, 그 이후에는 더 작은 값이 나올 수도 있다.
 * 따라서 모든 경우를 커버하기 위해서는, 3차원 배열을 사용해야 한다.
 * 4방향이기 때문에 특정 위치에 4가지 방향으로 올 수 있는 경우를 모두 담도록 하는 식으로 코드를 수정해야 한다.
 */
public class Programmers_경주로_건설 {

    static int N;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    //    static int[][] visited;
    static int[][][] visited;
    static int[][] maps;

    static int result;

    //dfs를 돌면서 목적지까지의 비용을 구하고, 재귀함수가 종료되면서 값을 저장하는 메서드
    //이전값을 알아야 다음 위치로 가는 비용을 알 수 있음.
    //왔던 위치를 반복해서 탐색하지 않도록, board에서 방문한 위치를 -1로 바꿔두고, 재귀함수가 리턴되어 돌아오면 다시 0으로 만듦.
    /*
    preDir => 이전에 이동한 방향
    cost => 해당위치까지 이동하는데 든 비용
     */
    /*
    static int dfs(Node currentNode, int prevDir){


        // 목적지에 도달하면 목적지까지 누적된 비용을 리턴.
        if(currentNode.x == (N - 1) && currentNode.y == (N - 1)) {
            visited[currentNode.x][currentNode.y] = currentNode.cost;
            return currentNode.cost;
        }


        System.out.println("x : " + currentNode.x + " y : " + currentNode.y);
        //목적지가 아니라면 4방향 탐색으로 목적지 탐색함.
        //단, 이동하려고 하는 위치에 저장된 값이, 0이 아닌 값인데, 현재까지 누적한 값이 저장된 값과 같거나 크면, 더 가볼 필요 없음.
        for(int i = 0; i < 4; i++){
            int nextX = currentNode.x + dx[i];
            int nextY = currentNode.y + dy[i];

            // 이동하려고 하는 위치가 0일때만 이동
            if(nextX >= 0 && nextX < N && nextY >= 0 && nextY < N && maps[nextX][nextY] == 0){
                System.out.println("nextX : " + nextX + ", nextY : " + nextY);

                // |이전방향 - 현재선택한 방향| == 0이면 직진이므로 100원, 나머지의 경우에는 500원, (1일때는 왔던곳으로 돌아가는 것이라 불가능.)
                int tempCost = currentNode.cost;

                if(prevDir == -10 || Math.abs(prevDir - i) == 0){
                    //100원
                    tempCost += 100;
                }
                else{
                    //500원
                    tempCost += 600;
                }
                System.out.println(tempCost);

                //다음위치에 저장된 비용과, 구한 비용을 비교
                //다음 위치에 저장된 비용과 구한비용을 비교했을때, 구한 비용이 저장된 것 보다 크다면 진행 안함.
                if(tempCost < visited[nextX][nextY]){
                    maps[nextX][nextY] = -1; //왔던 방향을 탐색하지 않기 위해서 -1로 표시.
                    int returnValue = dfs(new Node(nextX,nextY,tempCost), i);
                    maps[nextX][nextY] = 0;

                    if(returnValue != -1) visited[currentNode.x][currentNode.y] = Math.min(returnValue,visited[currentNode.x][currentNode.y]);

                }
                // 저장된 비용보다 크다면,
                else{
                    return visited[nextX][nextY];
                }
            }
        }

        return -1;

    }

    public int solution(int[][] board) {
        int answer = 0;

        N = board.length; // 지도의 길이.
        visited = new int[N][N]; //dfs탐색을 하면서 메모이제이션 할 2차원 배열.
        maps = board; //주어진 지도를 전역을 사용


        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                visited[i][j] = Integer.MAX_VALUE;
            }
        }

        visited[0][0] = 0;
        maps[0][0] = -1;
        dfs(new Node(0,0,0), -10);


        System.out.println(Arrays.deepToString(visited));

        return visited[N-1][N-1];
    }
    */

    /*bfs코드*/
    //방향 => 0: 좌,1 : 상,2 : 우,3 : 하
    static int bfs(){

        PriorityQueue<Node> needVisited = new PriorityQueue<>();

        visited[0][0][0] = visited[1][0][0] = visited[2][0][0] =visited[3][0][0] = -1; //방문 처리.

        //오른쪽
        if(maps[0][1] == 0){
            needVisited.add(new Node(0,1,100, 3));
            visited[3][0][1] = 100;

        }
        //아래
        if(maps[1][0] == 0){
            needVisited.add(new Node(1,0,100, 2));
            visited[2][1][0] = 100;
        }



        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            if(currentNode.x == N-1 && currentNode.y == N-1){
                return currentNode.cost;
            }

            for(int i = 0; i < 4; i++){

                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];


                if(nextX >= 0 && nextX < N && nextY >= 0 && nextY < N && maps[nextX][nextY] != 1){
                    int tempCost = currentNode.cost;
                    if((currentNode.dir+2)%4 == i) continue;
                    if(currentNode.dir == i){
                        //100원
                        tempCost += 100;
                    }
                    else{
                        //600원
                        tempCost += 600;
                    }


                    //방문을 안했거나, 이전에 저장된 값보다 작다면,
                    if(visited[i][nextX][nextY] == 0 || visited[i][nextX][nextY] >= tempCost){
                        visited[i][nextX][nextY] = tempCost;
                        needVisited.add(new Node(nextX,nextY,tempCost,i));
                    }
                }
            }
        }

        return -1;
    }

    public int solution(int[][] board) {

        N = board.length; // 지도의 길이.
        visited = new int[4][N][N]; //방문처리 배열 - 4가지 방향(0:좌, 1: 상, 2:우,3:하)
        maps = board; //주어진 지도를 전역을 사용

        return bfs();
    }

    static class Node implements Comparable<Node>{
        int x, y, cost,dir;

        Node(int x, int y, int cost,int dir){
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.dir = dir;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }
}
