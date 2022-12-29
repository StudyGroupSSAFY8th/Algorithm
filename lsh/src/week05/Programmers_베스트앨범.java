package week05;

/**
 * @author : sh Lee
 * @date : 22. 12. 29.
 */

import java.util.*;

/**
 * 아이디어
 * 1. 각 장르별로 총 몇회인지, 어떤 장르인지 객체에 저장후에 플레이 횟수로 정렬해준다.
 * 2. 각 장르별로 해쉬맵을 이용해서 플레이 된 횟수와 번호를 번호순서로 저장한다.
 * 이중 반복문을 쓰지 않아서(장르별 2개 뽑아낼때 이중 반복문이 있지만, 데이터 수에 따라 달라지는 것이 아닌 2로 고정된 횟수라 상관없음), O(n)이 나오고,
 * 데이터가 10000개라서 충분히 시간내에 통과한다.
 */

/**
 * 실수한 부분 - genreInfo을 추가할때, 기존에 없는 key이라면 value로 리스트 객체를 생성후, 값을 넣어야 하는데, 객체 생성후 값을 넣지 않았음(주의)
 */
public class Programmers_베스트앨범 {
    public int[] solution(String[] genres, int[] plays) {

        //각 장르별로 플레이 횟수에 따라 정렬시켜놓음 - 내림차순 정렬
        Map<String, List<GenrePlayCountNum>> genreInfo = new HashMap<>();

        //모든 장르의 총 플레이 횟수에 따라 저장 - 내림차순 정렬해서 플레이 횟수가 많은 장르를 먼저 꺼내도록 함.
        //아래 로직에서 반복문을 돌면서 플레이 횟수를 누적시켜야되기 때문에 해쉬 사용
        Map<String, GenrePlayCount> genreTotal = new HashMap<>();

        for(int i = 0; i < genres.length; i++){
            //이전에 저장된 적 있으면 그냥 추가
            if(genreInfo.containsKey(genres[i])){
                //해당 장르 번호와, 플레이 횟수 저장.
                genreInfo.get(genres[i]).add(new GenrePlayCountNum(i, plays[i]));
            }
            else{
                genreInfo.put(genres[i], new ArrayList<>());
                genreInfo.get(genres[i]).add(new GenrePlayCountNum(i, plays[i]));
            }

            //해당 장르 총 플레이 횟수 누적
            if(genreTotal.containsKey(genres[i])){
                genreTotal.get(genres[i]).playCount += plays[i];
            }
            else{
                genreTotal.put(genres[i], new GenrePlayCount(genres[i], plays[i]));
            }
        }

        //총 몇개의 노래가 나올지 모르기 떄문에 리스트로 만듦
        List<Integer> answer = new ArrayList<>();

        //플레이 횟수가 가장 큰 장르부터 꺼냄.
        List<GenrePlayCount> playCountObjs = new ArrayList<>(genreTotal.values());
        playCountObjs.sort(Comparator.naturalOrder());

        //하나씩 꺼내서 각 장르별로 플레이 횟수를 모아놓은 맵을 조회하고, 정렬후에, 최대 2개까지 뽑아서 저장함.
        for(GenrePlayCount playCountObj : playCountObjs){

            List<GenrePlayCountNum> tempList = genreInfo.get(playCountObj.genre);
            tempList.sort(Comparator.naturalOrder()); //큰 순서로 뽑을 수 있도록 정렬.

            int count = 0; // 최대 2개까지 뽑을 수 있도록 함.

            for(GenrePlayCountNum temp : tempList){
                answer.add(temp.genreNum);
                count++;

                //2개까지만 뽑도록, 2개가 되면 종료.
                if(count == 2) break;
            }
        }

        int[] returnAnswer = new int[answer.size()];

        for(int i = 0; i < returnAnswer.length; i++){
            returnAnswer[i] = answer.get(i);
        }

        return returnAnswer;
    }

    static class GenrePlayCount implements Comparable<GenrePlayCount>{
        String genre;
        int playCount;

        GenrePlayCount(String genre, int playCount){
            this.genre = genre;
            this.playCount = playCount;
        }

        @Override
        public int compareTo(GenrePlayCount o) {
            return o.playCount - this.playCount;
        }

        @Override
        public String toString() {
            return "GenrePlayCount{" +
                    "genre='" + genre + '\'' +
                    ", playCount=" + playCount +
                    '}';
        }
    }

    //해당 장르의 번호와 인덱스 번호를 저장(장르별로 플레이 횟수가 많은것을 먼저 처리하기 위해)
    static class GenrePlayCountNum implements Comparable<GenrePlayCountNum>{
        int genreNum,playCount;

        GenrePlayCountNum(int genreNum, int playCount){
            this.genreNum = genreNum;
            this.playCount = playCount;
        }

        @Override
        public int compareTo(GenrePlayCountNum o) {
            return o.playCount - this.playCount;
        }

        @Override
        public String toString() {
            return "GenrePlayCountNum{" +
                    "genreNum=" + genreNum +
                    ", playCount=" + playCount +
                    '}';
        }
    }

    public static void main(String[] args) {
        Programmers_베스트앨범 s = new Programmers_베스트앨범();

        String[] genres = {"classic", "pop", "classic", "classic", "pop"};

        int[] plays = {500, 600, 150, 800, 2500};

        System.out.println(Arrays.toString(s.solution(genres,plays)));

    }

}
