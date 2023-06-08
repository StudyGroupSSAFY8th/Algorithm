import java.io.*;
import java.util.*;
import java.math.BigInteger;

//1793
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		BigInteger[] dp= new BigInteger[251];
		
		dp[0] = new BigInteger("1");
		dp[1] = new BigInteger("1");
		//0 = 1
		//1 = 1
		//2 = 3
		//3 = 5
		//4 = 11 = 5+3*2
		//5 = 14,23,32,41 = 11 + 5*2 =21?
		//6 = 21 + 11*2 = 43
		//7 = 43 + 21*2 = 85
	
		for(int i=2;i<=250;i++) {
			dp[i] = dp[i-1].add(dp[i-2].multiply(new BigInteger("2")));
		}
		
		while(true) {
			String line = br.readLine();
			if(line==null) break;
			int n = Integer.parseInt(line);
			
			System.out.println(dp[n]);
		}
		
	}

}
