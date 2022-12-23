package week04;

/**
 * @author : sh Lee
 * @date : 22. 12. 23.
 */

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 아이디어
 * 1. 그리디 하게, 여러작업이 있다면, 작업시간이 더 짧은 작업을 선택하는 것이 좀 더 빠름
 * 2. pq에 작업을 넣고, 작업시간이 짧은 순서-> 도착시간이 작은 순서 로 정렬을 함.
 * 3. 하나의 작업이 끝나면, 다음 작업을 꺼내는데, 작업 종료 시간 + 1 까지 도달한 작업이면 시간을 누적함.
 * pq는 맞지만 잘 안 풀려서 참고함.
 * 참고 : https://codevang.tistory.com/316
 */

public class Programmers_디스크_컨트롤러 {
    public int solution(int[][] jobs) {
        int answer = 0;

        //문제에 jobs배열이 요청시간 순서로 들어온다는 말이 없기 때문에 정렬 필요
        Arrays.sort(jobs, (o1,o2) -> {
            return o1[0] - o2[0];
        });

        PriorityQueue<Task> pq = new PriorityQueue<>(); //요청시간이 짧은 순서로 정렬.

        int index = 0;
        int jobEndTime = jobs[0][0]; //작업이 수행되고 난 직후의 시간 -  요청시간이 가장 짧은 걸로 스타트
        int taskCount = 0;

        //모든 작업을 수행할때 까지 반복,
        while(taskCount < jobs.length){

            //하나의 작업이 끝나는 시점까지 들어온 모든 요청을 넣어줌.
            while(index < jobs.length && jobs[index][0] <= jobEndTime){
                pq.add(new Task(jobs[index][0], jobs[index][1]));
                index++; //다음 작업도 확인해보기 위해 인덱스 증가.
            }

            //pq에 작업이 없는 경우는 하나의 작업이 끝나고, 시간이 지난 다음 다음 작업이 요청되는 상황
            //0,3 -> 4,6 이런식으로 작업이 들어온다면,{0,3} 작업이 끝나도 {4,6}작업이 시작되지 않은 상황
            //이런 경우라면, 작업이 끝나는 시간을 다음 위치로 재조정 해야됨 => {0,3}이 끝나면 끝나는 시간은 3 => 4로 재조정
            if(pq.isEmpty()){
                jobEndTime = jobs[index][0];
                continue;
            }
            //pq에 요청시간이 짧은 순서로 정렬했기 때문에, pq에서 하나 꺼내서 처리
            Task currentTask = pq.poll();

            answer += (currentTask.processTime + jobEndTime - currentTask.startTime);//수행시간 누적 : 작업이 걸리는 시간 + 이전작업이 끝나는 시간 - 작업 시작 시간.

            jobEndTime += currentTask.processTime;//이전 작업이 끝나는 시간 업데이트
            taskCount++;//작업 수 저장.
        }

        return answer / jobs.length;
    }

    static class Task implements Comparable<Task>{
        int startTime, processTime; //작업 요청 시간, 작업의 소요시간.

        Task(int startTime, int processTime){
            this.startTime = startTime;
            this.processTime = processTime;
        }

        @Override
        public int compareTo(Task o) {


            return this.processTime - o.processTime;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "startTime=" + startTime +
                    ", processTime=" + processTime +
                    '}';
        }
    }

    public static void main(String[] args) {
        Programmers_디스크_컨트롤러 s = new Programmers_디스크_컨트롤러();
        int[][] jobs = {{0,3},{1,9},{2,6}};
        System.out.println(s.solution(jobs));

        int[][] jobs2 = {{0,3},{4,6}};
        System.out.println(s.solution(jobs2));
    }
}

