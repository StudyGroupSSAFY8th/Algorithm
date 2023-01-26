package week08;

/**
 * @author : sh Lee
 * @date : 23. 1. 22.
 */

/**
 * 아이디어 - 아이디어성 문제
 * 처음 봤을때 완전탐색이라고 생각했지만, 데이터의 개수가 1000000이라서 완탐을 돌릴 수 없고, 디피를 이용해야 된다고 생각했다.
 * 디피는 최소값이나 최대값을 구할때, 중복되는 연산을 메모이제이션을 통해서 줄이는 것인데, 이문제는 가능한 수를 전부 구해야 된다고 했다.
 * 그래서 다르게 접근해야 되어서 풀지 못했다.
 *
 * 아래의 블로그를 참고해서 다음과 같이 접근했다.
 * 양쪽 끝 숫자는 항상 가능한 숫자들이다, 나머지 숫자들이 어떤 방식으로든 1개만 남기면 왼쪽끝, 남은 수, 오른쪽끝  이와 같이 남게 되는데,
 * 이때 작은 수를 선택하던, 큰수를 선택하는 식으로 하든, 어떤식으로든 양쪽의 끝수는 남을 수 있다
 * 그렇다면 나머지 수는 다음과 같이 생각했다.
 * 12 15 11 이와같이 양쪽에 모두 작은 수가 있으면 15는 선택되지 않는다, 작은 수를 선택하는 경우는 오직 한번이기 때문이다.
 * 따라서 이런식으로 수를 하나 생각하고, 해당위치를 기준으로 왼쪽에 있는 수 중, 최소값을 찾고, 오른쪽에 있는 수 중 최소값을 찾아서,
 * 양쪽의 최소값이 둘다 내가 선택한 수 보다 작다면, 내가 선택한 수는 어떤방법으로든 삭제가 불가능하다.
 *
 * 참고 : https://tosuccess.tistory.com/167
 */
public class Programmers_풍선_터트리기 {

    static int[] leftMin; //왼쪽의 최소값, i번째에는 i번째 수의 왼쪽의 최소값이 저장됨.
    static int[] rightMin; //오른쪽의 최소값,

    //특정 값을 선택했을 때 왼쪽부분의 최소값 구하기
    static void setLeftMin(int[] a){

        int min = Integer.MAX_VALUE;

        for(int i = 0; i < a.length; i++){

            if(min > a[i]) min = a[i];

            leftMin[i] = min;
        }

    }
    //오른쪽부분의 최소값 구하기
    static void setRightMin(int[] a){

        int min = Integer.MAX_VALUE;

        for(int i = a.length - 1; i >= 0; i--){

            if(min > a[i]) min = a[i];

            rightMin[i] = min;
        }
    }

    //특정값을 선택 했을 때 오른쪽 부분의 최소값 구학.

    public int solution(int[] a) {
        int answer = 2; //양쪽은 확정

        leftMin = new int[a.length];
        rightMin = new int[a.length];

        setLeftMin(a); //왼쪽 최소 셋팅
        setRightMin(a); //오른쪽 최소 셋팅

        //양쪽끝은 확정이므로 1번째 ~ (a.length - 2)번째를 계산해봄
        for(int i = 1; i < a.length - 1; i++){

            //양쪽의 최소값이 현재 선택한 값보다 작다면 해당 값은 마지막까지 남을 수 없음.
            if(leftMin[i] < a[i] && rightMin[i] < a[i]) continue;

            answer++;
        }
        return answer;
    }
}
