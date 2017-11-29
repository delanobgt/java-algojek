package src.utility;

import src.playerstuff.Person;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

public class SaveHandler {

    public static final String SAVE_SLOT_NOT_USED = Tool.rep(' ',10)+"<no save data>\n";

    public static void save(Object obj, String name) {
        File dir = null;
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            dir = new File("saved"); dir.mkdir(); //create folder
            file = new File("saved\\"+name); file.createNewFile(); //create new file
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (Exception ex) {}
        }
    }

    public static Object load(String name) {
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File("saved\\"+name);
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (Exception ex) {}
        }
    }

    public static boolean checkDuplicate(String name) {
        try {
            File dir = new File("saved"); dir.mkdir();
            for (File file : dir.listFiles()) {
                String curName = file.getName();
                if (curName.matches("save\\d_.*\\.algojek")) {
                    curName = curName.substring(16, curName.length()-8);
                    if (curName.equals(name)) return true;
                }
            }
        } catch (Exception ex) {System.out.println(ex);}
        return false;
    }

    public static String[][] getSaveData() {
        String[] saveNames = {SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED};
        String[] saveNamesAndDates = {SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED};
        File dir = new File("saved"); dir.mkdir();
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            if (fileName.matches("save\\d_.*\\.algojek")) {
                int idx = (int)(fileName.charAt(4)-'0');
                if (idx >= 4) continue;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                Person person = (Person)load(fileName);
                String tmp = String.format("%-20s %s\n", fileName.substring(6, fileName.length()-8), sdf.format(file.lastModified()) ) +
                        String.format("%sDay: %d, Money: %,d\n", Tool.rep(' ',34), person.getDay(), person.getMoney());
                saveNamesAndDates[idx] = tmp;
                saveNames[idx] = fileName;
            }
        }
        return new String[][] {saveNames, saveNamesAndDates};
    }
}
