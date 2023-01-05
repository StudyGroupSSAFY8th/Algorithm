package week06;

/**
 * @author : sh Lee
 * @date : 23. 1. 4.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 아이디어트
 * 트리를 구성하고, 전위순회(루트 - 좌 - 우), 후위순회(좌 - 우 - 루트) 하는 방식이다.
 * 각 좌표가 주어지기 때문에 y축기준으로 가장 위에 있는 루트부터 탐색하도록 해야 하는데, 이를 위해서 y값을 기준으로 정렬해준다.
 * y값이 같으면 왼쪽부터 채우기 위해서 (완전이진트리가 아니라서 마음대로 해도 됨) x값으로 정렬한다.
 * 각 노드번호는 인덱스+1로 주어졌기 때문에, 노드 객체를 구성할때 반드시 노드 번호도 넣어 주어야 한다.
 */
public class Programmers_길_찾기_게임 {

    static Node[] treeNode;

    static List<Integer> preOrderResult;
    static List<Integer> postOrderResult;

    //현재 노드정보를 받아서 다음 노드 정보 업데이트 - 트리 구성
    static void makeTree(Node rootNode, Node childNodeCandidate){

        //루트 노드랑 x값을 비교해서 왼쪽에 있을때
        if(rootNode.x > childNodeCandidate.x){

            //루트의 왼쪽에 값이 없으면 그대로 넣기
            if(rootNode.left == null) rootNode.left = childNodeCandidate;
            //왼쪽에 이미 값이 있으면
            else makeTree(rootNode.left, childNodeCandidate); //왼쪽에 있는 노드를 부모로 하여 재귀호출해서 똑같은 연산수행.
        }
        //오른쪽에 있을떄
        else if(rootNode.x < childNodeCandidate.x){

            //루트의 오른쪽에 값이 없으면 그대로 넣기
            if(rootNode.right == null) rootNode.right = childNodeCandidate;
            //없다면 재귀호출로 타고 내려가봄
            else makeTree(rootNode.right, childNodeCandidate);
        }
    }

    //전위순회
    static void preOrder(Node rootNode){

        if(rootNode == null) return;

        preOrderResult.add(rootNode.nodeNum);

        preOrder(rootNode.left);
        preOrder(rootNode.right);
    }

    //후위순회
    static void postOrder(Node rootNode){

        if(rootNode == null) return;

        postOrder(rootNode.left);
        postOrder(rootNode.right);

        postOrderResult.add(rootNode.nodeNum);
    }

    public int[][] solution(int[][] nodeinfo) {

        treeNode = new Node[nodeinfo.length]; //노드 정보를 객체에 담은 배열

        //주어진 노드정보를 가지고 전부 객체화 하고 배열에 저장
        for(int i = 0; i < nodeinfo.length; i++){
            treeNode[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], i+1);
        }

        Arrays.sort(treeNode); //상위레벨일수록 인덱스상 앞에 오도록 만들어 줘야됨.

        //루트(0번째)를 시작으로 1번째 노드부터 하나씩 어디에 넣어야되는지 탐색
        for(int i = 1; i < nodeinfo.length; i++){
            makeTree(treeNode[0], treeNode[i]);
        }

        int[][] answer = new int[2][nodeinfo.length];

        preOrderResult = new ArrayList<>(); //전위순회 결과
        postOrderResult = new ArrayList<>(); //후위순회 결과.

        preOrder(treeNode[0]);
        postOrder(treeNode[0]);

        //전위 순회 결과를 결과리턴 배열로 옮기기
        for(int i = 0; i < nodeinfo.length; i++){
            answer[0][i] = preOrderResult.get(i);
        }

        //후위 순회 결과를 결과리턴 배열로 옮기기
        for(int i = 0; i < nodeinfo.length; i++){
            answer[1][i] = postOrderResult.get(i);
        }

        return answer;
    }

    //노드를 y내림차순 -> x오름차순  순서로 정렬해야 되기 때문에 Comparable을 구현해준다.
    static class Node implements Comparable<Node>{
        int x,y, nodeNum;

        Node left,right ; //노드는 왼쪽 오른쪽을 가진다.

        Node(int x, int y, int nodeNum){
            this.x = x;
            this.y = y;
            this.nodeNum = nodeNum;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node o) {
            if(this.y == o.y) return this.x - o.x;
            return o.y - this.y;
        }
    }

}
