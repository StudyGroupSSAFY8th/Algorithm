package week04;

/**
 * @author : sh Lee
 * @date : 22. 12. 21.
 */

/*
백준 - 공유기 설치와 조금 다르지만 비슷한 문제
참고 : https://small-stap.tistory.com/81
 */
public class Programmers_기지국_설치 {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;

        int start = 1; //아파트는 1번부터 시작

        //하나의 기지국이 커버가능한 아파트의 수는 (2*W + 1)

        //기지국이 설치된 위치에서, 해당 기지국의 전파거리가 닿을 수 있는 최소 위치보다 탐색 시작한 값이 더 작다면 기지국 설치가 필요함.
        for(int station : stations){

            //탐색중인 위치(leftStart)가 전파가 도달 가능한 최소 위치(station - w)보다 작다면 기지국이 필요하다는 의미
            if(start < station - w){
                int end = station - w; //탐지할 끝 범위

                int length = end - start; //탐색할 범위 - 몇개의 아파트를 확인 할것인지

                //해당 범위에 최소 몇개의 기지국이 필요한지.
                //w*2 + 1 => 기지국이 커버 가능한 크기
                int count = length / (w * 2 + 1);

                //나머지가 0이 아니라면, 전파가 도달하지 못하는 구역이 남는 다는 뜻이므로 개수를 하나 더 추가해야됨
                if(length % (w*2+1) != 0) count++;

                answer += count; //구한 기지국 수를 리턴
            }

            //해당위치가 기지국 범위 안이라면, 기지국의 최대 전파거리 + 1위치에서부터 다시 시작.
            start = station + w + 1;
        }


        //마지막 기지국의 전파도달 최대거리를 벗어나는 부분이 있을수 있음.
        // 0-- *** =>  이러한 모양처럼 ***과 같이 도달 안되는부분 존재.
        // 마지막 기지국의 최대 전파도달 위치가 n보다 작다면, 위의 반복문에서 구하지 못하고 남은 영역이 존재한다는 뜻
        // 남은 범위에 최소로 설치 가능한 기지국 수 구하기.
        if(stations[stations.length - 1] + w < n){
            start = stations[stations.length - 1] + w + 1;

            int end = n + 1;
            
            int length = end - start;

            int count = length / (w * 2 + 1);

            if(length % (w * 2 + 1) != 0) count ++;

            answer += count;
        }

        return answer;
    }
}
