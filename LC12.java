class Solution {
    public String intToRoman(int num) {
        if (num == 1) {
            return "I";
        }
        else if (num < 4) {
            return intToRoman(1) + intToRoman(num - 1);
        }
        else if (num < 5) {
            return intToRoman(1) + intToRoman(num + 1);
        }
        else if (num == 5) {
            return "V";
        }
        else if (num < 9) {
            return intToRoman(5) + intToRoman(num - 5);
        }
        else if (num < 10) {
            return intToRoman(1) + intToRoman(num + 1);
        }
        else if (num == 10) {
            return "X";
        }
        else if (num < 40) {
            return intToRoman(10) + intToRoman(num - 10);
        }
        else if (num < 50) {
            return intToRoman(10) + intToRoman(num + 10);
        }
        else if (num == 50) {
            return "L";
        }
        else if (num < 90) {
            return intToRoman(50) + intToRoman(num - 50);
        }
        else if (num < 100) {
            return intToRoman(10) + intToRoman(num + 10);
        }
        else if (num == 100) {
            return "C";
        }
        else if (num < 400) {
            return intToRoman(100) + intToRoman(num - 100);
        }
        else if (num < 500) {
            return intToRoman(100) + intToRoman(num + 100);
        }
        else if (num == 500) {
            return "D";
        }
        else if (num < 900) {
            return intToRoman(500) + intToRoman(num - 500);
        }
        else if (num < 1000) {
            return intToRoman(100) + intToRoman(num + 100);
        }
        else if (num == 1000) {
            return "M";
        }
        else {
            return intToRoman(1000) + intToRoman(num - 1000);
        }

    }
}