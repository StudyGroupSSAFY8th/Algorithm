import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    private class Music implements Comparable<Music> {
        int id;
        int play;

        public Music(int id, int play) {
            this.id = id;
            this.play = play;
        }

        @Override
        public int compareTo(Music o) {
            return o.play - this.play;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        List<Integer> answer = new ArrayList<>();
        Map<String, PriorityQueue<Music>> musicByGenre = new HashMap<>();
        Map<String, Integer> playCount = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            int play = plays[i];

            if (!musicByGenre.containsKey(genre)) {
                musicByGenre.put(genre, new PriorityQueue<>());
                playCount.put(genre, 0);
            }

            musicByGenre.get(genre).add(new Music(i, play));
            playCount.replace(genre, playCount.get(genre) + play);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(playCount.entrySet());
        entries.sort((o1, o2) -> o2.getValue() - o1.getValue());

        List<String> keys = entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        for (String key : keys) {
            for (int i = 0; i < 2; i++) {
                if (!musicByGenre.get(key).isEmpty())
                    answer.add(musicByGenre.get(key).poll().id);
            }
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}