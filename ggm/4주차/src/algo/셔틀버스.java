package algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class 셔틀버스 {

	public static void main(String[] args) {
		
		// 운행횟수
		int n = 1;
		
		// 셔틀 운행 간격
		int t = 10;
		
		// 한 셔틀에 탈 수 있는 최대 크루 수
		int m = 3;
		String[] timetable = {"08:55", "08:55", "08:59"};
		System.out.println(solution(n, t, m, timetable));

	}
	
	public static String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        
        /*
         *  콘이 셔틀을 탈 수 있는 제일 늦은 시각을 찾는다.
         *  단 콘의 도착 시간과 다른 크로의 도착 시간이 같으면 콘이 제일 뒤에 정렬이 된다.
         */
        
        Arrays.sort(timetable);
        
        // 버스 출발 시간
        int busStart = 60 * 9;
        
        
        Map<Integer, int[]> map = new HashMap<>();
        
        int idx = 0;
        
        //for문을 n만큼 굴린다.
        for(int i = 0; i < n; i++) {
        	
        	int curTime = busStart + (i * t);
        	
        	int[] tmp = new int[m];
        	
        	
        	
        	for(int j = 0; j < m; j++) {
        		
        		if(idx < timetable.length) {
        			String[] time = timetable[idx].split(":");
            		int busTime = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            		
//            		System.out.println(busTime);
            		
            		
            		if(busTime <= curTime) {
            			tmp[j] = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            			idx++;
            		}
            		else {
            			break;
            		}
        		}
        		else {
        			break;
        		}
        		
        		
        		
        	}
        	
        	map.put(curTime, tmp);
        	
        }
        
        
        int max = busStart;
        int[] arr = map.get(busStart);
//        System.out.println(Arrays.toString(arr));
        for(int key : map.keySet()) {
        	
        	if(max < key) {
        		arr = map.get(key);
        		max = key;
        	}
        }
        
//        System.out.println(Arrays.toString(arr));
        
        boolean flg = false;
        int checkIdx = 0;
        
        for(int i = 1; i < m; i++) {
        	if(arr[i] != arr[i-1]) {
        		flg = true;
        		checkIdx = i-1;
        		
        	}
        }
//        System.out.println(arr[checkIdx+1]);
        if(flg) {
        	
        	if(arr[checkIdx+1] != 0) {
	        	
	        	
	        	answer = makeAnswer(arr[checkIdx+1]-1);
        	}
        	else {
        		
        		answer = makeAnswer(max);
        	}
        	
        }
        else {
        	if(arr[checkIdx] != 0) {
        		int tmp = arr[checkIdx] -1;
        	
        		answer = makeAnswer(tmp);
        	}
        	else {
        		answer = makeAnswer(max);
        	}
        	
        	
        	
        }
        
        return answer;
    }
	
	public static String makeAnswer(int tmp) {
		String answer = "";
		if(Integer.toString(tmp/60).length() == 1) {
    		answer += "0"+Integer.toString(tmp/60);
    	}
    	else {
    		answer += Integer.toString(tmp/60);
    	}
    	
    	answer += ":";
    	
    	if(Integer.toString(tmp%60).length() == 1) {
    		answer += "0"+Integer.toString(tmp%60);
    	}
    	else {
    		answer += Integer.toString(tmp%60);
    	}
    	
    	return answer;
	}

}
