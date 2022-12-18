package one;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class 선입선출_스케줄링 {

	public static void main(String[] args) {
		
		// 처리해야할 일의 갯수
		int n = 6;
		
		// 코어당 작업 시간
		int[] cores = {1,2,3};
		
		System.out.println(solution(n, cores));
		System.out.println(solution2(n, cores));
	}
	
	public static int solution(int n, int[] cores) {
        int answer = 0;
        
        
       
        
        
        // while문을 돌려 n이 0이 되면 해당 코어의 idx를 answer에 넣어준다.
        // timer를 통해 각 코어들이 배수에 해당하는지 판단한다.
        
        
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < cores.length; i++) {
    		min = Math.min(min, cores[i]);
    	}
        
        
        
        int timer = min;
        n = n - cores.length;

        while(n != 0) {
//        	System.out.println("t: " + timer);
        	
        	for(int i = 0; i < cores.length; i++) {
        		if(timer % cores[i] == 0) {
        			
        			
//        			System.out.print(cores[i] + " ");
        			
        			if(n != 0) {
        				n--;
        			}
        		}
        		if(n == 0) {
        			answer = i;
        			break;
        		}
        	}
        	
//        	System.out.println();
        	timer++;
        	
        	
        }
        
        
        return answer+1;
    }
	
	
	// 참고
	public static int solution2(int n, int[] cores) {
		int answer = 0;
		
		//  총 작업량 n이 주어졌을때, 이 n개의 작업을 처리할 수 있는 최소 시간 H를 구하는 것
		// H는 이분탐색으로 구한다.
		
		
		
		
		// 최소 시간
		int left = 0;
		
		//최대 시간
		int max = 0;
		for(int i = 0; i < cores.length; i++) {
			max = Math.max(max, cores[i]);
		}
		
		int right = max * n;
		
		int time = 0;
		// time까지 처리한 작업량
		int m = 0;
		
		while(left <= right) {
			
			// 시간의 이분탐색
			int mid = (left + right) /2;
			
			
			
			int count = CountN(mid, cores);
			
			
			if(count >= n) {
				time = mid;
				right = mid -1;
				m = count;
				
			}
			else {
				left = mid + 1;
			}
			
		}
		
		
		
		
		m -= n;
		
		for(int i = cores.length - 1; i >= 0; i--) {
			if(time % cores[i] == 0) {
				if(m == 0) {
					answer = i + 1;
					break;
				}
				m--;
			}
		}
		
		
		return answer;
	}

	// 해당 시간의 작업갯수를 구하여 리턴
	private static int CountN(int time, int[] cores) {
		
		//time이 0이면 모든 코어들이 비어있기 때문에 코어의 갯수를 리턴
		if(time == 0) {
			return cores.length;
			
		}
		
		//
		int count = cores.length;
		
		// time까지 코어가 처리할 수 있는 작업량 누적
		for(int i = 0; i < cores.length; i++) {
			count += (time / cores[i]);
		}
		
		return count;
	}

}
