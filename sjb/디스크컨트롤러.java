import java.util.*;

class Solution {
    class Job{
        int inTime;
        int jobTime;
        Job(int inTime,int jobTime){
            this.inTime = inTime;
            this.jobTime = jobTime;
        }
    }
    public int solution(int[][] jobs) {
        int answer = 0;
        PriorityQueue<Job> jobList = new PriorityQueue<>((a,b)->{
            if(a.jobTime==b.jobTime){
                return a.inTime - b.inTime;
            }else{
                return a.jobTime-b.jobTime;//작업이 제일 적은거먼저
            }
        });
        
        
        //잡 정렬
        Arrays.sort(jobs,(a,b)->a[0]-b[0]);
        
        
        int time =0;
        int idx = 0;
        
        int sum = 0;
        
        int endTime = 0;
        int check = 0;
        //
        while(idx<jobs.length||!jobList.isEmpty()){
            check++;
            //현시간에 들어온 job입력
            while(idx<jobs.length){
                if(jobs[idx][0]==time){
                    jobList.offer(new Job(jobs[idx][0],jobs[idx][1]));
                    idx++;
                }else{
                    break;
                }
            }
                        
            //맨앞에 있는것을 사용
            if(time==endTime){
                if(!jobList.isEmpty()){
                    Job dojob = jobList.poll();
                    endTime = endTime+dojob.jobTime;
                    sum+=time-dojob.inTime+dojob.jobTime;
                }else{
                    endTime = time+1;
                }
                
            }
            time+=1;
        }
        
        // System.out.println("----------");
        // System.out.println(endTime);
        // System.out.println(sum);
        // System.out.println(sum/jobs.length);
        answer = sum/jobs.length;
        return answer;
    }
}