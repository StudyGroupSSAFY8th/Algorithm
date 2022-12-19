package week03;

/**
 * @author : sh Lee
 * @date : 22. 12. 19.
 */

/**
 * 아이디어 - 실패(dp를 구할때  그리디처럼 생각해서 틀림.)
 * 모든 연산순서를 다 확인해봐야 되기 떄문에 완탐문제이다
 * 주어진 조건을 보면,각 행렬의 크기는 200이하의 자연수이고, 행렬의 최대 개수는 200개 이므로,순열로 해결할 수 없다
 * 따라서 dp 또는 그리디를 이용해야 하는데, 특정행렬까지의 곱은, 이전 행렬과의 연관성이 있기 때문에 dp의 점화식을 이용할 수 있다
 * A B C 행렬이 있을때, C행렬까지의 곱은 A * BC , AB * C 이 두가지 순서가 있다.
 * 이를 응용하여 특정 행렬까지의 곱하기 횟수를, 위의 예시를 활용해서 점화식을 도출하여 해결한다.
 * AB일때 나오는 a by b 형태의 행렬 크기를 저장할 배열(matricDp)과, 곱의 횟수를 저장할 배열(dp) 두개를 만들어서 계산한다.
 */

/*
이 문제는 "연쇄행렬 최소곱셈 알고리즘을 이용해서 해결해야 한다."
참고: https://source-sc.tistory.com/24
 */
public class Programmers_최적의_행렬_곱셈 {
    public int solution(int[][] matrix_sizes) {
        int answer = 0;

        int length = matrix_sizes.length;
        int[][] dp = new int[length][length]; // dp[i][j] => i번째 행렬부터 j번째 행렬까지 최소 연산 횟수

        //i번째 a by b 행렬을 matrix[i][0] = a, matrix[i][1] = b 이라고 표현할때 점화식은 아래와 같다
        //dp[i][j] = Math.min(dp[i][j],dp[i][k] + dp[k+1][j] + matrix_sizes[i][0] *matrix_sizes[k][1] * matrix_sizes[j][1]) (i <= k < j)

        //곱셈횟수의 최소값을 구해야 하기 떄문에 최대값으로 초기화
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length - i; j++){
                int a = j;
                int b = j+i;

                //두개가 같은 행렬이면 곱셈 횟수 구할 필요 없음.
                if(a == b) dp[a][b] = 0;

                else{
                    for(int k = a; k < b; k++){
                        dp[a][b] = Math.min(dp[a][b],dp[a][k] + dp[k+1][b] + matrix_sizes[a][0] *matrix_sizes[k][1] * matrix_sizes[b][1]);
                    }
                }
            }
        }


        //0번째에서 length-1번째(마지막)까지 행렬 곱의 최소 횟수
        return dp[0][length-1];
    }
    /*
    public int solution(int[][] matrix_sizes) {
        int answer = 0;

        Matrix[] matrixDp = new Matrix[matrix_sizes.length]; // 행렬의 a by b 형태를 저장해두는 배열
        int[] dp = new int[matrix_sizes.length]; //해당 위치까지의 곱 횟수.

        matrixDp[0] = new Matrix(matrix_sizes[0][0], matrix_sizes[0][1]);
        matrixDp[1] = new Matrix(matrix_sizes[1][0], matrix_sizes[1][1]);

        for(int i = 2; i < matrix_sizes.length; i++){

            //짝수 인덱스 일때는 앞의 두개를 확인해야됨 - 두가지 경우가 발생.
            if(i % 2== 0){

                //i-2 * ((i-1) * i)
                int case1 = (matrixDp[i-2].a * matrixDp[i-1].a * matrix_sizes[i][1]) + (matrixDp[i-1].a * matrixDp[i-1].b * matrix_sizes[i][1]);

                //((i-2) * (i-1)) * i
                int case2 = (matrixDp[i-2].a * matrixDp[i-2].a * matrixDp[i-1].b) + (matrixDp[i-2].a * matrixDp[i-1].b * matrix_sizes[i][1]);

                dp[i] = dp[i-2] + Math.min(case1,case2); //둘중 더 작은 쪽으로 업데이트

                matrixDp[i] = new Matrix(matrix_sizes[i-2][0], matrix_sizes[i][1]); //행렬 모양 업데이트.

            }
            //홀 수 인덱스 일때는 볼 필요 없음 - 해당 행렬의 크기만 저장 해둠
            else{
                matrixDp[i] = new Matrix(matrix_sizes[i][0], matrix_sizes[i][1]);
            }
        }

        return dp[matrix_sizes.length - 1];
    }

    static class Matrix{
        int a, b; //  a by b 형태

        Matrix(int a, int b){
            this.a = a;
            this.b = b;
        }
    }

    */

    public static void main(String[] args) {
        Programmers_최적의_행렬_곱셈 s = new Programmers_최적의_행렬_곱셈();

        int[][] matrix_sizes = {{5,3},{3,10},{10,6}};
        int result = 270;

        System.out.println(s.solution(matrix_sizes));

    }
}
