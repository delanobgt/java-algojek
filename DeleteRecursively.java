import java.util.*;
import java.io.*;

public class DeleteRecursively {

    private static void deleteRecursively(File file, String ext) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                deleteRecursively(f, ext);
            } else if (f.getName().endsWith(ext)) {
                f.delete();
            }
        }
    }

    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        //System.out.print("Target Folder    : "); String folderName = sc.nextLine();
        //System.out.print("Target Extension : "); String extension = sc.nextLine();
		//deleteRecursively(new File(folderName), extension);
		deleteRecursively(new File("."), ".class");
    }
}
