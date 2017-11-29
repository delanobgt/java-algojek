package src.utility;

import src.utility.Tool;
import java.util.List;

public class QuoteBox {
    private static final int WIDTH = "\\--------------------------------------+-------------------------+----------------------------------/".length()+5;
    private List<String> lst;
    private String result;

    public QuoteBox(String filePath) {
        this.lst = Tool.getStringListFromTextFile(filePath);
        int minLen = getMinimumStringLength();
        int tabSize = (WIDTH-minLen)/2;
        String tab = Tool.rep(' ', tabSize);

        StringBuilder sb = new StringBuilder();
        for (String s : lst)
            sb.append(tab).append(s).append('\n');
        result = sb.toString();
    }

    public String toString() {
        return result;
    }

    private int getMinimumStringLength() {
        int minimumLength = Integer.MAX_VALUE;
        for (int i = 1; i < lst.size(); i++)
            minimumLength = Math.min(minimumLength, lst.get(i).length());
        return minimumLength;
    }
}
