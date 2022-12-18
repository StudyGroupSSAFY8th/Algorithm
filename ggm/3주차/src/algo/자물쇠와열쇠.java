package algo;

import java.util.Arrays;

/*
 * 자물쇠 영역을 벗어난 부분에 있는 열쇠의 홈과 돌기는 자물쇠를 여는데 영향을 주지 않는다.
 * 자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확이 일치 해야함
 * 
 * 
 * 자물쇠의 모든 홈을 채워야 열 수 있다.
 */

/*
 * 처음에 열쇠 배열을 상하좌우 이동을 통해 자물쇠 배열과 비교한다.
 * 자물쇠 부분에 배열이 0이 오면 열쇠 부분에 1이 와야한다
 * 
 * 만약 맞지 않으면 90도 회전하여 위와 같은 방법으로 다시 실행
 * 
 * 제자리로 돌아오게 되면 false를 결과로 리ㅏ턴해준다
 * 
 * 단 열쇠의 상하좌우 움직이지만 시작 지점의 좌표는 열쇠는 0,0 자물쇠는 N -1. N -1 겹치게 시작
 * 
 */
public class 자물쇠와열쇠 {

	static int[][] copyLock;

	public static void main(String[] args) {

		int[][] key = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
		int[][] lock = { { 1, 1, 1, 1, 1, 1, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1 }, 
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1 }};

		System.out.println(solution(key, lock));
	}

	public static boolean solution(int[][] key, int[][] lock) {
		boolean answer = false;

		copyLock = new int[lock.length][lock.length];

		for (int i = 0; i < lock.length; i++) {
			for (int j = 0; j < lock.length; j++) {
				copyLock[i][j] = lock[i][j];
			}
		}

		for (int i = 0; i < 4; i++) {

//			System.out.println("key");
//			for(int a = 0; a < key.length; a++) {
//				System.out.println(Arrays.toString(key[a]));
//			}
//			System.out.println();

			answer = moveKey(key, lock);
//			System.out.println(answer);
			
			if (answer == true)
				break;
			turnKey(key);

		}

		return answer;
	}

	private static void turnKey(int[][] key) {
		int[][] tmp = new int[key.length][key.length];

		// 90도

		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key.length; j++) {
//					System.out.println(j + " " + (key.length - 1 - i));
				tmp[j][key.length - 1 - i] = key[i][j];
			}
		}

		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key.length; j++) {
//					System.out.println(j + " " + (key.length - 1 - i));
				key[i][j] = tmp[i][j];
			}
		}

	}

	private static boolean moveKey(int[][] key, int[][] lock) {
		boolean flg = false;

		int nr = lock.length;
		int nc = lock.length;

		for (int i = nr; i > 0; i--) {
			for (int j = nc; j > 0; j--) {

				copyLock(lock);

				for (int k = 0; k < lock.length - i + 1; k++) {
					for (int l = 0; l < lock.length - j + 1; l++) {
//						
						if(k < key.length && l < key.length) {
//							System.out.println((i - 1 + k) + " " + (j - 1 + l));
							lock[i - 1 + k][j - 1 + l] += key[k][l];
						}
					}
				}

//				for(int a = 0; a < lock.length; a++) {
//					System.out.println(Arrays.toString(lock[a]));
//				}
//				System.out.println();

				if (checkLock(lock)) {
//					System.out.println(1111);
					return true;
				}

//				System.out.println();

			}

//			System.out.println();

		}

//		System.out.println("right up");
		for (int i = 1; i <= nr; i++) {
			for (int j = nc; j > 0; j--) {

				copyLock(lock);

				for (int k = 0; k < i; k++) {
					for (int l = 0; l < lock.length - j + 1; l++) {
						 
						

						if (k < key.length && l < key.length) {
//							System.out.println((i-1-k) + " " + (j - 1 + l));
							lock[i-1-k][j - 1 + l] += key[key.length- k - 1][l];
						}
					}

				}
				
//				for(int a = 0; a < lock.length; a++) {
//					System.out.println(Arrays.toString(lock[a]));
//				}
//				System.out.println();
				
//				System.out.println();
				if (checkLock(lock)) {
					return true;
				}

			}

//			System.out.println();

		}

//		System.out.println("left down");
		for (int i = nr; i > 0; i--) {
			for (int j = 1; j <= nc; j++) {

				copyLock(lock);

				for (int k = 0; k < lock.length - i + 1; k++) {
					for (int l = 0; l < j; l++) {
						// System.out.println(k + " " + l);
						if (k < key.length && l < key.length) {
//							System.out.println((i - 1 + k) + " " + (j-l-1));
							lock[i - 1 + k][j-l-1] += key[k][key.length - l - 1];
						}
					}
				}
				
				

				if (checkLock(lock)) {
					return true;
				}

//				System.out.println();

			}

//			System.out.println();

		}

//		System.out.println("left up");
		for (int i = 1; i <= nr; i++) {
			for (int j = 1; j <= nc; j++) {

				copyLock(lock);

				for (int k = 0; k < i; k++) {
					for (int l = 0; l < j; l++) {
						// System.out.println(k + " " + l);
						if (k < key.length && l < key.length) {
//							System.out.println((i-k-1) + " " + (j-l-1));
							lock[i-k-1][j-l-1] += key[key.length - k - 1][key.length - l - 1];
						}
					}
				}
				

				if (checkLock(lock)) {
					return true;
				}

//				System.out.println();

			}

//			System.out.println();

		}

		return flg;
	}

	private static boolean checkLock(int[][] lock) {
		for (int i = 0; i < lock.length; i++) {
			for (int j = 0; j < lock.length; j++) {
				if (lock[i][j] != 1) {
					return false;
				}
			}
		}
		return true;
	}

	private static void copyLock(int[][] lock) {
		for (int i = 0; i < lock.length; i++) {
			for (int j = 0; j < lock.length; j++) {
				lock[i][j] = copyLock[i][j];
			}
		}

	}

}
