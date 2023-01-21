package week07;

/**
 * @author : sh Lee
 * @date : 23. 1. 21.
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 아이디어
 * 전형적인 디피이다
 * 모든 경우를 다 해봐야 한다.
 * 숫자 1개를 썼을때, 2개를 썼을때....8개를 사용했을때까지 구한다
 * 2개를 사용했을때는 1개 (연산자) 1개,  3개를 사용했을때, 1개 (연산자) 2개 이런식으로 구하면 된다.
 */
public class Programmers_N으로_표현 {
    public int solution(int N, int number) {


        //구한 수를 저장할 배열 - 각 원소로는 set자료구조를 가지게 된다.
        Set<Integer>[] dp = new HashSet[9]; // 인덱스는 1~8까지 사용
        for(int i = 1; i < 9; i++){
            dp[i] = new HashSet<>();
        }

        //초기 값 채워넣기 - 1이면 5, 2면 55, 3이면 555, 4이면 5555, 5이면 55555
        int preNum = 0;

        for(int i = 1; i < 9; i++){

            int initNum = preNum + (int)Math.pow(10,i-1) * N;
            preNum = initNum;

            dp[i].add(initNum);
        }

        for(int i = 2; i <= 8; i++){
            for(int j = 1; j < i; j++){

                //수 3개를 썼을때는 - (1,2),(2,1)를 구하면 된다, (1,1,1) 이 경우는 수 2개를 썼을때 (1,1)가 들어가 있기 때문에 구할 필요 없다
                //이렇게 이전에 구한 것을 다시 구하지 않기 때문에 디피가 된다.
                for(Integer a : dp[j]){
                    for(Integer b : dp[i-j]){

                        //빼기나, 나누기 처럼 순서가 바뀌면 결과가 바뀌는 경우를 따질 필요 없다.
                        //반복문을 다 돌면  1-2, 2-1 이 두가지 경우 모두 따지기 때문에.
                        dp[i].add(a+b);
                        dp[i].add(a-b);
                        dp[i].add(a*b);

                        if(b != 0) dp[i].add(a/b);
                    }
                }

            }
        }


        //반복문을 돌면서 number를 만드는 최소 횟수 찾기.
        for(int i = 1; i < 9; i++){
            if(dp[i].contains(number)) return i;
        }

        return -1;
    }

    public static void main(String[] args) {
        Programmers_N으로_표현 s = new Programmers_N으로_표현();

        int N = 5;
        int number = 12;
        System.out.println(s.solution(N,number));


        int N1 = 2;
        int number1 = 11;
        System.out.println(s.solution(N1,number1));
    }
}
