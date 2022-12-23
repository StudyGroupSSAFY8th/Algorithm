package week04;

/**
 * @author : sh Lee
 * @date : 22. 12. 21.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 아이디어
 * 1. 주어진 정보를 탐색이 가능한 트리형태로 만든다.
 * 2. 여기서 필요한 정보는 선택한 노드의 부모정보만 있으면 된다. - dfs를 이용해서 노드의 부모를 탐색하는 식으로 하면 됨.
 * 3. seller배열의 원소를 하나씩 돌면서, 해당 원소를 시작노드로 하여, dfs탐색을 진행한다.
 * 4. 부모가 있다면 amount값의 10%를 계산해보고, 이 값이 1미만이라면, 부모에게 주지 않고, 자신이 가져간다.
 * 5. 이익금을 배열에 누적시켜서 리턴한다.
 */
public class Programmers_다단계_칫솔_판매 {

    static Map<String,String> parentMap;
    static Map<String, Integer> indexMap;

    static void addAmount(String member, int amount, int[] returnArray){

        String currentNode = member; //현재 노드

        //현재 노드가 '-'가 아니면 반복.
        while(!currentNode.equals("-")){

            int parentProfit = amount/10;//부모노드에 줘야되는 수익 - 10%로 떼줌
            int myProfit = amount - parentProfit;//내가 가져갈 수익.

            returnArray[indexMap.get(currentNode)] += myProfit; //내 수익 누적.

            amount = parentProfit; //다음 부모에서 처리할 수익업데이트

            //업데이트한 수익이 1미만이면, 내가 다 가져야되기 때문에 반복 종료
            if(amount < 1) break;

            currentNode = parentMap.get(currentNode);
        }
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] returnArray = new int[enroll.length];

        parentMap = new HashMap<>(); // 현재노드 : 부모노드로 매핑
        indexMap = new HashMap<>(); // 해당 노드가 몇번째인지(인덱스가 몇인지)

        //부모노드 매핑 정보 입력, '-'인 경우는 부모가 없음을 나타냄.
        for(int i = 0; i < enroll.length; i++){
            parentMap.put(enroll[i], referral[i]); //노드의 부모를 저장.
            indexMap.put(enroll[i], i);//노드의 인덱스 저장(결과 리턴시 배열에 리턴해야 돼서 저장 필요)
        }

        //판매자들 정보 하나씩 꺼내서 확인하면서 부모 쪽으로 배분
        for(int i = 0; i < seller.length; i++){
            String sellMember = seller[i]; //판매원 이름 뽑기.
            int sellAmount = amount[i] * 100; //해당 판매원의 판매수량 * 100;

            addAmount(sellMember, sellAmount, returnArray);

        }

        return returnArray;
    }
}
