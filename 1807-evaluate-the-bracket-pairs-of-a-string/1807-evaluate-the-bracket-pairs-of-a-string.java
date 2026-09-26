class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>(Math.max(1, knowledge.size() * 2));
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder sb = new StringBuilder(s.length());
        int n = s.length();
        int i = 0;

        while (i < n) {
            char c = s.charAt(i);

            if (c == '(') {
                // Find matching ')'. No nested brackets, so this is safe.
                int close = s.indexOf(')', i + 1);
                String key = s.substring(i + 1, close);
                String value = map.get(key);
                sb.append(value == null ? "?" : value);
                i = close + 1;
            } else {
                sb.append(c);
                i++;
            }
        }

        return sb.toString();
    }
}