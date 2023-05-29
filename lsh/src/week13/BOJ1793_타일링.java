package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * 아이디어
 * * 나열해서 규칙찾으면 쉬운 문제
 * n = 1일때, 2*1타일을 세로로 세우는 하나의 경우가 나온다
 * n = 2일때, 2*1타일 두개를 가로로 두는 경우, 세로로 두는 경우, 2*2 타일을 두는 경우, 총 3가지 경우가 나온다.
 * n = 3일때, n = 1인 경우에서 2*1타일을 가로로  위아래 두개 두는 경우와, 2*2타일을 두는 경우(2*1을 세로로 두는 경우는 n = 2인 경우에서 만들어짐), n = 2인 경우에서 타일을 세로로 두는 경우이다.
 * 중복되는 경우가 발생하지 않기 위해, 타일을 오른쪽 끝에 붙이는 경우만 생각했다
 * 위의 과정을 통해서 다음과 같은 점화식을 세울 수 있다.
 * dp[n] = 2* dp[n - 2] + dp[n - 1]
 *
 * 주의 할 점은 0일때도 경우의 수가 1이라고 친다는 점이다.
 */

public class BOJ1793_타일링 {

	static BigInteger[] dp;

	//dp 배열을 만듦 -> n이 여러 가지 일때 값을 뽑아야 하기 때문에 한번에 250까지 만들어두고 시작.
	static void makeDpArray(){

		//2까지는 값을 넣어 둠
		for(int i = 3; i <= 250; i++){
			dp[i] = dp[i - 2].multiply(BigInteger.valueOf(2)).add(dp[i - 1]);
		}
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//250까지 표현하기 위해 배열을 251까지 만듦
		dp = new BigInteger[251];

		//초기 값 채우기 => 0~2
		dp[0] = new BigInteger("1");
		dp[1] = new BigInteger("1");
		dp[2] = new BigInteger("3");

		//dp 배열 만들기.
		makeDpArray();

		//출력을 한번에 해서 연산을 줄이기 위해 StringBuilder 사용
		StringBuilder sb = new StringBuilder();

		String strN = null;
		while((strN = br.readLine()) != null && !strN.isEmpty()){

			int n = Integer.parseInt(strN);


			sb.append(dp[n].toString()).append("\n");
		}

		System.out.println(sb);
	}
}
