package programmers;

import java.util.*;

public class 섬연결하기 {
	static int[] parents;
	
	public static void main(String[] args) {
		int n = 4;
		int[][] costs = {{0,1,1},{0,2,2},{1,2,5},{1,3,1},{2,3,8}};
		System.out.println(solution(n, costs));
	}

	public static int solution(int n, int[][] costs) {
        int answer = 0;
        parents = new int[n];
        
        // 처음에 자기 자신을 부모로 만들어줘서 초기화하기
        for(int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}
        
        // costs의 길이에 대해 오름차순 정렬
        Arrays.sort(costs,new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});
        
        for(int i = 0; i < costs.length; i++) {
        	// 각각의 부모를 찾아서
        	int px = findParent(costs[i][0]);
        	int py = findParent(costs[i][1]);
        	
        	// 만약 같으면 넘어가고
        	if(px == py) continue;
        	// 같지 않으면 합치기
        	union(px, py);
        	answer += costs[i][2];
        }
        return answer;
    }
	
	// x와 y의 부모 합치기
	public static void union(int x, int y) {
		int px = findParent(x);
		int py = findParent(y);
		
		if(px > py) parents[px] = py;
		else parents[py] = px;
	}
	
	// 부모 찾기
	public static int findParent(int x) {
		if(parents[x] == x) return x;
		
		int px = findParent(parents[x]);
		parents[x] = px;
		return px;
	}
	
}
