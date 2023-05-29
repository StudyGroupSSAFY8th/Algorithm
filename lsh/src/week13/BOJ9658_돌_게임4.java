package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * 완벽하게 게임을 한다 -> 이길수 있으면 이기는 수를 내고 지는 수를 내지는 않는다. 즉 필사적으로 이기려고 한다고 생각하면 된다.
 * 나열해보면
 * 1 2 3 4 5 6 7 8 ...
 * c s c s s s s c ...
 * 나열해보니 7로 나눈 나머지가 1또는 3일때 반복이 된다.
 */

public class BOJ9658_돌_게임4 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		if(N % 7 == 1 || N % 7 == 3) System.out.println("CY");

		else System.out.println("SK");


	}
}
