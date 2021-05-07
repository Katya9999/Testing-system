import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TestBuilder {
    private String testName;
    private HashMap<Question, HashSet<Answer>> map = new LinkedHashMap<>();
    private HashMap<Question, Answer> correctAnswerMap = new LinkedHashMap<>();
    private static final File dir = new File("src/main/resources/Tests");
    private static final File dirResponses = new File("src/main/resources/Tests/Test responses");

    public String getTestName() {
        return testName;
    }

    public HashMap<Question, HashSet<Answer>> getMap() {
        return map;
    }

    public HashMap<Question, Answer> getCorrectAnswerMap() {
        return correctAnswerMap;
    }

    public void downloadTestNames(){
        for (File item: Objects.requireNonNull(dir.listFiles())){
            TestSet.testNameSet.add(item.getName().split("\\.")[0]);
        }
    }

    public void downloadTest(String testName){
        if (TestSet.testNameSet.contains(testName)){
            this.testName = testName;
            File file = new File(dir.getPath() + "/" + testName + ".txt");
            try(Scanner scanner = new Scanner(file)) {
                String s;
                Question quest = null;
                Answer answer;
                HashSet<Answer> answerSet = null;
                while(scanner.hasNext()) {
                    s = scanner.nextLine();
                    if(s.contains("?")){
                        quest = new Question(s);
                        answerSet = new LinkedHashSet<>();
                    }else{
                        answer = new Answer(s);
                        assert answerSet != null;
                        answerSet.add(answer);
                        answer = new Answer(s);
                        answerSet.add(answer);
                        this.map.put(quest, answerSet);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            Test test = new Test(this);
            TestSet.testSet.add(test);
            TestSet.testNameSet.add(testName);
        }
    }

    public void downloadCorrectAnswers(String testName){
        if (TestSet.testNameSet.contains(testName)){
            File file = new File(dirResponses.getPath() + "/" + testName);
            try(Scanner scanner = new Scanner(file)) {
                String s;
                Question quest = null;
                Answer answer = null;
                while(scanner.hasNext()){
                    s = scanner.nextLine();
                    if(s.contains("?")){
                        quest = new Question(s);
                    }else{
                        answer = new Answer(s);
                    }
                    if(quest!=null && answer!=null){
                        this.correctAnswerMap.put(quest, answer);
                    }
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
