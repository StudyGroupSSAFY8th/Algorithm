package week03;

/**
 * @author : sh Lee
 * @date : 22. 12. 15.
 */
public class Programmers_자물쇠와_열쇠 {

    static int N;
    static int M;

    static int mapLen;
    static int[][] maps;

    //자물쇠 원상복구 시키는 메서드
    static void initMap(int[][] lock){
        for(int i = M-1; i < N+M-1; i++){
            for(int j = M-1; j < N+M-1; j++){
               maps[i][j] = lock[i - (M-1)][j - (M-1)];
            }
        }
    }

    //열쇠와 자물쇠에 딱 맞았는지 확인하는 메서드 - 자물쇠부분에 표시된 값이 전부 1이여야됨(돌기1, 들어간부분0)
    static boolean check(){

        for(int i = M-1; i < N+M-1; i++){
            for(int j = M-1; j < N+M-1; j++){
                if(maps[i][j] == 0 || maps[i][j] == 2){
                    return false;
                }
            }
        }

        return true;
    }


    //배열 90도 회전후 회전된 배열 반환.
    static int[][] rotateArray(int[][] key){
        int[][] returnKey = new int[M][M];

        for(int i = 0; i < M; i++){
            for(int j = 0; j < M; j++){
                returnKey[j][M-i-1] = key[i][j];
            }
        }

        return returnKey;
    }

    //한칸씩 이동하면서 열쇠와 자물쇠가 딱 맞는지 확인
    static boolean moveAndCheck(int[][] lock, int[][] key){

        //열쇠 크기 만큼 반복 - (0~열쇠길이, 1~열쇠길이) 이런식으로 돌면서 자물쇠를 확장한 maps에 열쇠+자물쇠값을 저장함.
        for(int i = 0; i < mapLen - (M-1); i++){
            for(int j = 0; j < mapLen - (M-1); j++){

                //열쇠를 더하기 전에 lock영역 초기화 필요 - 이전에 누적된 값이 있을 수 있음
                initMap(lock);

                //열쇠 영역을 maps에 더해서 저장함.
                for(int x = 0; x < M; x++){
                    for(int y = 0; y < M; y++){
                        maps[x+i][y+j] += key[x][y];
                    }
                }

                //더해서 저장하는 과정이 끝나면 열쇠로 자물쇠를 열수 있는 지 확인
                if(check()) return true;

            }
        }

        //끝까지 다 돌았는데도 중간에 한번도 true가 반환되지 않았다면 열쇠로 자물쇠를 열 수 없는 것.
        return false;

    }

    public boolean solution(int[][] key, int[][] lock) {

        N = lock.length;//자물쇠의 한변의 길이
        M = key.length;//열쇠의 한변의 길이

        //열쇠를 자물쇠에 하나씩 맞춰보면서 완탐 돌려야 됨
        //하나씩 맞춰보는 연산을 위해서 자물쇠 격자판의 크기를 키움
        mapLen = N * (M-1) * 2;

        maps = new int[mapLen][mapLen];

        //0도,90도, 180도,270도, 회전했을때 열쇠를 반복문을 돌면서 자물쇠에 맞춰봄 -
        for(int i = 0; i < 4; i++){

            if(moveAndCheck(lock,key)) return true;

            key = rotateArray(key);

        }

        return false;
    }
}
