class Solution {
    public String reverseWords(String s) {
        char[] str = s.toCharArray();
        reverse(str, 0, str.length - 1);
        reverseWords(str);
        return cleanSpaces(str);
    }

    private String cleanSpaces(char[] str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(str[i] == ' ') i++;
        while (i < str.length) {
            if (str[i] != ' ') {
                sb.append(str[i++]);
            }
            else if (str[i] == ' ') {
                sb.append(' ');
                while (i < str.length && str[i] == ' ') i++;
            }
        }

        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private void reverseWords(char[] str) {
        int start = 0;
        int end = 0;
        while(start < str.length && end < str.length) {
            while (start < str.length && str[start] == ' ') start++;
            end = start;
            while (end < str.length && str[end] != ' ') end++;
            reverse(str, start, end - 1);
            start = end;
        }
    }

    private void reverse(char[] s, int start, int end) {
         while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;
            end--;
            start++;
         }
    }
}