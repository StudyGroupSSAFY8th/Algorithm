package algo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class 행렬과연산 {

	static int N;
	static int M;

	static Deque<Integer> deq;

	public static void main(String[] args) {

//		int[][] rc = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
		int[][] rc = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		String[] operations = { "ShiftRow", "Rotate", "ShiftRow", "Rotate" };

		int[][] answer = solution(rc, operations);

		for (int i = 0; i < answer.length; i++) {
			System.out.println(Arrays.toString(answer[i]));
		}

	}

	public static int[][] solution(int[][] rc, String[] operations) {

		N = rc.length;
		M = rc[0].length;

		int[][] answer = new int[N][M];

		deq = new ArrayDeque<>();

		for (int i = 0; i < N; i++) {
			deq.add(i);
		}

//		System.out.println(deq);

		for (int i = 0; i < operations.length; i++) {

			if (operations[i].equals("ShiftRow")) {
				shiftRow(rc);
			} else {
				rc = rotate(rc);
//				initDeque();
			}

		}

		for (int i = 0; i < N; i++) {
			int idx = deq.pollFirst();
			answer[i] = rc[idx];
		}

		return answer;
	}



	private static int[][] rotate(int[][] rc) {
		int[][] tmp = new int[N][M];

		for (int i = 0; i < N; i++) {
			int idx = deq.pollFirst();

			if (i == 0 || i == N - 1) {
				for (int j = 0; j < M; j++) {

					if (i == 0) {
						if (j == M - 1) {
							tmp[1][j] = rc[idx][j];
						} else {
							tmp[i][j + 1] = rc[idx][j];
						}
					}

					else if (i == N - 1) {
						if (j == 0) {
//						System.out.println(i -1 + " " + j);
							tmp[i - 1][j] = rc[idx][j];
						} else {
							tmp[i][j - 1] = rc[idx][j];
						}
					}

				}
			} else {

				tmp[i - 1][0] = rc[idx][0];

				tmp[i + 1][M - 1] = rc[idx][M - 1];

			}

			deq.addLast(idx);
		}
		
		
		
		for (int i = 0; i < N; i++) {
			int idx = deq.pollFirst();

			if (i == 0 || i == N - 1) {
				for (int j = 0; j < M; j++) {

					rc[idx][j] = tmp[i][j];

				}
			} else {
				
				rc[idx][0] = tmp[i][0];
				rc[idx][M-1] = tmp[i][M-1];
			}

			deq.addLast(idx);
		}
		
//		for(int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(rc[i]));
//		}
//		System.out.println();
		
		
		return rc;

	}

	private static void shiftRow(int[][] rc) {

		deq.addFirst(deq.pollLast());

	}

}
