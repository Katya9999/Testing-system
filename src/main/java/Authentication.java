import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Authentication {
    private static File file;

    public static File getFile() {
        return file;
    }

    public static boolean authenticateUser(String password){
        file = Registration.getFile();
        try(Scanner scanner = new Scanner(file)) {
            String s;
            while (scanner.hasNext()){
                s = scanner.nextLine();
                if(s.contains("password")) {
                    s = s.split(": ")[1];
                    if(password.equals(s)){
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
