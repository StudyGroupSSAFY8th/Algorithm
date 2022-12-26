package programmers;

import java.util.*;

public class 다단계칫솔판매 {
	/*
	 * enroll의 사람들을 referral에 있는 사람들이 추천해줘서 일함
	 * seller의 사람들이 amount만큼 판매를 해서 x100만큼의 이익금을 만들었음
	 * 근데 본인은 그 금액의 90%를 받고 10%는 추천해준 refferal의 사람이 받음 
	 * 그리고 refferal의 refferal이 있으면 마찬가지로 본인이 90%, 추천해준 사람이 10% 받음 
	 * 	-> refferal이 아무도 없을 때(-)까지 반복
	 * 만약 10%를 한 금액이 1원 미만이면 이득 분배하지 않고 자신이 모두 받음
	 */
	static int[] answer;
	static HashMap<String, Integer> map = new HashMap<>();
	
	public static void main(String[] args) {
		String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"}; // 판매원
		String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"}; // 위의 판매원을 추천한 사람
		String[] seller = {"young", "john", "tod", "emily", "mary"}; // 판매한 사람들
		int[] amount = {12, 4, 2, 5, 10}; // 위의 사람들의 판매 수량
		
		System.out.println(Arrays.toString(solution(enroll, referral, seller, amount)));
	}
	
	public static int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        answer = new int[enroll.length];
        
        // 인덱스를 매번 찾지말고 처음부터 map으로 이름과 인덱스 함께 저장 -> 시간 초과 해결
        for(int i = 0; i < enroll.length; i++) {
        	map.put(enroll[i], i);
        }
        
        // seller 사람이 판매한 만큼의 이익금 계산
        for(int i = 0; i < seller.length; i++) {
        	int profit = amount[i] * 100;
        	
        	// i번째 seller의 인덱스 번호
        	int idx = map.get(seller[i]);
        	dfs(idx, profit, referral);
        }
        return answer;
    }

	public static void dfs(int idx, int profit, String[] referral) {    		
		// 10% 이익금
		int newProfit = (int) (profit * 0.1);
		
		// 만약 10%를 한 금액이 1원 미만이면 이득 분배하지 않고 자신이 모두 받음
		if(newProfit < 1) {
			answer[idx] += profit;
			return;
		} else {
			// 본인은 90%의 이익금 받음
			answer[idx] += (profit - newProfit);
		}
		
		// 만약에 추천해준 사람이 없으면(-) 끝내기
		if(referral[idx].equals("-")) return;
		// idx번째 사람을 추천해준 사람이 enroll에서는 newIdx번째 사람임
		int newIdx = map.get(referral[idx]);
		
		dfs(newIdx, newProfit, referral);
	}
	
}
