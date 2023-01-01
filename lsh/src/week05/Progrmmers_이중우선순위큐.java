package week05;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author : sh Lee
 * @date : 22. 12. 31.
 */
/*
아이디어
1. 우선순위큐는 maxQueue, minQueue둘중 하나만 할 수 있기 때문에, 최소 최대를 골라낼수 있는 힙이 두개 필요하다.
2. 최대힙에서 뺀 값이 최소힙에서도 업데이트 되어야하기 때문에, 별도로 Map을 두어, 값을 저장하거나 뺄때 마다 업데이트를 해준다
3. 문제에서 숫자가 중복없이 들어온다는 소리가 없기 때문에 set으로 할 수 없고, 또한 입력되는 수가 음수가 존재하고, 최대 몇까지인지 나와있지 않기 때문에 배열을 사용할 수 없다.
4. Map을 이용해서 해당 숫자를 카운트 하고, 삭제연산이 들어올때마다 카운트를 감소시킨다.
5. 해당 수의 카운트가 0이 되면, 메모리를 줄이기 위해서 삭제를 한다.
=> 자바는 최소힙, 최대힙을 구현할때, 클래스를 사용하여, compareable을 구현하고 만들어 주면 된다.(또는 그냥 익명 클래스로 comparator 구현)
문제점 : 최소 또는 최대값을 삭제하려고 할때, 여러개라면 문제 발생 - 한쪽에서 빼고, 다른쪽에서 뺴려고 할때, 몇개나 뺀 상태인지 알방법이 없음.

단순하게 한쪽에서 poll한 값을 다른쪽에서 remove연산으로 삭제해버린다.
pq에서 제공하는 remove메서드는 파라미터로 삭제할 원소를 받는다.
https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/PriorityQueue.html#remove(java.lang.Object)
 */

public class Progrmmers_이중우선순위큐 {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        //입력형태는 {명령어} {데이터} => 공백을 기준으로 split해서 명령어와 데이터를 구분해야됨.
        for(String operation : operations){
            String[] splitOperation = operation.split(" "); //공백으로 명령어와 데이터 구분

            String command = splitOperation[0];
            int data = Integer.parseInt(splitOperation[1]);

            //명령어가 I라면 숫자를 삽입 - 삽입할때는 두 힙에 전부 삽입해야 하며, Map에도 기록해야됨.
            if(command.equals("I")){
                maxHeap.add(data); //최대 힙에 넣기
                minHeap.add(data); //최소 힙에 넣기

            }
            //삭제 명령어 - 삭제할때는 반드시, Map을 확인해야됨.
            else{
                //삭제 시 힙이 비어있으면 패스
                if(maxHeap.isEmpty()) continue;
                //최댓값 삭제
                if(data == 1){
                    int maxValue = maxHeap.poll();
                    minHeap.remove(maxValue);
                }
                //최솟값 삭제
                else{
                    int minValue = minHeap.poll();
                    maxHeap.remove(minValue);

                }
            }
        }

        //연산이 모두 끝난 후에 힙이 비어있다면, [0,0], 아니면 [최대, 최소]
        if(!maxHeap.isEmpty()){
            answer[0] = maxHeap.poll();
            answer[1] = minHeap.poll();
        }

        return answer;
    }

}
