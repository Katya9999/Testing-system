import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class StudentSet {
    private HashSet<Student> studentHashSet;
    private HashMap<String, String> creds;

    private static final File dirCreds = new File("./Credentials");

    StudentSet(){
        downloadStudentCreds();
        createStudentHashSet();
    }

    private void createStudentHashSet(){
        this.studentHashSet = new HashSet<>();
        for(Map.Entry<String, String> m: this.creds.entrySet()){
            Student student = new Student(m.getKey(), m.getValue());
            this.studentHashSet.add(student);
        }
    }

    private void downloadStudentCreds(){
        this.creds = new HashMap<>();
        for (File file : Objects.requireNonNull(dirCreds.listFiles())) {
            if(!file.getName().contains("Administrator")){
                try(Scanner scanner = new Scanner(file)) {
                    String s;
                    String login = null;
                    String password = null;
                    while(scanner.hasNext()){
                        s = scanner.nextLine();
                        if(s.contains("login")){
                            login = s.split(": ")[1];
                        }else{
                            password = s.split(": ")[1];
                        }
                        if(login!=null && password!=null){
                            this.creds.put(login, password);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
