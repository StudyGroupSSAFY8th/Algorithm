package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class 코드트리_포탑부수기 {

    //최단 경로 찾기 위한 노드
    private static class Node{
        int x,y,count;

        Node preNode;

        public Node(int x, int y, int count, Node preNode){
            this.x = x;
            this.y = y;
            this.count = count;
            this.preNode = preNode;
        }
    }

    //포탑객체 - 포탑의 좌표, 포탑의 공격력,공격한 시점, 포탑의 공격여부, 피격여부
    private static class Turret{
        int x,y,power,time;
        boolean attackStatus, beAttackedStatus; // 공격 , 피격

        public Turret(int x, int y, int power){
            this.x = x;
            this.y = y;
            this.power = power;
            this.time = 0;
            this.attackStatus = false;
            this.beAttackedStatus = false;
        }

        //타워 상태 초기화 하기 - 직전에 공격여부, 공격 당한여부 초기화.
        public void initStatus(){
            this.attackStatus = false;
            this.beAttackedStatus = false;
        }

        //타워 고치기.
        public void fix(){

            if(!this.attackStatus && !this.beAttackedStatus) {
                this.power++;
            }
        }

        //공격자로 선정되어 공격력 증가
        public void powerUp(int n, int m){
          this.power += n + m;
        }

        //공격 + 공격한 시간 저장.
        public void attack(int time){
            this.attackStatus = true;
            this.time = time;
        }

        //피격
        public void beAttacked(int attackPower){

            //파워 감소
            this.power -= attackPower;

            //파워가 음수가 되면 0으로 만듦
            if(this.power < 0){
                this.power = 0;
            }

            //피격 표시
            this.beAttackedStatus = true;

        }

        @Override
        public String toString() {
            return "{" + this.power + "}";
        }
    }

    //게임판의 크기
    private static int N;
    private static int M;

    //반복횟수
    private static int K;

    //포탑이 놓여진 게임판
    private static Turret[][] maps;

    //우하좌상 대각선 이동 - 인덱스0~3까지는 상하좌우, 나머지는 대각선(포탄 공격일때)
    private static int[] dx = {0,1,0,-1,-1,-1,1,1};
    private static int[] dy = {1,0,-1,0,-1,1,-1,1};

    //모든 포탑정보를 가지고 있는 리스트 - 가장 강한 포탑과 약한 포탑을 구하기 위해서 정렬을 해야됨. - N*M => 최대 100이므로 nlogn을 1000번 반복해도 충분
    private static List<Turret> turretList;

    //테스트용 출력
    private static void print(){

        System.out.println("------------------------");
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                System.out.print(maps[i][j].toString() + ", ");
            }

            System.out.println();
        }
    }

    /* 모든 포탑을 돌면서 파워가 최대인 포탑 찾기.*/
    private static int maxPower(){

        int max = 0;

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                max = Math.max(max, maps[i][j].power);

            }
        }

        return max;
    }

    /* 모든 포탑을 돌면서, 부서지지 않은 포탑이 1개인지 확인*/
    private static boolean turretCheck(){

        int count = 0;

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                Turret turret = maps[i][j];

                //공격력이 0이면 부서진 포탑
                if(turret.power == 0) continue;

                count++;
            }
        }

        //확인했는데 부서지지 않은 포탑이 1개이면, true를 반환해서 바로 종료하도록 함.
        if(count == 1) return true;

        return false;
    }
    /*부서지지 않은 포탑정보(이전 공격 및 피격 여부)를 초기화 하는 메서드*/
    private static void statusInit(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                maps[i][j].initStatus();
            }
        }
    }

    /*정비*/
    private static void fixTurret(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                Turret turret = maps[i][j];

                //파워가 0이면 그냥 패스
                if(turret.power == 0) continue;

                //공격을 했거나 피격당하지 않은 포탑은 수리를 함.
                turret.fix();
            }
        }
    }
    /*리스트에서 처음으로 파워가 0보다 큰 객체 리턴*/
    private static Turret powerNotZero(Turret weakTurret){

        for(Turret turret : turretList){

            if(turret.power == 0) continue;

            //공격자라면 패스
            if(weakTurret != null && (weakTurret.x == turret.x && weakTurret.y == turret.y)) continue;

            return turret;
        }

        //파워가 0보다 큰 터렛이 존재하지 않는다면 잘못된 경우 - 발생할 수 없는 경우.
        return null;
    }

    /*가장 약한 포탑 선정하는 메서드
    * 1. 공격력이 가장 약함
    * 2. 가장 최근에 공격한 포탑
    * 3. 각 포탑의 위치의 행+열 합이 가장 큰 포탑
    * 4. 각 포탑의 위치의 열값이 가장 큰 포탑
    * */
    private static Turret weakTurret(){

        //조건에 맞게 리스트 정렬
        Collections.sort(turretList, (o1,o2) -> {

            if(o1.power == o2.power){

                if(o1.time == o2.time){

                    if((o2.x + o2.y) == (o1.x + o1.y)){
                        return o2.y - o1.y;
                    }

                    return (o2.x + o2.y) - (o1.x + o1.y);
                }

                return o2.time - o1.time;
            }

            return o1.power - o2.power;
        });

        //타워 공격력 증가
        Turret turret = powerNotZero(null);
        turret.powerUp(N,M);

        //타워 객체 반환
        return turret;
    }
    /*가장 강한 포탑 선정하는 메서드 - 파라미터로 공격자 객체를 받음(정렬 후 제외하기 위해서)
     * 1. 공격력이 가장 강함
     * 2. 공격한지 가장 오래된 포탑
     * 3. 각 포탑의 위치의 행+열 합이 가장 작은 포탑
     * 4. 각 포탑의 위치의 열값이 가장 작은 포탑
    * */
    private static Turret strongTurret(Turret weakTurret){
        //조건에 맞게 리스트 정렬
        Collections.sort(turretList, (o1,o2) -> {

            if(o1.power == o2.power){

                if(o1.time == o2.time){

                    if((o1.x + o1.y) == (o2.x + o2.y)){
                        return o1.y - o2.y;
                    }

                    return (o1.x + o1.y) - (o2.x + o2.y);
                }

                return o1.time - o2.time;
            }

            return o2.power - o1.power;
        });

        //타워 객체 반환.
        return powerNotZero(weakTurret);
    }
    /*레이저 공격*/
    private static void laserAttack(int currentTime, Turret attackTurret, Turret beAttackTurret){

        //피격자 경로가 담긴 노드 반환
        Node node = shortestPath(attackTurret, beAttackTurret);

        //공격자 공격 체크
        attackTurret.attack(currentTime);

        //피격자 감소
        beAttackTurret.beAttacked(attackTurret.power);

        //null이라면 포탄 공격
        if(node == null){
            boomAttack(attackTurret, beAttackTurret);
            return;
        }

        //null아니라면 레이저 공격 실행 - 반환된 노드부터 전부 처리.

        //그 다음 노드부터는 1/2만큼 피격
        Turret nextTurret = null;

        node = node.preNode;

        while(node.preNode != null){
            nextTurret = maps[node.x][node.y];
            nextTurret.beAttacked(attackTurret.power / 2);

            node = node.preNode;
        }
    }
    /*포탄 공격 - 피격자의 8방향은 공격자 파워의 절반, 피격자는 공격자의 파워만큼*/
    private static void boomAttack(Turret attackTurret, Turret beAttackTurret){


        //피격자 주변 1/2 감소
        for(int i = 0; i < 8; i ++){

            int nextX = (beAttackTurret.x + dx[i] + N) % N;
            int nextY = (beAttackTurret.y + dy[i] + M) % M;

            Turret nextTurret = maps[nextX][nextY];

            //해당 칸의 파워가 0 이거나, 공격자라면 패스.
            if(nextTurret.power == 0 || (attackTurret.x == nextX && attackTurret.y == nextY)) continue;

            nextTurret.beAttacked(attackTurret.power / 2);

        }

    }
    /*레이저 공격시, 최단 경로를 찾는 bfs - 출발지와 목적지를 정하고, 목적지까지 연결해놓은 마지막 노드를 반환.*/
    private static Node shortestPath(Turret attackTurret, Turret beAttackTurret){

        //시작 노드
        Node startNode = new Node(attackTurret.x, attackTurret.y, 0, null);

        //방문처리
        boolean[][] visited = new boolean[N][M];
        visited[startNode.x][startNode.y] = true;

        Queue<Node> needVisited = new LinkedList<>();
        needVisited.add(startNode);

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            if(currentNode.x == beAttackTurret.x && currentNode.y == beAttackTurret.y){
                return currentNode;
            }

            for(int i = 0; i < 4; i++){

                //한쪽방향으로 쭉가면 반대쪽으로 이어짐
                int nextX = (currentNode.x + dx[i] + N) % N;
                int nextY = (currentNode.y + dy[i] + M) % M;

                //0이 아니고 방문하지 않았다면
                if(maps[nextX][nextY].power != 0 && !visited[nextX][nextY]){
                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX,nextY,currentNode.count+1,currentNode));
                }
            }
        }

        //도달 할 수 없으면 null
        return null;
    }

    public static void main(String[] main) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        //맵에 터렛객체 만들고 리스트에 넣기
        maps = new Turret[N][M];
        turretList = new ArrayList<>();

        for(int i = 0; i < N; i++){

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){

                Turret turret = new Turret(i,j,Integer.parseInt(st.nextToken()));

                maps[i][j] = turret;

                if(turret.power == 0) continue;
                turretList.add(turret);
            }
        }

        int loopCount = 1;

        //최대 K번 반복
        while(loopCount <= K){
            //1. 공격자 선정
            Turret attackTurret = weakTurret();

            //2. 피격자 선정
            Turret beAttackTurret = strongTurret(attackTurret);

            //3. 공격자의 공격 - 기본적으로 레이저 공격, 안되면 포격
            laserAttack(loopCount, attackTurret, beAttackTurret);

            //4. 공격이 끝나면 부서지지 않은 포탑이 하나인지 확인 - 하나라면 종료.
            if(turretCheck()) break;

            //5. 포탑정비
            fixTurret();

            //6. 공격 or 피격 상태 초기화
            statusInit();

            loopCount++;
        }

        print();
        System.out.println("time : " + loopCount);

        //포탑 최대파워 구하기
        System.out.println(maxPower());

    }
}
