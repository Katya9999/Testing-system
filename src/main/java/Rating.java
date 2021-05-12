import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Rating {
    private String login;
    private static final File dir = new File("src/main/resources/Ratings");
    private int currentRate;

    public File getDir(){
        return dir;
    }

    public int getCurrentRate() {
        return currentRate;
    }

    Rating(String login, String password){
        this.login = login;
    }

    public void createRatingFileIfIsNotExist(String testName){
        try {
            if(Arrays.stream(Objects.requireNonNull(dir.listFiles())).noneMatch(x-> x.getName().contains(login + "_" + testName))) {
                Files.createFile(Path.of("src/main/resources/Ratings/" + login + "_" + testName + ".txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateRate(int pointsForTest, int numberOfQuestions){
        //>90% - эт 5
        //>65% и <=90% -4
        //>50% и <=65% -3
        //<50% - 2

        double percent =(double) pointsForTest/numberOfQuestions * 100;
        if(percent > 90){
            currentRate = 5;
        }
        else if(percent > 65){
            currentRate = 4;
        }
        else if(percent > 50){
            currentRate = 3;
        }
        else{
            currentRate = 2;
        }
    }

    public void saveRating(String testName) {
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            if(file.getName().contains(login + "_" + testName)){
                try (Scanner scanner = new Scanner(file)) {
                    try {
                        int previousRate = scanner.nextInt();
                        System.out.println("Ваша предыдущая оценка: " + previousRate);
                        if (previousRate < currentRate) {
                            try (PrintWriter printWriter = new PrintWriter(dir + "/" + login + "_" + testName + ".txt")) {
                                printWriter.println(currentRate);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        try (PrintWriter printWriter = new PrintWriter(dir + "/" + login + "_" + testName + ".txt")) {
                            printWriter.println(currentRate);
                        } catch (FileNotFoundException x) {
                            x.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
