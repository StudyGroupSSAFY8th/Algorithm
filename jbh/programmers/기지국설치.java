package programmers;

public class 기지국설치 {

	public static void main(String[] args) {
		int n = 20;
		int[] stations = {4, 11, 13}; 
		int w = 2;
		
		System.out.println(solution(n, stations, w));
	}
	
	public static int solution(int n, int[] stations, int w) {
        int answer = 0;
        
        // 기지국이 설치된 아파트 기준으로 w만큼 양쪽에 전파가 전달되니까
        // -와 +를 해서 전달이 안되는 구간을 구함
        int x = 0, y = 0;
        for(int i = 0; i < stations.length; i++) {
        	// 그 전 아파트의 오른쪽 끝부분 ~ 이번 아파트의 왼쪽 끝까지
        	x = stations[i] - w - y - 1;
        	// 이번 아파트의 오른쪽 끝
        	y = stations[i] + w;
        	
        	// 한 기지국마다 전달되는 거리
        	int length = w * 2 + 1;
        	int count = 0;
        	if(x > 0) {
        		// 전파 안터지는 구간에서 최소 몇개를 설치할 수 있는지
        		count = x / length;
        		if(x % length != 0) count++;
        		
        		answer += count;
        	}
        	
        	// 만약에 오른쪽 끝부분이 전파가 전달되지 않는 공간이라면 마지막에 또 구해줘야함
        	if(i == stations.length-1) {
        		if(y < n) {
        			count = (n - y) / length;
        			if((n - y) % length != 0) count++;
        			
        			answer += count;
        		}
        	}
        }
        return answer;
    }

}
