package week09;

import java.util.*;

/**
 * 아이디어
 * 슬라이딩 윈도우로 접근해봄.
 * 1. 먼저 모든 종류의 보석수를 반복문을 돌면서 set에 추가하여 구함 -> log(n)
 * 2. 모든 보석을 포함 하려면 1번에서 구한 수 만큼은 되어야 함.
 * 3. 슬라이딩 윈도우로 앞에서 부터 탐색하는 식으로 해봄.
 * 4. 슬라이딩 윈도우의 크기는 1에서 구한 보석종류의 수로 잡아서 계산함
 * 5. 모든 진열장을 확인하면, 윈도우의 크기를 하나 늘려서 계산해봄.
 *
 */

public class Programmers_보석_쇼핑_시간초과 {


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

    //윈도우크기를 받아서 시작점부터 끝까지 탐색하고 답을 찾았는지 여부를 반환
    static int[] findSectionCheck(String[] gems,int totalJewelCount,int windowSize){

        int[] result = new int[2];

        //초기 윈도우에 들어가는 보석 저장.
        Map<String,Integer> windowJewel = initWindow(gems, windowSize);

        //초기 윈도우가 조건을 만족하면 반환.
        if(windowJewel.size() == totalJewelCount){
            result[0] = 1;
            result[1] = windowSize;
            return result;
        }


        int startIndex = 1;
        int lastIndex = startIndex + windowSize - 1;

        while(lastIndex < gems.length){


            //뺼때는 중복된 것이 있을 수도 있어서 개수를 -1하고 0이면 삭제.
            windowJewel.put(gems[startIndex-1],windowJewel.get(gems[startIndex-1]) - 1);
            if(windowJewel.get(gems[startIndex-1]) == 0){
                windowJewel.remove(gems[startIndex-1]);
            }

            windowJewel.put(gems[lastIndex],windowJewel.getOrDefault(gems[lastIndex],0)+1);

            //구한 값과 모든 보석의 종류 수가 같다면,
            if(windowJewel.size() == totalJewelCount){
                result[0] = startIndex + 1;
                result[1] = result[0] + windowSize - 1;
                return result;
            }

            startIndex++;
            lastIndex++;

        }


        return null;
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

        //윈도우 사이즈를 구해서 체크하기 - 윈도우 사이즈는 (보석종류의 수) ~ (gems사이즈)

        for(int windowSize = jewelCount; windowSize <= gems.length; windowSize++){
            answer = findSectionCheck(gems,jewelCount,windowSize);

            if(answer != null) break;
        }
        return answer;
    }

    public static void main(String[] args) {
        Programmers_보석_쇼핑_시간초과 s = new Programmers_보석_쇼핑_시간초과();
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
