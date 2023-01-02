package programmers;

import java.util.*;

public class 이중우선순위큐 {
	
	public static void main(String[] args) {
		String[] operations = {"I 4", "I -1", "I 6", "I 3"};
		System.out.println(Arrays.toString(solution(operations)));
	}
	
	public static int[] solution(String[] operations) {
        int[] answer = new int[2];
        ArrayList<Integer> list = new ArrayList<>();
        
        for(int i = 0; i < operations.length; i++) {
        	String str = operations[i];
        	
        	// 큐에 숫자 삽입하는 명령어일때
        	if(str.charAt(0) == 'I') {
        		// 숫자부분만 뽑아서 int형으로 바꿈
        		int num = Integer.parseInt(str.substring(2, str.length()));
        		// 큐에 넣기
        		list.add(num);
        	} else {
        		// 큐가 비어있지 않다면
        		if(!list.isEmpty()) {
        			// 최솟값 삭제
        			if(str.equals("D -1")) {
        				list.remove(0);
        			}
        			// 최댓값 삭제
        			else if(str.equals("D 1")) {
        				list.remove(list.size()-1);
        			}
        		}
        	}
        	
        	// 오름차순 정렬
        	Collections.sort(list);
        }
        
        if(!list.isEmpty()) {
        	answer[0] = list.get(list.size()-1);
        	answer[1] = list.get(0);
        }
        
        return answer;
    }

}
