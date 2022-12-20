package programmers;

import java.util.*;

public class 행렬과연산 {
	static ArrayDeque<Integer> leftCol, rightCol;
	static ArrayDeque<ArrayDeque<Integer>> row = new ArrayDeque<>();

	public static void main(String[] args) {
		int[][] rc = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
		String[] operations = {"ShiftRow", "Rotate", "ShiftRow", "Rotate"};
		System.out.println(Arrays.deepToString(solution(rc, operations)));
	}

	public static int[][] solution(int[][] rc, String[] operations) {
        int[][] answer = new int[rc.length][rc[0].length];
        leftCol = new ArrayDeque<>();
        rightCol = new ArrayDeque<>();
        
        // 왼쪽과 오른쪽 열을 분리
        for(int i = 0; i < rc.length; i++) {
        	leftCol.add(rc[i][0]);
        	rightCol.add(rc[i][rc[0].length-1]);
        }
        
        // 나머지 행 부분
        for(int i = 0; i < rc.length; i++) {
        	ArrayDeque<Integer> deq = new ArrayDeque<>();
        	for(int j = 1; j < rc[0].length-1; j++) {
        		deq.add(rc[i][j]);
        	}
        	row.add(deq);
        }
        
        for(int i = 0; i < operations.length; i++) {
        	if(operations[i].equals("ShiftRow")) {
        		shiftRow();
        	} else {
        		rotate();
        	}
        }
        
        // deque에 있는 모든 애들 정리하기 -> answer에 넣어주기
        // leftCol, rightCol
        for(int i = 0; i < rc.length; i++) {
        	answer[i][0] = leftCol.pollFirst();
        	answer[i][rc[0].length-1] = rightCol.pollFirst();
        }
        
        // row
        for(int i = 0; i < rc.length; i++) {
        	ArrayDeque<Integer> deq = row.pollFirst();
        	for(int j = 1; j < rc[0].length-1; j++) {
        		answer[i][j] = deq.pollFirst();
        	}
        }
        return answer;
    }
	
	public static void rotate() {
		// 1  2  3  4		5  1  2  3
		// 5  6  7  8		9  6  7  4
		// 9  10 11 12		10 11 12 8
		
		// 0번째 열(leftCol), n-1번째 열(rightCol), 나머지 부분(row)을 분리하여 각자 구함
		// leftCol: 1	rightCol: 4		row: 2  3
		//			5			  8			 6  7
		//			9			  12		 10 11
		// -> 다른 영역으로 옮겨지는 아이는 1, 3, 12, 10
		
		// 1. leftCol의 1을 오른쪽(row)으로 보내기
		// 1 뽑고
		int num = leftCol.pollFirst();
		// 첫번째 행의 맨 왼쪽(처음)에 붙이기
		row.peekFirst().addFirst(num);
		// 2. row의 3을 오른쪽(rightCol)으로 보내기
		// 3 뽑고
		num = row.peekFirst().pollLast();
		// rightCol의 맨 위(처음)에 붙이기
		rightCol.addFirst(num);
		// 3. rightCol의 12를 왼쪽(row)으로 보내기
		// 12 뽑고
		num = rightCol.pollLast();
		// 마지막 행의 맨 오른쪽(끝)에 붙이기
		row.peekLast().add(num);
		// 4. row의 10을 왼쪽(leftCol)으로 보내기
		// 10 뽑고
		num = row.peekLast().pollFirst();
		// rightCol의 맨 아래(끝)에 붙이기
		leftCol.add(num);
	}
	
	public static void shiftRow() {
		// 마지막 행을 맨 위로 보내면 된다.
		leftCol.addFirst(leftCol.pollLast());
		rightCol.addFirst(rightCol.pollLast());
		row.addFirst(row.pollLast());
	}
	
	// 정확성 O, 효율성 X -> 시간초과
//	public static int[][] rotate(int[][] rc) {
//		Queue<Integer> que = new ArrayDeque<>();
//		que.add(rc[1][0]);
//		
//		for(int j = 0; j < rc[0].length; j++) {
//			que.add(rc[0][j]);
//			rc[0][j] = que.poll();
//		}
//		
//		for(int i = 1; i < rc.length; i++) {
//			que.add(rc[i][rc[0].length-1]);
//			rc[i][rc[0].length-1] = que.poll();
//		}
//		
//		for(int j = rc[0].length-2; j >= 0; j--) {
//			que.add(rc[rc.length-1][j]);
//			rc[rc.length-1][j] = que.poll();
//		}
//		
//		for(int i = rc.length-2; i > 1; i--) {
//			que.add(rc[i][0]);
//			rc[i][0] = que.poll();
//		}
//		
//		rc[1][0] = que.poll();
//		return rc;
//	}
//	
//	public static int[][] shiftRow(int[][] rc) {
//		int[][] arr = new int[rc.length][rc[0].length];
//		
//		for(int j = 0; j < rc.length; j++) {
//			if(j == rc.length-1) {
//				arr[0] = rc[j].clone();
//			} else {
//				arr[j+1] = rc[j].clone();				
//			}
//		}
//		
//		return arr;
//	}
	
}
