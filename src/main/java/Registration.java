import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class Registration {
    private static final File dirCreds = new File("src/main/resources/Credentials");
    private static File currentFile;

    public static File getFile() {
        return currentFile;
    }

    public static boolean isRegistration(String login){
        for (File file: Objects.requireNonNull(dirCreds.listFiles())){
            try(Scanner scanner = new Scanner(file)) {
                String s;
                while (scanner.hasNext()){
                    s = scanner.nextLine();
                    if(s.contains("login")){
                        s = s.split(": ")[1];
                        if(login.equals(s)){
                            currentFile = file;
                            return true;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void register(String login, String password){
        try {
            Files.createFile(Path.of("src/main/resources/Credentials/" + login + ".txt"));
            Files.writeString(Path.of("src/main/resources/Credentials/" + login + ".txt"), "login: " + login + "\n" + "password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
