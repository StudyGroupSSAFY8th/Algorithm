import java.util.*;

class Solution {
    long answer = 0;
    public long solution(int[] a, int[][] edges) {
        //모든 가중치를 0으로 만듭니다.
            //한쪽1증가, 한쪽1감소
        //가능한지 판별, 
        //가능하면 최소 몇번??
        
        //a는 가중치
        //edges는 연결
        
        long sum = 0;
        for(int i=0;i<a.length;i++){
            sum+=(long)a[i];
        }
        
        if(sum!=0) return -1;
        
        
        ArrayList<Integer>[] adjlist = new ArrayList[a.length];
        for(int i=0;i<a.length;i++){
            adjlist[i] = new ArrayList<>();
        }
        
        for(int i=0;i<edges.length;i++){
            //edges[i][0] -> edges[i][1];
            adjlist[edges[i][0]].add(edges[i][1]);
            adjlist[edges[i][1]].add(edges[i][0]);
        }
        Queue<Integer> leafs = new ArrayDeque<>();
        boolean[] visited = new boolean[a.length];
        int[] broCnt = new int[a.length];
        for(int i=0;i<adjlist.length;i++){
            broCnt[i] = adjlist[i].size();
            if(adjlist[i].size()==1){
                leafs.add(i);
            }
        }
        
        //맨끝으로 이동하고, 말단들을 모은다.
        
        //말단에서 시작해 위로 올라간다.
        long[] weight = new long[a.length];
        for(int i=0;i<a.length;i++){
            weight[i] = (long)a[i];
        }
        answer = goLeaf(adjlist,visited,leafs,weight,broCnt);

        return answer;
    }

    //이건 스택오버플로우로 런타임 에러
    public long back(int cur,ArrayList<Integer>[] list, boolean[] visited,long[] cnt){
        visited[cur] = true;
        long result = cnt[cur];//현재값
        //다음으로 이동가능한것 확인
        for(int i=0;i<list[cur].size();i++){
            if(!visited[list[cur].get(i)]){
                int next = list[cur].get(i);
                long tmp = back(next,list,visited,cnt);
                result+=tmp;
                answer+=Math.abs(tmp);
            }
        }
        return result;
    }
    
    
    
    
    public long goLeaf(ArrayList<Integer>[] list, boolean[] visited, Queue<Integer> leafs,long[] cnt, int[] broCnt){           
        long result = 0;
        while(!leafs.isEmpty()){
            int size = leafs.size();
            for(int i=0;i<size;i++){
                int cur = leafs.poll();
                if(broCnt[cur]!=1){
                    continue;
                }
				broCnt[cur] = 0;
                visited[cur] = true;
                if(cnt[cur]==0){
                    continue;
                }
                for(int s=0;s<list[cur].size();s++){
                    int next = list[cur].get(s);
                    if(!visited[next]){
                        //cur->next로 이동시킨다.
                        result+=(long)Math.abs((long)cnt[cur]);
                        
                        cnt[next] += (long)cnt[cur];
                        broCnt[next]-=1;
                        cnt[cur]=0;
                        if(cnt[next]!=0){
                            leafs.add(next);
                        }
                    }
                }
            }
        }
        
        return result;
        
    }
}