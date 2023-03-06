package week09;

import java.util.*;

/**
 * 슬라이딩 윈도우로 모든 경우를 다 보는 것이 아닌 , 투포인터로 왼쪽, 오른쪽 인덱스를 움직이면서 해결
 */

public class Programmers_보석_쇼핑 {

    //보석 종류의 수를 구하는 메서드
    static int jewelNum(String[] gems){
        //보석 중복을 피하고 개수세기 위한 집합 자료구조
        Set<String> jewelSet = new HashSet<>();

        //반복문을 돌면서 set에 보석을 넣음
        for(String gem : gems){
            //보석이 포함되어있으면 패스
            if(jewelSet.contains(gem)) continue;

            jewelSet.add(gem);
        }
        return jewelSet.size();
    }

    //시작점부터 끝까지 탐색하고 답을 찾았는지 여부를 반환
    static int[] findSectionCheck(String[] gems,int totalJewelCount){

        int[] result = new int[2];
        result[0] = 0;
        result[1] = 100001;

        //초기 윈도우에 들어가는 보석 저장.
        Map<String,Integer> windowJewel = initWindow(gems, totalJewelCount);

        //초기 윈도우가 조건을 만족하면 반환.
        if(windowJewel.size() == totalJewelCount){
            result[0] = 1;
            result[1] = totalJewelCount;
            return result;
        }


        int startIndex = 0;
        int lastIndex = totalJewelCount;

        while(lastIndex < gems.length){

            windowJewel.put(gems[lastIndex],windowJewel.getOrDefault(gems[lastIndex],0)+1);


            //개수가 2개 이상이면 빼도 됨
            while(windowJewel.get(gems[startIndex]) >= 2){
                windowJewel.put(gems[startIndex],windowJewel.get(gems[startIndex]) - 1);
                startIndex++;
            }


            //구한 값과 모든 보석의 종류 수가 같다면,
            if(windowJewel.size() == totalJewelCount){

                //기존에 저장된 값과 비교해봐야 됨.
                if((result[1] - result[0] + 1) > (lastIndex - startIndex + 1)){
                    result[0] = startIndex + 1;
                    result[1] = lastIndex + 1;
                }
                else if((result[1] - result[0] + 1) == (lastIndex - startIndex +1)){
                    if(result[0] > startIndex+1){
                        result[0] = startIndex + 1;
                        result[1] = lastIndex + 1;
                    }
                }
            }


            lastIndex++;
        }


        return result;
    }

    //초기 윈도우에 들어가는 값 넣어두는 메서드
    static Map<String,Integer> initWindow(String[] gems, int windowSize){

        Map<String,Integer> windowJewel = new HashMap<>();

        for(int i = 0; i < windowSize; i++){
            windowJewel.put(gems[i],windowJewel.getOrDefault(gems[i],0) + 1);
        }

        return windowJewel;
    }


    public int[] solution(String[] gems) {
        int[] answer = {};

        int jewelCount = jewelNum(gems); //보석의 종류 수 구하기.


        return findSectionCheck(gems,jewelCount);
    }

    public static void main(String[] args) {
        Programmers_보석_쇼핑 s = new Programmers_보석_쇼핑();
        String[] gems1 = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
        System.out.println(Arrays.toString(s.solution(gems1)));

        String[] gems2 = {"AA", "AB", "AC", "AA", "AC"};
        System.out.println(Arrays.toString(s.solution(gems2)));

        String[] gems3 = {"XYZ", "XYZ", "XYZ"};
        System.out.println(Arrays.toString(s.solution(gems3)));

        String[] gems4 = {"ZZZ", "YYY", "NNNN", "YYY", "BBB"};
        System.out.println(Arrays.toString(s.solution(gems4)));
    }
}
