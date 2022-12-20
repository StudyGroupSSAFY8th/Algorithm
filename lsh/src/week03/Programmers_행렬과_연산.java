package week03;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author : sh Lee
 * @date : 22. 12. 19.
 */

/*
https://nahwasa.com/entry/%EC%9E%90%EB%B0%94-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%ED%96%89%EB%A0%AC%EA%B3%BC-%EC%97%B0%EC%82%B0-Lv4-Java
 */
/*
1.맨 왼쪽, 오른쪽을 deque로 두고, 가운데 row들도 전부 deque로 두면, 모든 원소를 다 옮길 필요 없이,
회전시에는 왼쪽,오른쪽 deque
 */
public class Programmers_행렬과_연산 {

    static Deque<Integer> leftDeque;
    static Deque<Integer> rightDeque;
    static Deque<Deque<Integer>> middleDeque;
    static int[][] rc1;

    //모든행이 아래쪽으로 한칸씩 밀려나가는 연산 - deque 또는 Queue를 사용해서 처리.
    static void shiftRow(){

        //왼쪽큐 right(아래쪽)에서 하나뺴서 left쪽으로 넣음
        leftDeque.addFirst(leftDeque.pollLast());

        //오른쪽큐 right(아래쪽)에서 하나뺴서 left쪽으로 넣음
        rightDeque.addFirst(rightDeque.pollLast());

        //가운데 큐 right(아래쪽)에서 하나뺴서 left쪽으로 넣음
        middleDeque.addFirst(middleDeque.pollLast());

    }

    //행렬의 바깥쪽 원소를 시계방향으로 한칸씩 회전.
    static void rotate(){
        //왼쪽큐 left(위쪽)에서 하나 빼서 중간큐 left쪽 첫번째 원소에 넣음
        middleDeque.getFirst().addFirst(leftDeque.pollFirst());

        //오른쪽큐 right(아래쪽)에서 하나 빼서 중간큐 right쪽 마지막 원소에 넣음.
        middleDeque.getLast().addLast(rightDeque.pollLast());

        //가운데 큐 left쪽에서 마지막 원소를 빼서, 오른쪽큐 left쪽 첫번째 원소로 넣음
        rightDeque.addFirst(middleDeque.getFirst().pollLast());

        //가운데 큐 right 쪽에서 첫번째 원소를 빼서, 왼쪽큐 right쪽 마지막 원소로 넣음.
        leftDeque.addLast(middleDeque.getLast().pollFirst());
    }

    public int[][] solution(int[][] rc, String[] operations) {

        leftDeque = new LinkedList<>();//왼쪽 deque
        rightDeque = new LinkedList<>(); //오른쪽 deque
        middleDeque = new ArrayDeque<>();//가운데 deque

        rc1 = rc;

        //왼쪽 오른쪽 큐에 넣기
        for(int i = 0; i < rc.length; i++){
            leftDeque.add(rc[i][0]);
            rightDeque.add(rc[i][rc[0].length - 1]);
        }

        //가운데 deque에 넣기
        for(int i = 0; i < rc.length; i++){

            Deque<Integer> temp = new LinkedList<>();

            for(int j = 1; j < rc[0].length-1; j++){
                temp.add(rc[i][j]);
            }

            middleDeque.add(temp);
        }


        for(String operation : operations){

            //회전
            if(operation.equals("Rotate")) rotate();
            //열을 한칸씩 밀기.
            else shiftRow();
        }


        int[][] maps = new int[rc.length][rc[0].length];

        //왼쪽, 오른쪽큐에 있는 것 옮기기.
        for(int i = 0; i < rc.length; i++){
            maps[i][0] = leftDeque.pollFirst();
            maps[i][rc[0].length - 1] = rightDeque.pollFirst();
        }
        for(int i = 0; i < rc.length; i++){

            Deque<Integer> temp = middleDeque.pollFirst();

            for(int j = 1; j < rc.length - 1; j++){
                maps[i][j] = temp.pollFirst();
            }
        }

        return maps;
    }
}
