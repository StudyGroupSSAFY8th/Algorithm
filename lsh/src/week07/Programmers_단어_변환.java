package week07;

/**
 * @author : sh Lee
 * @date : 23. 1. 21.
 */

import java.util.*;

/**
 * 아이디어
 * 단순한 그래프 탐색문제이다.
 * 시작노드를 begin변수로 잡고, bfs탐색을 돌린다
 * 다음 노드는 words배열에서 찾고, 매번 글자 수 차이가 1인지 확인하는 메서드를 만들어서 확인한다.
 * 글자 수 차이가 1이면,다음 탐색을 위한 노드로 추가하고, 방문처리를 한다.
 * 문자열이므로 배열로 방문처리가 불가능하고, 집합 자료구조를 이용해서 방문처리를 한다
 */
public class Programmers_단어_변환 {

    //두 단어를 입력받아서 1글자 차이인지 확인하는 메서드
    static boolean strCheck(String currentNode, String nextNode){

        int strLen = currentNode.length();
        int count = 0; //다른 글자수 카운트

        for(int i = 0; i < strLen; i++){

            //두 글자가 다르다면 카운트
            if(currentNode.charAt(i) != nextNode.charAt(i)){
                count++;

                //두글자 이상 차이나면 종료
                if(count >= 2) return false;
            }
        }

        return true;
    }

    //단어를 탐색할 bfs 메서드
    static int bfs(String begin, String target, String[] words){

        Set<String> visited = new HashSet<>(); //방문처리할 집합 자료구조

        Queue<Node> needVisited = new ArrayDeque<>(); // 탐색할 노드 저장할 큐
        needVisited.add(new Node(begin,0));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll(); //큐에 있는 노드 하나 빼봄

            //노드가 타켓단어라면 종료
            if(currentNode.node.equals(target)) return currentNode.weight;

            //다음 탐색할 노드 찾기
            for(int i = 0; i < words.length; i++){

                //방문한적 없고, 글자 수 차이가 1인 단어라면 추가
                if(!visited.contains(words[i]) && strCheck(currentNode.node, words[i])){
                    needVisited.add(new Node(words[i], currentNode.weight + 1));
                    visited.add(words[i]);
                }
            }
        }

        return -1;
    }
    public int solution(String begin, String target, String[] words) {
        int answer = 0;

        //단어 리스트에 target단어가 없다면 만들수 없음.
        boolean check = false;
        for(String word : words){
            if(target.equals(word)){
                check = true;
                break;
            }
        }

        if(!check) return 0;

        return bfs(begin, target, words);
    }

    static class Node{
        String node; //문자열
        int weight; //단어 변환 횟수

        Node(String node, int weight){
            this.node = node;
            this.weight = weight;
        }
    }
}
