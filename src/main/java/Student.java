import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Student implements StudentCommands {
    private String login;
    private String password;
    private Rating studentRate;

    public Student(String login, String password) {
        this.login = login;
        this.password = password;
        this.studentRate = new Rating(login, password);
    }

    @Override
    public void startTest() {
        Scanner scanner = new Scanner(System.in);
        TestBuilder testBuilder = new TestBuilder();
        System.out.println("Введите номер теста для прохождения:");
        int testNumber;
        String testNumberStr = null;
        TestSet.listTest();
        System.out.println("Для выхода в предыдущее меню, введите exit");
        while(true) {
            try {
                testNumberStr = scanner.next();
                testNumber = Integer.parseInt(testNumberStr);
                break;
            } catch (Exception e) {
                assert testNumberStr != null;
                if (testNumberStr.equals("exit"))
                    return;
                else {
                    System.out.println("Введенный номер - не число. Попробуйте еще раз");
                }
            }
        }

        try {
            String testName = TestSet.testNames.get(testNumber);
            Test test = testBuilder.downloadTest(testName);
            HashMap<Question, HashSet<Answer>> map = test.getMap();
            int points = 0;
            for (Map.Entry<Question, HashSet<Answer>> m: map.entrySet()) {
                System.out.println(m.getKey().getQuestion());
                for(Answer answer: m.getValue()){
                    System.out.println(answer.getAnswer());
                }
                System.out.println("Введите вариант ответа(латинскими буквами): ");
                String answer = scanner.next();
                if(answer.contains("exit"))
                    endTest();
                if (!answer.contains(")"))
                    answer = answer + ")";

                HashMap<Question, Answer> answerHashMap = test.getCorrectAnswerMap();
                if(answerHashMap.get(m.getKey()).getAnswer().contains(answer)){
                    System.out.println("правильно");
                    points++;
                }else{
                    System.out.println("не правильно");
                }
            }
            this.studentRate.createRatingFileIfIsNotExist(testName);
            studentRate.calculateRate(points, map.size());
            System.out.println("Ваш балл за тест: " + studentRate.getCurrentRate() + " из 5");
            studentRate.saveRating(testName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void showResults() {
        HashSet<String> passedTests = new HashSet<>();
        int previousRate = 0;
        for (File file: Objects.requireNonNull(this.studentRate.getDir().listFiles())) {
            if(file.getName().contains(login)) {
                try(Scanner scanner = new Scanner(file)) {
                    passedTests.add(file.getName().split("_")[1].split("\\.")[0]);
                    previousRate = scanner.nextInt();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!passedTests.isEmpty()){
            System.out.println("Пройденные тесты:");
            for(String testName: passedTests){
                System.out.println(testName + ". Лучший результат: " + previousRate);
            }
        }

        if(passedTests.containsAll(TestSet.testNameSet)){
            System.out.println("Непройденные тесты отсутствуют");
        }else{
            System.out.println("Непройденные тесты:");
            for(String testName: TestSet.testNameSet){
                if(!passedTests.contains(testName))
                    System.out.println(testName);
            }
        }
    }

    @Override
    public void endTest() throws Exception {
        throw new Exception("Экстренное завершение теста");
    }
}
