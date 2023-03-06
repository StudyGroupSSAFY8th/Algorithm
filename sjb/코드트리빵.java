import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	
	static class Node{
		int r,c;

		public Node(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

		@Override
		public String toString() {
			return "Node [r=" + r + ", c=" + c + "]";
		}
		
	}
	public static void main(String[] args) throws IOException {

		//1분마다 번호순
		//n*n 격자
		//123순으로 진행
			//편의점 방향으로 1칸
				//최단 거리로 이동, 상,좌,우,하 우선순위
			//도착하면, 편의점에서 멈춤, 해당 편의점으로는 못지나감
			//현재 자기번호 시간이면 t번 사람은 자기가 가고싶은 편의점과 가장 가까운 베이스켐프로 이동
				//같으면 행이 작은, 열이 작은 순으로 이동
				//시간 소요x
				//해당 베이스 캠프는 못지나간다.

		//지도
		//베이스캠프 위치
		//편의점 위치

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());//지도크기
		int m = Integer.parseInt(st.nextToken());//편의점개수 = 사람갯수

		ArrayList<Node> camps = new ArrayList<>();
		//1은 베이스 캠프
		int[][] map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]==1) {
					//베이스 캠프 저장
					camps.add(new Node(i,j));
				}
			}
		}

		//편의점위치 저장
		Node[] pyon = new Node[m];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			pyon[i]=new Node(r-1,c-1);//-1을 해준다.
		}
		
		ArrayList<Node> people = new ArrayList<>();


		//-----------------------------------------------------------확인
//		map[0][1] = -1;
//		int [] tt = bfs(0,1,new Node(1,1),map,camps);
//		for(int i=0;i<map.length;i++) {
//			for(int j=0;j<map.length;j++) {
//				System.out.print(map[i][j]);
//			}
//			System.out.println("");
//		}
//		System.out.println("=");
//		System.out.println(tt[0]+","+tt[1]);
		codetree(people,camps,pyon,map,m);
		
	}
	public static void codetree(ArrayList<Node> people,ArrayList<Node> camps,Node[] pyon,int[][] map, int m) {
		int time = 1;
		int cur = 0;//현재 출발할 사람
		
		while(true){
			
			cur = 0;
//			System.out.println("--------------------------"+time);
//			System.out.println(time);
			//이미 있는 사람들은 이동
			int endCnt = 0;
			for(int i=0;i<people.size();i++){
//				System.out.println("사람 이동중"+cur+":"+people.get(cur).r+","+people.get(cur).c);
				
				//이동
				//cur번 사람을 이동시킨다.
				//이미 편의점 위치인지 확인
				if(pyon[cur].r==people.get(cur).r && pyon[cur].c==people.get(cur).c) {
					cur+=1;
//					System.out.println("도착함");
					endCnt+=1;
					continue;
				}
				int[] next = bfs(people.get(cur).r,people.get(cur).c,pyon[cur],map,camps);
//				System.out.println("이동하고나서"+next[0]+","+next[1]);
				people.set(i,new Node(next[0],next[1]));
				
				//도착 여부 확인
				if(pyon[cur].r==people.get(cur).r && pyon[cur].c==people.get(cur).c) {

					map[next[0]][next[1]] = -1;
					endCnt+=1;
				}
				cur+=1;
			}
			
			//이제 이동할 사람이 더 없을겨우 종료
			if(endCnt==m) {
				break;
			}
			time+=1;
			
			if(cur<m){
//				System.out.println("출동");
				//출동
				int[] bcPos = initBfs(pyon[cur],map,camps);
				map[bcPos[0]][bcPos[1]] = -1;
				people.add(new Node(bcPos[0],bcPos[1]));
//				System.out.println(cur+"번의 사람이 가야할 베이스는");
//				System.out.println(bcPos[0]+","+bcPos[1]);
			}
			
		}
		System.out.println(time);
	}
	
	public static int[] initBfs(Node pyon, int[][] map, ArrayList<Node> camps) {
		//n번의 사람이 가고싶은 편의점에서 제일 가까운 베이스 캠프 찾기
		Queue<Node> que = new ArrayDeque<>();
		que.add(pyon);
		boolean visited[][] = new boolean[map.length][map.length];
		visited[pyon.r][pyon.c] = true;
		
		int[] dr = {-1,1,0,0};
		int[] dc = {0,0,-1,1};

		int[] result = {map.length,map.length};
		while(!que.isEmpty()) {
			int size = que.size();
			for(int i=0;i<size;i++) {
				Node cur = que.poll();
//			System.out.println(cur);
				//다음 이동
				
				for(int d=0;d<4;d++) {
					int zr = cur.r+dr[d];
					int zc = cur.c+dc[d];
					if(zr>=0&&zc>=0&&zr<map.length&&zc<map.length) {
						//map이 -1이면 이동 불가
						if(map[zr][zc]==-1) {
							continue;
						}
						visited[zr][zc] = true;
						que.add(new Node(zr,zc));
						if(map[zr][zc]==1) {
							//행이 작은지
							if(zr<result[0]) {
								result[0] = zr;
								result[1]=zc;
							}else if(zr==result[0]) {
								//열이 작은지
								if(zc<result[1]) {
									result[0]=zr;
									result[1]=zc;
								}
							}
						}
					}
				}
			}
			
			if(result[0]!=map.length) {
				break;
			}
		}
		
		
		return result;
	}
	
	public static int[] bfs(int r, int c,Node pyon, int[][] map, ArrayList<Node> camps) {
		Queue<Node> que = new ArrayDeque<>();
		que.add(pyon);
		//pyon -> r,c
		//편의점에서부터 해당 r,c로 까지의 최단거리 구하기
		//아래, 오른쪽, 왼쪽, 위 순으로
		int[] dr = {1,0,0,-1};
		int[] dc = {0,1,-1,0};
//		System.out.println("시작"+pyon.r+","+pyon.c);
		boolean[][] visited = new boolean[map.length][map.length];
		visited[pyon.r][pyon.c] = true;
		
		boolean isEnd = false;
		int[] result = {0,0};
		int time = 0;
		while(true) {
			int size = que.size();
			for(int i=0;i<size;i++) {
				
				Node cur = que.poll();
//				System.out.println("지금"+cur);
				
				for(int d=0;d<4;d++) {
					int zr = cur.r+dr[d];
					int zc = cur.c+dc[d];
					if(zr>=0&&zc>=0&&zr<map.length&&zc<map.length) {
						if(!visited[zr][zc]) {
							visited[zr][zc] = true;
							
							if(map[zr][zc]==-1) {
								//베이스 캠프인데 내가 있는 경우
//								System.out.println("베이스캠프임");
								//도착했는지 확인
								if(zr==r&&zc==c) {
									isEnd = true;
									if(time==0) {//한방에 갔을때
//										System.out.println("한방에 옴");
//										System.out.println(zr);
//										System.out.println(zc);
										result[0] = cur.r;
										result[1] = cur.c;
									}else {							
//										System.out.println("여기 근처임");
										result[0] = cur.r;
										result[1] = cur.c;
									}
									break;
								}
							}else {								
								//베이스캠프가 아니지만, 바로 옆에 있는 경우
								if(zr==r&&zc==c) {
									isEnd = true;
									if(time==0) {//한방에 갔을때
//										System.out.println("한방에 옴");
//										System.out.println(zr);
//										System.out.println(zc);
										result[0] = cur.r;
										result[1] = cur.c;
									}else {							
//										System.out.println("여기 근처임");
										result[0] = cur.r;
										result[1] = cur.c;
									}
									break;
								}
								que.add(new Node(zr,zc));
							}
						}
					}
				}
				
				if(isEnd) {
					break;
				}
			}
			time=1;
			
			if(isEnd) {
				break;
			}
			
		}
		
		return result;
	}

}
