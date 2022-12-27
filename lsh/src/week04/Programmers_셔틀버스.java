package week04;

/**
 * @author : sh Lee
 * @date : 22. 12. 26.
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 아이디어
 * 1. 분으로 계산을 하기 때문에 시 -> 분으로 바꿔준다
 * 2. 09:00 부터 t(셔틀 운행 간격)만큼 증가시켜가면서, 각각의 시간에 대해, 몇명의 크루를 태워갈 수 있는지 계산한다.
 * 3. 크루들의 정보는 queue에 넣고 정렬해서 시간이 빠른 사람들 부터 하나씩 빼준다 - 새로운 데이터를 넣을 게 아니므로, pq보단 그냥 큐를 사용한다.(pq 사용시, 하나 뺼때마다 정렬이 일어나서 느림)
 * => 3번 수정 - 기존에 배열에 들어가 있기 때문에 따로 큐에 넣지 않고 배열의 인덱스를 이용하면 훨씬 빠르다.
 * 4. 마지막 셔틀이 태울 수 있는 시간중에서 가장 늦은 시간이 콘이 탈 수 있는 시간이 되고, 이를 반환해주면 된다.
 */
public class Programmers_셔틀버스 {

    //분의 경우, 가장 큰 수가 나오는 경우를 계산해봐도 23*60 + 59 = 약 1300 => 즉 int로 해도 충분하다
    static int timeToMinute(String time){

        String[] temp = time.split(":"); //시간의 경우, ":"로 구분되어있기 때문에 :로 나눈 후,계산해준다.

        return Integer.parseInt(temp[0]) * 60 + Integer.parseInt(temp[1]); //시 * 24 + 분
    }

    //결과 출력을 위해 분을 HH:MM 형태로 바꾸는 메서드
    static String minuteToTime(int min){

        String hour = String.format("%02d",min / 60);
        String minute = String.format("%02d",min % 60);

        return hour + ":" + minute;
    }

    /*n : 셔틀 운행 횟수, t : 셔틀 운행 간격, m : 한 셔틀에 탈 수 있는 최대 크루 수*/
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";

        //크루들을 시간순서대로 태울 예정이므로 빠른시간이 먼저 와야되는데, 정렬되어있다는 말이 없으므로 정렬을 한번해준다.
        Arrays.sort(timetable);

        int index = 0; //첫번째 크루부터 시작한다.
        int busCount = 0;
        int busTime = 0;
        int startTime = timeToMinute("09:00");//셔틀은 9시부터 시작하기 때문에 , 9시를 분으로 환산한 값을 시작 시간으로 한다.

        //셔틀 운행 횟수 만큼 반복한다.
        for(int i = 0; i < n; i++){

            //매 반복시에 시간 추가 - t분마다 셔틀 운행
            busTime = startTime + (t * i);

            //버스에 타는 인원수
            busCount = 0;

            //해당 시간까지 탈 수 있는 크루계산해서 인덱스 증가시키기.
            //인덱스가 주어진 timetable을 벗어나면 안되고, 셔틀 운행시간보다 크면 안되고, 태울수 있는 셔틀의 최대인원을 넘지 않아야 됨.
            while(index < timetable.length && (timeToMinute(timetable[index]) <= busTime) && (busCount < m)){
                busCount++; //버스 승객수 증가.
                index++;
            }
        }

        // 마지막버스에 모든 사람이 다 탔다면, 마지막사람보다 1분더 빨리 오면 됨.
        if(busCount == m) answer = minuteToTime(timeToMinute(timetable[index-1]) - 1);

        //마지막 버스에 모든 사람이 다 타지 않았다면, 마지막 버스가 도착하는 시간이 콘이 타는 시간.
        else{
            answer = minuteToTime(busTime);
        }

        return answer;
    }
}
