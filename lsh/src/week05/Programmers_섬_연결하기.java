package week05;


import java.util.Arrays;
import java.util.Comparator;

/**
 * @author : sh Lee
 * @date : 23. 1. 1.
 */
/*
아이디어.
1. 모든 섬이 서로 통행 가능하도록 하는 최소 비용 => mst를 생각할 수 있다.
2. 간선의 가중치가 주어졌기 때문에 크루스칼을 이용해서 풀수 있다.
 */

public class Programmers_섬_연결하기 {

    static int[] parents;

    static int findParents(int node){

        if(node == parents[node]) return node;

        return parents[node] = findParents(parents[node]);
    }

    static boolean union(int node1, int node2){

        int parent1 = findParents(node1); //각각의 최대 부모 찾기.
        int parent2 = findParents(node2);

        if(parent1 < parent2) parents[parent2] = parent1;
        else if(parent1 > parent2) parents[parent1] = parent2;
        else return false;

        return true;
    }

    public int solution(int n, int[][] costs) {
        int answer = 0;

        parents = new int[n]; //각 노드의 대표 루트를 저장함. 초기에는 자기 자신.
        for(int i = 0; i < n; i++) parents[i] = i;

        //크루스칼은 간선 가중치가 적은것 부터 순서대로 선택해서, 사이클이 생기는지 확인하는 식으로 진행된다
        //따라서 가중치를 기준으로 오름차순 정렬한다.
        Arrays.sort(costs, (o1, o2) -> {
                return o1[2] - o2[2];
        });


        int edgeCount = 0;//연결한 간선의 개수가 (노드 - 1)개가 되면 종료
        int weightTotal = 0; //가중치 저장.

        for(int[] cost : costs){

            int node1 = cost[0];
            int node2 = cost[1];
            int weight = cost[2];

            //두 노드를 합쳐봄 - true면 합치는데 성공.
            if(union(node1,node2)){
                edgeCount++;
                weightTotal +=  weight;
            }

            if(edgeCount == (n-1)) break;
        }

        return weightTotal;
    }
}
