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

    public static void save(Object obj, String name) {
        try {
            File dir = new File("saved"); dir.mkdir(); //create folder
            File file = new File("saved\\saved_"+name+".txt"); file.createNewFile(); //create new file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.flush();
            os.close();
        } catch (Exception ex) {System.out.println(ex);}
    }

    public static Object load(String name) {
        try {
            File file = new File("saved\\saved_"+name+".txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            return is.readObject();
        } catch (Exception ex) {System.out.println(ex);}
        return null;
    }

    public static boolean checkDuplicate(String name) {
        try {
            File file = new File("saved\\saved_"+name+".txt");
            return file.exists();
        } catch (Exception ex) {System.out.println(ex);}
        return false;
    }

    public static List<List<String>> getSaveList() {
        List<List<String>> lst = new ArrayList<>();
        List<String> saveNames = new ArrayList<>();
        List<String> saveNamesAndDates = new ArrayList<>();
        File dir = new File("saved"); dir.mkdir();
        for (File file : dir.listFiles()) {
            String fileName = file.getName();
            if (fileName.startsWith("saved_") && fileName.endsWith(".txt")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                saveNamesAndDates.add( String.format("%-20s %s", fileName.substring(6, fileName.length()-4), sdf.format(file.lastModified()) ) );
                saveNames.add(fileName.substring(6, fileName.length()-4));
            }
        }
        lst.add(saveNames);
        lst.add(saveNamesAndDates);
        return lst;
    }
}
