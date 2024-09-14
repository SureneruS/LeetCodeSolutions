import java.util.List;

class Solution {
    public int rotatedDigits(int n) {
        int count = 0;
        List<Integer> goodDigits = List.of(2, 5, 6, 9);
        List<Integer> sameDigits = List.of(0, 1, 8);
        
        for (int i = 1; i <= n; i++) {
            int num = i;
            boolean isGood = true;
            boolean atleastOneGoodDigit = false;
            while(num > 0) {
                if (!goodDigits.contains(num) && !sameDigits.contains(num)) {
                    isGood = false;
                }

                if (goodDigits.contains(num)) {
                    atleastOneGoodDigit = true;
                }

                num /= 10;
            }

            if (atleastOneGoodDigit && isGood) {
                count ++;
            }
        }

        return count;
    }
}