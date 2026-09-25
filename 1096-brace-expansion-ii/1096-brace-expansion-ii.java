class Solution {
    public List<String> braceExpansionII(String expression) {
       int[] index = {0};
        Set<String> result = parse(expression, index);
        List<String> list = new ArrayList<>(result);
        Collections.sort(list);
        return list;
    }

    // Parses a concatenation sequence until ',' or '}' or end of string.
    private Set<String> parse(String s, int[] i) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (i[0] < s.length() && s.charAt(i[0]) != ',' && s.charAt(i[0]) != '}') {
            Set<String> factor;
            char c = s.charAt(i[0]);

            if (c == '{') {
                i[0]++; // skip '{'
                factor = parseUnion(s, i);
                i[0]++; // skip '}'
            } else {
                factor = new HashSet<>();
                factor.add(String.valueOf(c));
                i[0]++;
            }

            Set<String> next = new HashSet<>();
            for (String a : result) {
                for (String b : factor) {
                    next.add(a + b);
                }
            }
            result = next;
        }

        return result;
    }

    // Parses a comma-separated union until '}'.
    private Set<String> parseUnion(String s, int[] i) {
        Set<String> union = new HashSet<>();

        while (true) {
            Set<String> expr = parse(s, i);
            union.addAll(expr);

            if (i[0] < s.length() && s.charAt(i[0]) == ',') {
                i[0]++; // skip ','
            } else {
                break;
            }
        }

        return union;
    }
}
