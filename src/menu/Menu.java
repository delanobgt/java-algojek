package src.menu;

import src.utility.*;
import java.util.*;

public class Menu {
    private List items;
    private int tabSize;

    public Menu(List items) {
        this.items = items;
        this.tabSize = 0;
    }
    public Menu(Object[] items) {
        this.items = Arrays.asList(items);
        this.tabSize = 0;
    }
    public Menu(List items, int tabSize) {
        this.items = items;
        this.tabSize = tabSize;
    }
    public Menu(Object[] items, int tabSize) {
        this.items = Arrays.asList(items);
        this.tabSize = tabSize;
    }

    public void print() {
        for (int i = 0; i < items.size(); i++)
            System.out.printf("%s%d. %s\n", Tool.rep(' ', tabSize), i+1, items.get(i));
    }
}
