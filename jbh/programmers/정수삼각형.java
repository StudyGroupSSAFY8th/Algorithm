package programmers;

public class 정수삼각형 {
	/*
	 * 7
	 * 3 8
	 * 8 1 0
	 * 2 7 4 4
	 * 4 5 2 6 5
	 */
	public static void main(String[] args) {
		int[][] triangle = {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};	
		System.out.println(solution(triangle));
	}
	
	public static int solution(int[][] triangle) {
        int answer = 0;
        
        for(int i = 1; i < triangle.length; i++) {
        	for(int j = 0; j < triangle[i].length; j++) {
        		// j번째 인덱스의 위(i-1행)에서 바로 위랑 왼쪽 중에 더 큰값을 선택해야함
        		int num = 0;
        		// 왼쪽의 인덱스가 0보다 작으면 자동으로 오른쪽에 있는거 선택 
        		if(j-1 < 0) num = triangle[i-1][j];
        		// 오른쪽이 인덱스보다 크면 자동으로 왼쪽에 있는거 선택
        		else if(j >= triangle[i-1].length) num = triangle[i-1][j-1];        		
        		else num = Math.max(triangle[i-1][j-1], triangle[i-1][j]);
        		// 더 큰 값으로 누적합
        		triangle[i][j] += num;
        		answer = Math.max(answer, triangle[i][j]);
        	}
        }
        
        return answer;
    }

}
