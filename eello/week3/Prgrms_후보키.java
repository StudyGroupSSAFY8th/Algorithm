import java.util.*;

class Solution {
    
    private String[][] relation;
    private List<int[]>[] candidateKey;

    public int solution(String[][] relation) {
        this.relation = relation;
        this.candidateKey = new List[relation[0].length + 1];

        int ans = 0;
        for (int i = 1; i <= relation[0].length; i++) {
            candidateKey[i] = new ArrayList<>();
            combination(new int[i], 0, 0);
            ans += candidateKey[i].size();
        }

        return ans;
    }

    private void combination(int[] sel, int idx, int k) {
        if (isCandidateKey(sel, k)) {
            return;
        }
        
        if (sel.length == k) {
            if (isUnique(sel)) {
                candidateKey[k].add(Arrays.copyOf(sel, k));
            }

            return;
        }

        if (relation[0].length == idx) {
            return;
        }

        combination(sel, idx + 1, k);
        sel[k] = idx;
        combination(sel, idx + 1, k + 1);
    }

    private boolean isUnique(int[] sel) {
        Set<String> set = new HashSet<>();
        for (String[] tuple : relation) {
            String s = "";
            for (int i : sel) {
                s += tuple[i];
            }

            if (set.contains(s)) {
                return false;
            } else set.add(s);
        }

        return true;
    }

    private boolean isCandidateKey(int[] sel, int k) {
        for (int s = 1; s <= k; s++) {
            for (int[] ck : candidateKey[s]) {
                int cnt = 0;
                for (int attr : ck) {
                    for (int i = 0; i < k; i++) {
                        if (sel[i] == attr) {
                            cnt++;
                        }
                    }
                }

                if (s == cnt)
                    return true;
            }
        }
        return false;
    }
}