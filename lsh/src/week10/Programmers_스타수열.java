package week10;

import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 1. 모든 집합의 교집합의 원소가 1개이상이어야 하므로, 하나의 숫자를 잡고 탐색을 함.
 *  맨 앞부터 하나씩 기준으로 잡고 반복해봄.
 * 2. 탐색 과정에서 교집합 원소로 선택한 수라면, 그 다음 수를 보면 되지만, 교집합으로 선택한 수가 아니라면,
 * 교집합으로 선택한 수가 나올때까지 이동함
 * 3. 이 과정을 반복하면서 원소의 개수를 저장해둠.
 * 4.초기에 모든 원소들의 수를 Map으로 저장해두고, 갯수가 가장 많은 것 부터 계산함
 * 5. 가장 많이 나온 원소를 교집합 원소로 하는 수가 가장 긴 스타수열이 될 확률이 높음.
 *
 * 아이디어 구상에서 살짝막혀서 아이디어 참고함.
 * https://velog.io/@ujone/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EC%8A%A4%ED%83%80-%EC%88%98%EC%97%B4-JAVA
 */

public class Programmers_스타수열 {
    public int solution(int[] a) {

        /*수가 몇개 나왔는지 저장 - 탐색과정에서 스타수열 길이보다 수의 개수가 작다면 탐색이 필요가 없음.*/
        Map<Integer,Integer> numCount = new HashMap<>();

        for(int num : a){
            numCount.put(num,numCount.getOrDefault(num,0) + 1);
        }


        //스타수열의 부분집합의 갯수(2개씩 묶었을때의 개수)
        int answer = 0;

        //앞에서 부터 수 하나를 교집합원소로 나타나야 되는 수로 잡음 - 해당 수가 안나타났다면 잘못된것.
        for(int stdNum : numCount.keySet()){

            //해당 원소의 개수가 이전까지 저장된, 부분집합의 개수보다 작거나 같고, 또는  탐색할 필요 없음
            if(answer >= numCount.get(stdNum)) continue;


            int tempSetCount = 0; // 부분집합의 수 구하기.
            //2개씩 묶어서 확인하기 위해서 (길이 - 1)까지만 탐색.
            for(int i = 0; i < a.length - 1; i++){

                //i번째와 i+1 번째 둘다 stdNum이 아니라면 패스해야됨.
                if(a[i] != stdNum && a[i+1] != stdNum) continue;

                //각 집합내의 수는 달라야 함
                if(a[i] == a[i+1]) continue;

                //위의 조건에 부합하지 않았으면 가능한 부분집합임
                tempSetCount++; //부분집합의 개수 증가
                i++; //인덱스 증가 - 두칸 뛰어넘어야 되기 때문에 한번더 증가 시킴.
            }

            //이전에 저장된 스타수열의 부분집합 수와 새로구한 부분집합의 수를 비교해서 큰쪽으로 업데이트
            answer = Math.max(answer,tempSetCount);
        }


        return answer * 2;
    }

    public static void main(String[] args) {
        Programmers_스타수열 s = new Programmers_스타수열();
        int[] a = {0};
        System.out.println(s.solution(a));

        int[] a1 = {5,2,3,3,5,3};
        System.out.println(s.solution(a1));

        int[] a2 = {0,3,3,0,7,2,0,2,2,0};
        System.out.println(s.solution(a2));


    }
}
