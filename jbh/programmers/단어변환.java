package programmers;

public class 단어변환 {
	static int answer = Integer.MAX_VALUE;
	static boolean[] visited;
	
	public static void main(String[] args) {
		String begin = "hit";
		String target = "cog";
		String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
		System.out.println(solution(begin, target, words));
	}
	
	public static int solution(String begin, String target, String[] words) {
        visited = new boolean[words.length];
        dfs(0, begin, target, words);
        
        if(answer == Integer.MAX_VALUE) answer = 0;
        return answer;
    }
	
	public static void dfs(int cnt, String begin, String target, String[] words) {
		if(begin.equals(target)) {
			answer = Math.min(answer, cnt);
			return;
		}
		
		for(int i = 0; i < words.length; i++) {
			if(visited[i]) continue;
			
			// 하나빼고 다 같은 글자인지 확인
			int count = 0;
			for(int j = 0; j < begin.length(); j++) {
				if(begin.charAt(j) != words[i].charAt(j)) {
					count++;
				}
			}
			
			// 두 글자 중에 하나만 다르다면 변환하고 계속 탐색
			if(count == 1) {
				visited[i] = true;
				dfs(cnt+1, words[i], target, words);
				visited[i] = false;
			}
		}
	}

}
