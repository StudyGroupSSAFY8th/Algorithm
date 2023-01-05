package week06;
import java.util.Arrays;
/**
 * @author : sh Lee
 * @date : 23. 1. 5.
 */

/**
 *  아이디어
 *  이분탐색을 이용.
 *  이분탐색으로 심사를 받는데 걸리는 최소시간을 찾음
 *  해당 시간까지 몇명의 사람을 처리할 수 있는지 계산해봄 - 모듈러 연산을 이용해서 해당시간에 몇명처리가 되는 지 확인
 *  처리인원이  최대인원보다 크거나 같으면, 최소시간을 찾는 것이므로 시간을 줄여봄
 *  처리인원이 최대인원보다 작다면 해당시간에 모두 처리가 불가능 => 즉, 시간을 늘려야됨.
 */
public class Programmers_입국심사 {
    //뽑은 시간으로 몇명 처리가 가능한지 구하는 메서드
    static long processingMember(int[] times, long selectTime){
        long returnCount = 0;

        for(int i = 0; i < times.length; i++){
            //각 관리관당 해당 시간에 몇명을 처리할 수 있는 지 확인.
            // 관리관의 처리 시간이 7분이고, 주어진 시간이 15분이면 => 15/7 = 2, 2명을 처리할 수 있음.
            returnCount += selectTime / times[i];
        }

        return returnCount;
    }
    //주어진 시간으로 이분탐색을 하는 메서드
    static long binarySearch(int[] times, long start, long end, int n){

        if(start >= end) return end;

        long mid = (start + end) / 2;

        //해당시간으로 몇명 처리가능 한지 계산
        long tempProcessingCount = processingMember(times, mid);

        //처리가능한 인원이 최대 인원보다 많다면 시간을 줄여봐야됨.
        if(tempProcessingCount >= n){
            return binarySearch(times,start, mid,n);
        }
        //처리가능한 인원이 최대 인원보다 적다면, 시간을 늘려야됨.
        else{
            return binarySearch(times,mid+1,end, n);
        }

    }

    public long solution(int n, int[] times) {
        long answer = 0;

        Arrays.sort(times);


        answer = binarySearch(times,(long)times[0], (long)times[times.length-1] * n+1, n);

        return answer;
    }
}
