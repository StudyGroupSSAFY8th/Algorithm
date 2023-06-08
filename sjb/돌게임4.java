import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt( br.readLine());
		
		//1,3,4
		
		//1
			//2일때 차례인사람이 승리
		//3
			//4일때 차례인 사람이 승리
		//4
			//5일때 차례인 사람이 승리
		
		//6,7일때 승리
		
		//돌의 갯수마다 하나의 경우라도 승리하면 해당 숫자를 먹는다.
		
		//6이면
			//1짐,4짐
			//3 이김
		//7이면
		
		//8이면
			//3먹으면 짐
			//4먹으면 짐
			//1먹으면 짐
			//전부 짐
		//9이면
			//1먹으면 
		
		String dp[] = new String[1001];
		dp[1] = "CY";
		dp[2] = "SK";
		dp[3] = "CY";
		
		//sk가 기준으로 진행한다.
			//1개라도 sk가 이기면 sk가 승리

		for(int i=4;i<=n;i++) {
			
			//1
			if(i-1>0) {
				if(!dp[i-1].equals("SK")) {
					dp[i]="SK";
//					System.out.println(i+" 1번전에 sk승");
					continue;
				}
			}
			//3
			if(i-3>0) {
				if(!dp[i-3].equals("SK")) {
					dp[i]="SK";
//					System.out.println(i+" 3번전에 sk승");
					continue;
				}
			}
			//4
			if(i-4>0) {
				if(!dp[i-4].equals("SK")) {
					dp[i]="SK";
//					System.out.println(i+" 4번전에 sk승");
					continue;
				}
			}
			
			dp[i] = "CY";
//			System.out.println(i+" , "+dp[i]);
		}
		
		System.out.println(dp[n]);
	}

}
