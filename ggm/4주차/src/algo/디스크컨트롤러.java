package algo;

import java.util.Arrays;
import java.util.PriorityQueue;


public class 디스크컨트롤러 {

	public static void main(String[] args) {

		int[][] jobs = {{0, 3}, {1, 9}, {2, 6}};
		
		System.out.println(solution(jobs));
	}
	
	/*
	 * 참초함
	 * 
	 */
	public static int solution(int[][] jobs) {
        int answer = 0;
        
        /*
         * 1. 받능 배열을 요청 시간의 오름차순으로 정렬
         * 2. 우선순위 큐를 사용하여 소요시간의 오름차순으로 정렬 되도록 한다.
         * 3. 현재 작업죽인 job의 소요시간을 end로 잡고 end안에 요청이 들어오는 job들만 우선순위 큐에 넣는다.
         * end전에 요청을 하는 job이 없고 우선순위 큐가 비어 있다면 하나를 넣는다.
         * 4. 매 턴 마다 우선순위 큐에서 작업을 하나 씩 빼서 수행할때마다 end를 업데이트
         * 
         * 
         */
        
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        
        int idx = 0;
        int cnt = 0;
        int total = 0;
        int end = 0;
        
        while(cnt < jobs.length) {
        	
        	while(idx < jobs.length && jobs[idx][0] <= end) {
        		pq.add(jobs[idx++]);
        	}
        	
        	if(pq.isEmpty()) {
        		end = jobs[idx][0];
        	}
        	else {
        		int[] cur = pq.poll();
        		total += cur[1] + end - cur[0];
        		end += cur[1];
        		cnt++;
        	}
        	
        	
        }
        
        answer = total / jobs.length;
        
        return answer;
    }

}
