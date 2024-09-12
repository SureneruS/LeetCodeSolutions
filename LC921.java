class Solution {
    public int minAddToMakeValid(String s) {
        int openRemaining = 0;
        int openAdded = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                openRemaining++;
            }
            else {
                if (openRemaining > 0) {
                    openRemaining--;
                }
                else {
                    openAdded++;
                }
            }
        }

        return openAdded + openRemaining;
    }
} 