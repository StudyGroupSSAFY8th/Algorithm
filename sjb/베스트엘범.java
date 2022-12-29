import java.util.*;
import java.lang.*;
class Solution {
    class Song{
        int idx, cnt;
        Song(int idx, int cnt){
            this.idx = idx;
            this.cnt = cnt;
        }
        
        public String toString(){
            return "["+idx+","+cnt+"]";
        }
    }
    class Gen{
        String gen;
        int cnt;
        Gen(String gen, int cnt){
            this.gen = gen;
            this.cnt = cnt;
        }
        
        public String toString(){
            return "["+gen+","+cnt+"]";
        }
    }
    public int[] solution(String[] genres, int[] plays) {
        HashMap<String,Integer> gen_song= new HashMap<String,Integer>();
        HashMap<String,ArrayList<Song>> info= new HashMap<String,ArrayList<Song>>();

        //장르 / 재생수
        //장르갯수만큼 반복
        
        for(int i=0;i<genres.length;i++){
            if(gen_song.get(genres[i])==null){
                gen_song.put(genres[i],plays[i]);
                info.put(genres[i],new ArrayList<Song>());
                
                info.get(genres[i]).add(new Song(i,plays[i]));
            }else{
                gen_song.put(genres[i],gen_song.get(genres[i])+plays[i]);
                info.get(genres[i]).add(new Song(i,plays[i]));
            }
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        //info들 정렬
        info.forEach((key, value) -> {
            Collections.sort(info.get(key),(a,b)->b.cnt-a.cnt);
        });
        
        //장르별 정렬
        ArrayList<Gen> gens = new ArrayList<>();
        gen_song.forEach((key,value)->{
           gens.add(new Gen(key,value)); 
        });
        Collections.sort(gens,(a,b)->b.cnt-a.cnt);
        
        
        //정답 추출
        for(Gen ele:gens){
            
            ans.add(info.get(ele.gen).get(0).idx);
            
            //해당 장르에서 최대 두번
            if(info.get(ele.gen).size()>1){
                ans.add(info.get(ele.gen).get(1).idx);
            }
        }
        
        int[] answer = new int[ans.size()];
        
        for(int i=0;i<ans.size();i++){
            answer[i] = ans.get(i);
        }
        return answer;
    }

}
