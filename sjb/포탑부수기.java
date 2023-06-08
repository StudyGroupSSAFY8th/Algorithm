import java.util.*;
import java.io.*;
public class 포탑부수기 {
    static class Pos{
        int r,c;
        int power;
        Pos(){
        }
        Pos(int r, int c){
            this.r = r;
            this.c = c;
        }
        Pos(int r, int c,int power){
            this.r = r;
            this.c = c;
            this.power = power;
        }
        @Override
        public String toString() {
            return this.r+","+this.c;
        }
    }
    
    public static Pos[] findAttack(){
        Pos attack = new Pos(0,0,Integer.MAX_VALUE);//공격할 녀석 = 약한녀석
        Pos target = new Pos(0,0,0);//대상자 = 강한녀석
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(map[i][j]==0) continue;

                //가장 약한 녀석 찾기
                if(attack.power>map[i][j]){
                    attack.power = map[i][j];
                    attack.r = i;
                    attack.c = j;
                }else if(attack.power==map[i][j]){
                    //최근 공격 - 가장 큰값인거
                    if(timeMap[attack.r][attack.c]<timeMap[i][j]){
                        attack.r = i;
                        attack.c = j;
                    }else if(timeMap[attack.r][attack.c]==timeMap[i][j]){
                        //행+열이 큰것
                        if(attack.r+attack.c < i+j){
                            attack.r = i;
                            attack.c = j;
                        }else if(attack.r+attack.c == i+j){
                            //열이 큰것
                            if(attack.c<j){
                                attack.r = i;
                                attack.c = j;
                            }
                        }
                    }
                }

                //가장 강한 녀석 찾기
                if(target.power<map[i][j]){
                    target.power = map[i][j];
                    target.r = i;
                    target.c = j;
                }else if(target.power==map[i][j]){
                    //최근 공격 - 가장 작은값
                    if(timeMap[target.r][target.c]>timeMap[i][j]){
                        target.r = i;
                        target.c = j;
                    }else if(timeMap[target.r][target.c]==timeMap[i][j]){
                        //행+열이 작은
                        if(target.r+target.c > i+j){
                            target.r = i;
                            target.c = j;
                        }else if(target.r+target.c == i+j){
                            //열이 작은것
                            if(target.c>j){
                                target.r = i;
                                target.c = j;
                            }
                        }
                    }
                }
            }
        }

        return new Pos[]{attack,target};
    }


    //길찾기
    public static boolean goRazer(Pos attack,Pos target){
        Pos[][] visited = new Pos[map.length][map[0].length];

        int[] dr = {0,1,0,-1};
        int[] dc = {1,0,-1,0};

        Queue<Pos> que = new ArrayDeque<>();
        que.add(attack);
        visited[attack.r][attack.c] = new Pos(attack.r,attack.c);


        boolean isEnd = false;
        while(!que.isEmpty()){
            Pos cur = que.poll();
            
            for(int d=0;d<4;d++){
                int zr = cur.r+dr[d];
                int zc = cur.c+dc[d];
                
                if(zr==-1){
                    zr = map.length-1;
                }
                if(zc==-1){
                    zc = map[0].length-1;
                }
                if(zr==map.length){
                    zr = 0;
                }
                if(zc==map[0].length){
                    zc = 0;
                }

                if(map[zr][zc]==0 || visited[zr][zc]!=null){
                    continue;
                }

                visited[zr][zc] = new Pos(cur.r,cur.c);
                que.add(new Pos(zr,zc));
                if(zr==target.r&&zc==target.c){
                    isEnd = true;
                    break;
                }
            }
            if(isEnd) break;
        }

        if(!isEnd){
            return isEnd;
        }
        boolean[][] check = new boolean[map.length][map[0].length];

        //레이저 총공격
        Pos next = target;

        int damage = attack.power + map.length + map[0].length;
        while(true){
            check[next.r][next.c]=true;
            if(next.r==attack.r&&next.c==attack.c){
                map[next.r][next.c] = damage;
                break;
            } 
            if(next.r==target.r&&next.c==target.c){
                map[next.r][next.c] -= damage;
                if(map[next.r][next.c]<0){
                    map[next.r][next.c]=0;
                }
                next = visited[next.r][next.c];
                continue;
            }
            map[next.r][next.c] -= Math.floor((double)damage/2);
            if(map[next.r][next.c]<0){
                map[next.r][next.c]=0;
            }
            next = visited[next.r][next.c];
        }

        //1씩 증가시키기
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]!=0 && !check[i][j]){
                    map[i][j]+=1;
                }
            }
        }

        return isEnd;
    }
    

    public static void goBumb(Pos attack,Pos target){
        int damage = attack.power + map.length + map[0].length;
        boolean[][] check = new boolean[map.length][map[0].length];
        check[attack.r][attack.c] = true;
        check[target.r][target.c] = true;
        //target 8
        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        map[attack.r][attack.c] = damage;
        map[target.r][target.c] -= damage;
        if(map[target.r][target.c]<0){
            map[target.r][target.c]=0;
        }

        for(int d=0;d<8;d++){
            int zr = target.r+dr[d];
            int zc = target.c+dc[d];
            if(zr==-1){
                zr = map.length-1;
            }
            if(zc==-1){
                zc = map[0].length-1;
            }
            if(zr==map.length){
                zr = 0;
            }
            if(zc==map[0].length){
                zc = 0;
            }

            check[zr][zc] = true;
            if(zr==attack.r&&zc==attack.c){
                continue;
            }
            if(map[zr][zc]!=0){
                map[zr][zc] -= Math.floor((double)damage/2);
                if(map[zr][zc]<0){
                    map[zr][zc]=0;
                }
            }
        }

        //1씩 증가시키기
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]!=0 && !check[i][j]){
                    map[i][j]+=1;
                }
            }
        }

    }

    static int[][] map;//공격력맵
    static int[][] timeMap;//언제 공격했는지 저장
    public static void main(String[] args) throws Exception{

        //공격하는 녀석
        //N+M만큼 공격력 증가
        //공격력낮은
        //최근 공격한 포탑
        //행+열이 가장큰포탑이 약한포탑
        //열이 가장 큰포탑이 약한 포탑


        //공격받는녀석
        //가장 강한 포탑 공격
        //공력력 가장 높은
        //공격한지 오래된 포탑
        //행과열의 합이 작은 포탑
        //열이 가장 작은 포탑


        //레이저 공격
        //우,하,좌,상
        //부서진곳이동X
        //가장자리는 반대로 이동가능
        //최단 경로
        //만약 없다면 포탄 공격으로
        //해당 포탑은 공격력 만큼, 이동경로 포탑은 절반만큼

        //포탄공격
        //8개 방향의 포탑도 피해를 입는다.
        //해당 포탑은 공격력 만큼, 이동경로 포탑은 절반만큼

        //0이되면 부서진다.
        

        //공격과 무관한 포탑은1씩 증가한다.


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        timeMap = new int[N][M];

        for(int i=0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<M;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int k=1;k<=K;k++){
            //찾아라
            Pos[] poses = findAttack();
            if(poses[0].r==poses[1].r&&poses[0].c==poses[1].c){
                break;
            }
            timeMap[poses[0].r][poses[0].c] = k;
            //레이저
            if(!goRazer(poses[0], poses[1])){
                goBumb(poses[0],poses[1]);
            }
            //printmap();
        
        }

        Pos[] poses = findAttack();
        System.out.println(map[poses[1].r][poses[1].c]);

    }

    public static void printmap(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println("");
        }
    }
}
