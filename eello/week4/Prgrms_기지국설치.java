class Solution {
    
    public int solution(int n, int[] stations, int w) {
        int ans = 0;
        int coverage = 2 * w + 1; // 한 기지국에서 전파가 닿는 아파트 개수

        // 1번 ~ 맨 앞 기지국 사이에 전파가 닿지 않는 아파트가 1개라도 있으면
        if (stations[0] - w > 1) {
            // 1번 아파트와 첫번째 기지국 사이에 전파가 닿지 않는 아파트 수
            int uncoverCnt = stations[0] - w - 1;
            ans += calNeededStation(uncoverCnt, coverage);
        }

        // i번째 기지국과 i-1번째 기지국 사이에 필요한 기지국 수 계산
        for (int i = 1; i < stations.length; i++) {
            int start = stations[i - 1] + w + 1; // i-1번째 기지국의 오른쪽 전파가 닿지 않는 아파트 최소 번호
            int end = stations[i] - w - 1; // i번째 기지국의 왼쪽 전파가 닿지 않는 아파트의 최대 번호

            // i번째 기지국과 i-1번째 기지국 사이의 아파트가 모두 이미 전파를 받고 있으면
            if (end < start)
                continue;

            // i번째 기지국과 i-1번째 기지국 사이의 전파가 닿지 않는 아파트 수
            int uncoverCnt = end - start + 1;
            ans += calNeededStation(uncoverCnt, coverage);
        }

        // 마지막 기지국의 오른쪽 전파가 맨 끝 아파트까지 닿지 않는다면
        if (stations[stations.length - 1] + w < n) {
            // 마지막 아파트와 마지막 기지국 사이에 전파가 닿지 않는 아파트 수
            int uncoverCnt = n - (stations[stations.length - 1] + w);
            ans += calNeededStation(uncoverCnt, coverage);
        }

        return ans;
    }

    // 전파가 닿지 않는 아파트 수(uncoverCnt)에서
    // 모든 아파트에 전파가 닿기 위해 필요한 최소 기지국 수 리턴
    private int calNeededStation(int uncoverCnt, int coverage) {
        int station = uncoverCnt / coverage;
        if (uncoverCnt % coverage != 0) {
            // uncoverCnt가 coverage보다 작아도 최소 1개 필요
            // or 딱 나누어 떨어지지 않으면 1개가 더 필요
            station++;
        }

        return station;
    }
}