import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TestBuilder {
    private String testName;
    private HashMap<Question, HashSet<Answer>> map = new LinkedHashMap<>();
    private HashMap<Question, Answer> correctAnswerMap = new LinkedHashMap<>();
    private static final File dir = new File("src/main/resources/Tests");

    public void setMap(HashMap<Question, HashSet<Answer>> map) {
        this.map = map;
    }

    public void setCorrectAnswerMap(HashMap<Question, Answer> correctAnswerMap) {
        this.correctAnswerMap = correctAnswerMap;
    }

    private static final File dirResponses = new File("src/main/resources/Tests/Test responses");

    public void setTestName(String testName) {
        this.testName = testName;
    }

    TestBuilder(){
        downloadTestNames();
    }

    public String getTestName() {
        return testName;
    }

    public HashMap<Question, HashSet<Answer>> getMap() {
        return map;
    }

    public HashMap<Question, Answer> getCorrectAnswerMap() {
        return correctAnswerMap;
    }

    public void deleteTest(String testName){
        for (File item: Objects.requireNonNull(dir.listFiles())){
            if(item.getName().contains(testName)) {
                item.delete();
            }
        }
        for (File item: Objects.requireNonNull(dirResponses.listFiles())){
            if(item.getName().contains(testName)) {
                item.delete();
            }
        }
        File path = new File("src/main/resources/Ratings");
        for (File item: Objects.requireNonNull(path.listFiles())){
            if(item.getName().contains(testName)) {
                item.delete();
            }
        }
    }

    private void downloadTestNames(){
        for (File item: Objects.requireNonNull(dir.listFiles())){
            if(item.getName().contains("txt")) {
                TestSet.testNameSet.add(item.getName().split("\\.")[0]);
            }
        }
        int count = 1;
        for (String s: TestSet.testNameSet) {
            TestSet.testNames.put(count, s);
            count++;
        }
    }

    public Test downloadTest(String testName) throws Exception {
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
            downloadCorrectAnswers(testName);
            Test test = new Test(this);
            TestSet.testSet.add(test);
            return test;
        }else{
            throw new Exception("Нет такого теста");
        }
    }

    private void downloadCorrectAnswers(String testName){
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

    public void saveTest(){
        try(PrintWriter printWriter = new PrintWriter(dir + "/" + testName + ".txt")){
            for (Map.Entry<Question, HashSet<Answer>> m: map.entrySet()){
                printWriter.println(m.getKey().getQuestion());
                for (Answer answer: m.getValue()){
                    printWriter.println(answer.getAnswer());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try(PrintWriter printWriter = new PrintWriter(dirResponses + "/" + testName)){
            for (Map.Entry<Question, Answer> m: correctAnswerMap.entrySet()){
                printWriter.println(m.getKey().getQuestion());
                printWriter.println(m.getValue().getAnswer());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
