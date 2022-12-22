import java.util.HashMap;
import java.util.Map;

class Solution {
    
    private static class Salesman {
        private Salesman referral;
        private int income;

        public Salesman(Salesman referral) {
            this.referral = referral;
        }

        public void addIncome(int income) {
            int fees = (int) (income * 0.1);
            this.income += income - fees;

            if (this.referral != null && fees > 0) {
                this.referral.addIncome(fees);
            }
        }
    }

    private Map<String, Salesman> salesmans = new HashMap<>();

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        this.salesmans.put("-", new Salesman(null)); // center
        for (int i = 0; i < enroll.length; i++) {
            Salesman salesman = new Salesman(this.salesmans.get(referral[i]));
            this.salesmans.put(enroll[i], salesman);
        }

        for (int i = 0; i < seller.length; i++) {
            this.salesmans.get(seller[i]).addIncome(100 * amount[i]);
        }

        int[] ans = new int[enroll.length];
        for (int i = 0; i < enroll.length; i++) {
            ans[i] = salesmans.get(enroll[i]).income;
        }

        return ans;
    }
}