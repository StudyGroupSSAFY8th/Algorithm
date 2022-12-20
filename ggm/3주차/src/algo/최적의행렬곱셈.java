package algo;

import java.util.Arrays;

public class 최적의행렬곱셈 {

	public static void main(String[] args) {
		int[][] matrix_sizes = { { 4, 3 }, { 3, 2 }, { 2, 5 }, { 5, 1 }, { 1, 3 } };
		System.out.println(solution(matrix_sizes));

	}

	/*
	 * 참고함
	 * 
	 * 
	 * 
	 * 행렬의 곱을 계산하기 위한 연산 횟수중에서 제일 최소를 찾는 것이 목표 해당 계산 횟수의 값은 연산의 순서에 따라 다르다.
	 * 
	 * A : 5 * 3, B : 3 * 10, C : 10 * 6의 행렬이 있을 때 
	 * (A * B) * C = (5 * 3 * 10) + (5 *10 * 6) = 450 
	 * A * (B * C) = (5 * 3 * 6) + (3 * 10 * 6) = 270
	 * 
	 * 
	 * 연쇄 행렬곱셈 알고리즘을 사용한다
	 *  https://source-sc.tistory.com/24 
	 *  행렬의 개수가 5개 라고 가정을 할때
	 *  모든 결합의 마지막 결합 형태는 크게 다음과 같이 네가지로 분류 가능 (행렬의 개수가 n개일때 (n-1)가지로 분류 가능) 
	 *  1. A * (B *C * D * E) 
	 *  2. (A * B) * (C * D * E) 
	 *  3. (A * B * C) * (D * E) 
	 *  4. (A * B * C *D) * E
	 * 
	 * (AB)(C(DE)) 는 최종적으로 AB와 CDE의 곱이므로 2 에 해당하고
	 *  (A(B(CD)))E 는 최종적으로 ABCD와 E의 곱이므로 4에 해당한다.
	 * 
	 * 따라서 위 4가지 방법들 중 가장 최소값이 최종적인 답이 된다.
	 * 
	 * dp[시작][끝] = (시작~끝)범위에 있는 행렬을 모두 곱할 때 최소 연산수
	 * 
	 * 점화식 (시작 ~ 끝 까지의 연산수) = (시작 ~ 중간 까지의 연산수) + (중간 + 1 ~ 끝 까지의 연산수) + 두 행렬을 곱하기 위한 연산 수
	 * dp[시작][끝] = min(dp[시작][중간] + dp[중간 + 1][끝] + (matrix[시작][0] * matrix[중간][1] * matrix[끝][0]))
	 * 
	 */
	public static int solution(int[][] matrix_sizes) {
		int answer = 0;

		int len = matrix_sizes.length;
		int[][] dp = new int[len][len];

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				dp[i][j] = Integer.MAX_VALUE;
			}
		}

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len - i; j++) {
				int start = j;
				int end = i + j;

				if (start == end)
					dp[start][end] = 0;
				else {
					/*
					 * dp[시작][끝] = (시작~끝)범위에 있는 행렬을 모두 곱할 때 최소 연산수
					 * 
					 * 점화식 (시작 ~ 끝 까지의 연산수) = (시작 ~ 중간 까지의 연산수) + (중간 + 1 ~ 끝 까지의 연산수) + 두 행렬을 곱하기
					 * 위한 연산 수 dp[시작][끝] = min(dp[시작][중간] + dp[중간 + 1][끝] + (matrix[시작][0] *
					 * matrix[중간][1] * matrix[끝][0]))
					 */
					for (int mid = start; mid < end; mid++) {
//        				
						dp[start][end] = Math.min(dp[start][end], dp[start][mid] + dp[mid + 1][end]
								+ (matrix_sizes[start][0] * matrix_sizes[mid][1] * matrix_sizes[end][1]));
					}
//        			System.out.println();
				}
			}
			for (int j = 0; j < len; j++)
				System.out.println(Arrays.toString(dp[j]));
			System.out.println();
		}

		return answer;
	}

}
