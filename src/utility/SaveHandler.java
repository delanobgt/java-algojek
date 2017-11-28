package src.utility;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

public class SaveHandler {

    public static final String SAVE_SLOT_NOT_USED = Tool.rep(' ',11)+"<no save data>";

    public static void save(Object obj, String name) {
        try {
            File dir = new File("saved"); dir.mkdir(); //create folder
            File file = new File("saved\\"+name); file.createNewFile(); //create new file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.flush();
            os.close();
        } catch (Exception ex) {System.out.println(ex);}
    }

    public static Object load(String name) {
        try {
            File file = new File("saved\\"+name);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            return is.readObject();
        } catch (Exception ex) {System.out.println(ex);}
        return null;
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
        String[] saveNames = {SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED};
        String[] saveNamesAndDates = {SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED, SAVE_SLOT_NOT_USED};
        File dir = new File("saved"); dir.mkdir();
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            if (fileName.matches("save\\d_.*\\.algojek")) {
                int idx = (int)(fileName.charAt(4)-'0');
                if (idx >= 5) continue;
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                saveNamesAndDates[idx] = String.format("%-20s %s", fileName.substring(6, fileName.length()-8), sdf.format(file.lastModified()) );
                saveNames[idx] = fileName;
            }
        }
        return new String[][] {saveNames, saveNamesAndDates};
    }
}
