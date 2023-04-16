package week11;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 아이디어
 * 1. 먼저 모든 노드의 가중치를 더했을때 0이 되는지 확인한다
 * 2. 0이 안된다면 모든 점들의 가중치를 0으로 만들 수 없다.
 * 3. 모든 리프노드를 찾고, 리프 노드에서 연결된 노드로 숫자를 몰아준다.
 * 4. 리프노드는 연결된 간선이 한개이므로 연결된 쪽으로 몰아주는게 가장 효율적이다
 * 5. 그렇지 않는다면 리프노드쪽으로 숫자를 몰아줬다가 다시 몰아줘야되는 경우가 생긴다.
 * 6. 가중치가 0이 되면 해당 노드의 간선을 끊어준다
 * 7. 리프노드를 탐색하는 것을 반복한다.
 */

public class Programmers_모두_0으로_만들기 {

    //이동 횟수 저장.
    static long count;

    //가중치를 전역으로 선언.
    static long[] weightArray;

    //각 노드에 연결된 간선 수를 나타낼 Map선언
    static Map<Integer,Integer> edgesCount;

    //인접리스트 선언
    static Queue<Integer>[] graph;

    //연결된 노드로 현재 노드의 가중치가 0이 될때까지 값을 더하거나 빼는 메서드
    //node1(리프노드) -> node2
    static void moveWight(int node1, int node2){

        weightArray[node2] += weightArray[node1];
        count += Math.abs(weightArray[node1]);
        weightArray[node1] = 0;

        //간선 수 하나씩 빼기.
        edgesCount.put(node1,0);
        edgesCount.put(node2,edgesCount.get(node2)-1);
    }

    //현재 시점에서 리프노드인 것(간선의 개수가 1)을 리스트로 반환하는 메서드
    static List<Integer> findLeafNode(){

        List<Integer> returnList = new ArrayList<>();

        for(int node : edgesCount.keySet()){
            if(edgesCount.get(node) != 1) continue;

            returnList.add(node);
        }

        return returnList;
    }

    //엣지를 받아서 인접리스트 형태의 그래프를 만드는 메서드
    static void makeGraph(int[][] edges){
        graph = new Queue[weightArray.length];

        for(int i = 0; i < weightArray.length; i++){
            graph[i] = new LinkedList<>();
        }

        for(int[] edge : edges){
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

    }

    //모든 가중치의 합이 0인지 확인하는 메서드
    static boolean weightSumCheck(){

        long tempCount = 0;

        for(long temp : weightArray){
            tempCount += temp;
        }

        if(tempCount != 0) return false;

        return true;
    }

    //노드별 간선수 체크
    static void edgeCount(){
        edgesCount = new HashMap<>();

        for(int i = 0; i < weightArray.length; i++){
            edgesCount.put(i,graph[i].size());
        }
    }


    public long solution(int[] a, int[][] edges) {

        //최대 10만에 30만개 있기 때문에 long으로 해야됨.
        weightArray = new long[a.length];
        for(int i = 0; i < a.length; i++){
            weightArray[i] = a[i];
        }

        //그래프 만들기
        makeGraph(edges);

        //간선 수 저장.
        edgeCount();

        //모든 노드 가중치의 합이 0인지 확인.
        if(!weightSumCheck()) return -1;

        List<Integer> leadNodeList = findLeafNode();
        while(leadNodeList.size() != 0){


            for(int i : leadNodeList){

                for(int j : graph[i]){
                    if(edgesCount.get(j) == 0) continue;
                    moveWight(i, j);
                }

            }

            leadNodeList = findLeafNode();
        }


        return count;
    }
}
