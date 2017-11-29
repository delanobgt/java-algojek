package src.menu;

import src.utility.*;
import java.util.*;

public class ValidatedMenu {
    private List items;
    private int tabSize;
	private Validator validator;
    private boolean hasBackButton = true;

    public ValidatedMenu(List items, Validator validator) {
        this.items = items;
		this.validator = validator;
        this.tabSize = 0;
    }
    public ValidatedMenu(Object[] items, Validator validator) {
        this.items = Arrays.asList(items);
		this.validator = validator;
        this.tabSize = 0;
    }
    public ValidatedMenu(List items, Validator validator, int tabSize) {
        this.items = items;
		this.validator = validator;
        this.tabSize = tabSize;
    }
    public ValidatedMenu(Object[] items, Validator validator, int tabSize) {
        this.items = Arrays.asList(items);
		this.validator = validator;
        this.tabSize = tabSize;
    }

    public void print() {
        for (int i = 0; i < items.size(); i++) {
			System.out.printf("%s%d. %s%s\n",
								Tool.rep(' ', tabSize),
								i+1,
								items.get(i),
								validator.getValidationCode(i)==0?"zero":validator.getValidationCode(i)==-1?"minusOne":"one");
		}
    }

    public void print(String minusMsg, String zeroMsg) {
        for (int i = 0; i < items.size(); i++) {
            if (i == items.size()-1 && hasBackButton)
                System.out.printf("%s%d. %s\n",
                                    Tool.rep(' ', tabSize),
                                    0,
                                    items.get(i));
            else
                System.out.printf("%s%d. %s%s\n",
    				Tool.rep(' ', tabSize),
    				i+1,
    				items.get(i),
    				validator.getValidationCode(i)==0?zeroMsg:validator.getValidationCode(i)==-1?minusMsg:"");
		}
    }

    public void setHasBackButton(boolean flag) {
        this.hasBackButton = flag;
    }
}
