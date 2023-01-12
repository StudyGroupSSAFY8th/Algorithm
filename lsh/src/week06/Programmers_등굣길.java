package week06;

/**
 * @author : sh Lee
 * @date : 23. 1. 13.
 */

import java.util.Arrays;

/**
 * 아이디어
 * dp + dfs를 이용한 문제이다.
 * 출발지부터 목적지까지 이동을 하고, 목적지에 도달하면, 재귀함수 리턴하면서 +1씩 한다.
 *
 * 아이디어2
 * dp로만 풀지 않으면 통과가 안된다.
 * 시간초과가 나는 문제이다.
 */
public class Programmers_등굣길 {

    static int M;
    static int N;

    static int[][] dp;

    static final int MOD =  1000000007;
//    static int[] dx = {1,0};
//    static int[] dy = {0,1};
//
//    static int[][] maps;
//
//    static int[][] visited;
//
//    static int dfs(Node currentNode){
//
//        if(currentNode.x == M-1 && currentNode.y == N-1){
//            return 1;
//        }
//
//        if(visited[currentNode.x][currentNode.y] != 0){
//            if(visited[currentNode.x][currentNode.y] > 1000000007) return visited[currentNode.x][currentNode.y] % 1000000007;
//            else return visited[currentNode.x][currentNode.y];
//        }
//
//        for(int i = 0; i < 2; i++){
//            int nextX = currentNode.x + dx[i];
//            int nextY = currentNode.y + dy[i];
//
//            if(nextX >= 0 && nextX < M && nextY >= 0 && nextY < N && maps[nextX][nextY] == 0){
//                maps[nextX][nextY] = 1;
//
//                visited[currentNode.x][currentNode.y] += dfs(new Node(nextX,nextY));
//
//                maps[nextX][nextY] = 0;
//            }
//        }
//
//        if(visited[currentNode.x][currentNode.y] > 1000000007) return visited[currentNode.x][currentNode.y] % 1000000007;
//        else return visited[currentNode.x][currentNode.y];
//    }

//    public int solution(int m, int n, int[][] puddles) {
//        int answer = 0;
//
//        M = m;
//        N = n;
//        visited = new int[M][N];
//        maps = new int[M][N];
//
//        for(int[] puddle : puddles){
//            maps[puddle[0]-1][puddle[1]-1] = 1;
//        }
//
//        dfs(new Node(0,0));
//
//        System.out.println(Arrays.deepToString(visited));
//
//
//        return visited[0][0] % 1000000007;
//    }

    public int solution(int m, int n , int[][] puddles){

        dp = new int[m+1][n+1]; //경로 수를 누적할 배열

        // 물 웅덩이는 -1로 표현
        for(int[] puddle : puddles){
            dp[puddle[0]][puddle[1]] = -1;
        }

        dp[1][1] = 1;

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){

                //물 웅덩이는 패스
                if(dp[i][j] == -1) continue;

                //이동은 오른쪽과 아래쪽만 가능함
                //오른쪽으로 이동하는 경우
                if(dp[i][j-1] > 0){
                    dp[i][j] += dp[i][j-1] % MOD;
                }

                //아래로 이동하는 경우
                if(dp[i-1][j] > 0){
                    dp[i][j] += dp[i-1][j] % MOD;
                }


            }
        }

        return dp[m][n]% MOD;
    }


    static class Node{
        int x,y;

        Node(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
}
