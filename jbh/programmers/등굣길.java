package programmers;

public class 등굣길 {

	public static void main(String[] args) {
		int m = 4;
		int n = 3;
		int[][] puddles = {{2, 2}};
		System.out.println(solution(m, n, puddles));
	}

	public static int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        int[][] arr = new int[n][m];
        
        // 물 잠긴곳  -1로 초기화       
        for(int[] pud : puddles) {
            arr[pud[1]-1][pud[0]-1] = -1;
        }
        
        // 시작위치 초기화
        arr[0][0] = 1;
        
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < m; j++) {
        		// 물 잠기면 못가니까 0으로 바꿈
        		if(arr[i][j] == -1) {
        			arr[i][j] = 0;
        			continue;
        		}
        		// 위에 값 더해줌
        		if(i != 0) {
        			arr[i][j] += arr[i-1][j] % 1000000007;
//        			arr[i][j] = (arr[i][j] + arr[i-1][j]) % 1000000007;
        		}
        		// 왼쪽 값 더해줌
        		if(j != 0) {
        			arr[i][j] += arr[i][j-1] % 1000000007;
//        			arr[i][j] = (arr[i][j] + arr[i][j-1]) % 1000000007;
        		}
        	}
        }
        
        answer = arr[n-1][m-1] % 1000000007;
//      answer = arr[n-1][m-1];
        return answer;
    }
	
}
