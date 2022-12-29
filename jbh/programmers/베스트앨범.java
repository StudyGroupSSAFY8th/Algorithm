package programmers;

import java.util.*;

/*
 * 장르 별로 가장 많이 재생된 노래 2개씩 모아서 베스트 앨범 출시
 * 1. 각 장르들 중에서 많이 재생된 장르를 먼저 수록함
 * 	-> genres[]에서 같은 장르들끼리 몇번 재생되었는지 plays[]에서 찾아서 더함
 * 	-> 그중에 많은거 순서대로 2개씩 뽑아서 베스트 앨범 출시 
 * 2. 장르 내에서 2개씩 뽑을 때 많이 재생된 노래를 먼저 수록함
 * 	-> 만약에 재생 횟수가 같으면 고유 번호가 낮은 노래를 먼저 수록함 -> 인덱스 작은 순
 * 	-> 그리고 만약에 그 장르에 노래가 하나밖에 없으면 하나만 넣음
 */
public class 베스트앨범 {
	
	public static void main(String[] args) {
		String[] genres = {"classic", "pop", "classic", "classic", "pop"};
		int[] plays = {500, 600, 150, 800, 2500};
		
		System.out.println(Arrays.toString(solution(genres, plays)));
	}

	public static int[] solution(String[] genres, int[] plays) {
        // 장르별로 재생 횟수 구하기
        HashMap<String, Integer> allGenre = new HashMap<>();
        for(int i = 0; i < genres.length; i++) {
        	// getOrDefault(값을 가져와야하는 요소의 키, 지정된 키로 매핑된 값이 없는 경우 반환되어야 하는 기본값)
        	// 찾는 key가 존재하면 해당 key에 매핑되어 있는 값을 반환하고 그렇지 않으면 기본값(0)이 반환된다.
        	allGenre.put(genres[i], allGenre.getOrDefault(genres[i], 0) + plays[i]);
        }
        
        // 정렬하기 위해 list에다가 allGenre의 key값들 넣음
        ArrayList<String> list = new ArrayList<>(allGenre.keySet());
        // allGenre의 value를 기준으로 내림차순 정렬
        Collections.sort(list, (o1, o2) -> allGenre.get(o2) - allGenre.get(o1));
        
        // 베스트 앨범에 넣을 노래 선정
        ArrayList<Integer> bestAlbum = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
        	int max = 0;
        	int idx1 = 0;
        	// 1번째로 재생 횟수 많은 노래의 인덱스 구하기
        	for(int j = 0; j < genres.length; j++) {
        		if(list.get(i).equals(genres[j])) {
        			if(plays[j] > max) {
        				max = plays[j];
        				idx1 = j;
        			}
        		}
        	}
        	bestAlbum.add(idx1);
        	
        	max = 0;
        	int idx2 = -1;
        	// 2번째로 재생 횟수 많은 노래의 인덱스 구하기
        	for(int j = 0; j < genres.length; j++) {
        		if(list.get(i).equals(genres[j])) {
        			if(plays[j] > max && j != idx1) {
        				max = plays[j];
        				idx2 = j;
        			}
        		}
        	}
        	if(idx2 != -1) bestAlbum.add(idx2);
        }
        
        int[] answer = new int[bestAlbum.size()];
        for(int i = 0; i < bestAlbum.size(); i++) {
        	answer[i] = bestAlbum.get(i);
        }
        
        return answer;
    }
	
}
