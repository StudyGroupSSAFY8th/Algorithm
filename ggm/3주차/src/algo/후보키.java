package algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class 후보키 {

	// column 후보키 되는지 확인하는 배열


	static int[] sel;



	static List<Set<Integer>> keys;

	public static void main(String[] args) {
		String[][] relation = { {"a","1","aaa","c","ng"},
				{"a","1","bbb","e","g"},
				{"c","1","aaa","d","ng"},
				{"d","2","bbb","d","ng"}};

		System.out.println(solution(relation));

	}

	// 18 19 20 22 25

	public static int solution(String[][] relation) {
		int answer = 0;

		

		
		keys = new ArrayList<>();
		for (int i = 1; i <= relation[0].length; i++) {
			sel = new int[i];

			comb(0, 0, relation);

		}

		return keys.size();
	}

	private static void comb(int cnt, int idx, String[][] relation) {
		if (sel.length == cnt) {

			
			// 유일성 체크
			Set<String> stringCheck = new HashSet<>();

			for (int i = 0; i < relation.length; i++) {

				String key = "";

				for (int j = 0; j < sel.length; j++) {
					key += relation[i][sel[j]] + " ";

				}
//        		System.out.println(key);

				stringCheck.add(key);
			}

//        	System.out.println(stringCheck.size() == relation.length);

			
			// 최소성 체크
			if (stringCheck.size() == relation.length) {

				
				Set<Integer> idxCheck = new HashSet<>();
				for (int i = 0; i < sel.length; i++) {
					idxCheck.add(sel[i]);
				}
				
				for(Set<Integer> containCheck : keys) {
					if(idxCheck.containsAll(containCheck)) return;
				}
				
				keys.add(idxCheck);
//				cntCol++;
			}

			return;
		}

		for (int i = idx; i < relation[0].length; i++) {

			sel[cnt] = i;
			comb(cnt + 1, i + 1, relation);
		}

	}

}
