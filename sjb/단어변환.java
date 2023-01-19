import java.util.*;
class Solution {
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        //가장 짧은 변환 과정
        
        //hash로 word에서 다음으로 되는 word를 저장한다.
        HashMap<String,ArrayList<String>> adjList = new HashMap<>();
        
        //words에 target이 있는지 확인
        boolean check = false;
        
        //begin부터 확인
        for(int i=0;i<words.length;i++){
            if(check(begin,words[i])){
                //begin->words[i], words[i]->begin
                if(adjList.get(begin)==null){
                    adjList.put(begin, new ArrayList<String>());
                }
                adjList.get(begin).add(words[i]);
                
                if(adjList.get(words[i])==null){
                    adjList.put(words[i], new ArrayList<String>());
                }
                adjList.get(words[i]).add(begin);
            }
            //target포함 확인
            if(words[i].equals(target)){
                check = true;
            }
        }
        if(!check) return 0;
        
        //나머지 진행
        for(int i=0;i<words.length;i++){
            for(int j=i+1;j<words.length;j++){
                if(check(words[i],words[j])){
                    //begin->words[i], words[i]->begin
                    if(adjList.get(words[i])==null){
                        adjList.put(words[i], new ArrayList<String>());
                    }
                    adjList.get(words[i]).add(words[j]);

                    if(adjList.get(words[j])==null){
                        adjList.put(words[j], new ArrayList<String>());
                    }
                    adjList.get(words[j]).add(words[i]);
                }
            }
        }
        
        answer = bfs(begin, target, adjList);
        
        return answer;
    }
    //bfs
    public int bfs(String begin,String target, HashMap<String,ArrayList<String>> adjList ){
        Queue<String> que = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        visited.add(begin);
        que.offer(begin);
        
        int depth=0;
        boolean isTarget = false;
        while(!que.isEmpty()){
            int size = que.size();
            // System.out.println(que);
            // System.out.println(depth);
            for(int i=0;i<size;i++){
                String cur = que.poll();
            
                //도달 확인
                if(cur.equals(target)){
                    isTarget = true;
                    break;
                }
                
                //다음으로 이동
                for(int n=0;n<adjList.get(cur).size();n++){
                    String next = adjList.get(cur).get(n);
                    if(!visited.contains(next)){
                        visited.add(next);
                        que.offer(next);
                    }
                }
            }
            
            
            
            if(isTarget){
                break;
            }
            depth+=1;
        }
    
        
        
        
        return depth;
    }
    
    //두 단어 확인
    public boolean check(String a, String b){
        int cnt =0;
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)!=b.charAt(i)){
                cnt+=1;
            }
        }
        if(cnt==1){
            return true;
        }
        return false;
    }
}