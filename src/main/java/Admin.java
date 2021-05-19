import java.io.File;
import java.util.*;

public class Admin implements AdminCommands {
    private HashMap<Integer, String> students;

    private static final File dirCreds = new File("./Credentials");
    private static final File dirRatings = new File("./Ratings");

    public Admin() {

    }

    @Override
    public void removeTest(String testName, TestBuilder testBuilder) {
        testBuilder.deleteTest(testName);
    }

    @Override
    public void addQuestion(TestBuilder testBuilder) {
        HashMap<Question, HashSet<Answer>> map = testBuilder.getMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите вопрос:");
        String quest = scanner.nextLine();
        int numberQuestion = map.size() + 1;
        quest = numberQuestion + ". " + quest;
        Question question = new Question(quest);
        System.out.println("Введите варианты ответов. По окончанию ввода введите exit");
        HashSet<Answer> answers = new HashSet<>();
        char symbol = 'a';
        while(true){
            String str = scanner.nextLine();
            if(str.equals("exit")){
                break;
            }
            str = symbol + ") " + str;
            Answer answer = new Answer(str);
            answers.add(answer);
            symbol++;
        }
        if(answers.size()>=2)
            map.put(question, answers);
        else{
            System.out.println("Вопрос и ответы добавлены не были");
        }
    }

    @Override
    public void addAnswer(String testName, TestBuilder testBuilder) {
        try {
            Scanner scanner = new Scanner(System.in);
            Test test = testBuilder.downloadTest(testName);
            HashMap<Integer, Question> integerQuestionHashMap = new HashMap<>();
            int i = 1;
            for (Map.Entry<Question, HashSet<Answer>> m: test.getMap().entrySet()) {
                integerQuestionHashMap.put(i, m.getKey());
                i++;
                System.out.println(m.getKey().getQuestion());
                for (Answer answer : m.getValue()) {
                    System.out.println(answer.getAnswer());
                }
            }
            int questionNumber = scanner.nextInt();

            System.out.println("Введите ответ к вопросу:");
            HashSet<Answer> answers = test.getMap().get(integerQuestionHashMap.get(questionNumber));
            int l = answers.toArray().length - 1; //сет из ответов, всего ответа - 2
            String symbol = ((Answer) answers.toArray()[answers.toArray().length - 1]).getAnswer().split("\\)")[0]; //буква последнего ответа
            char x = symbol.toCharArray()[0];
            scanner.nextLine();
            String s = scanner.nextLine();
            s = ++x + ") " + s;
            Answer answer = new Answer(s);
            test.getMap().get(integerQuestionHashMap.get(questionNumber)).add(answer);
            testBuilder.saveTest();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCorrectAnswersToQuestions(TestBuilder testBuilder) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Question, Answer> correctAnswerMap = testBuilder.getCorrectAnswerMap();
        if(correctAnswerMap.size()!=0){
           Question question = (Question) testBuilder.getMap().keySet().toArray()[testBuilder.getMap().keySet().toArray().length -1];
           HashSet<Answer> answers = testBuilder.getMap().get(question);
            System.out.println(question.getQuestion());
            for (Answer answer: answers){
                System.out.println(answer.getAnswer());
            }
            System.out.println("Введите правильный вариант ответа(латинскими буквами): ");
            boolean flag =true;

            String answer = scanner.next();
            if (!answer.contains(")"))
                answer = answer + ")";

            while(flag) {
                for (Answer a : testBuilder.getMap().get(question)) {
                    if (a.getAnswer().contains(answer)) {
                        correctAnswerMap.put(question, a);
                        flag = false;
                    }
                }
                if(flag)
                    System.out.println("Вы не ввели ответ. Попробуйте снова");
            }
            return;
        }
        for (Map.Entry<Question, HashSet<Answer>> m: testBuilder.getMap().entrySet()) {
            System.out.println(m.getKey().getQuestion());
            for(Answer answer: m.getValue()){
                System.out.println(answer.getAnswer());
            }

            boolean flag =true;
            while(flag) {
                System.out.println("Введите правильный вариант ответа(латинскими буквами): ");
                String answer = scanner.next();
                if (!answer.contains(")"))
                    answer = answer + ")";

                for (Answer a : m.getValue()) {
                    if (a.getAnswer().contains(answer)) {
                        correctAnswerMap.put(m.getKey(), a);
                        flag = false;
                    }
                }
                if(flag)
                    System.out.println("Вы не ввели ответ. Попробуйте снова");
            }
        }
    }

    @Override
    public void deleteStudent(int studentNumber) {
        if(students.containsKey(studentNumber)) {
            String login = students.get(studentNumber);
            File file1 = new File(dirCreds + "/" + login + ".txt");
            file1.delete();
            for (File file: Objects.requireNonNull(dirRatings.listFiles())){
                if(file.getName().contains(login)){
                    file.delete();
                }
            }
            System.out.println("Студент " + login + " удален из системы");
        }else{
            System.out.println("Студента с таким номером не существует");
        }

    }

    @Override
    public void showStudentList() {
        students = new LinkedHashMap<>();
        int number = 1;
        for(File file: Objects.requireNonNull(dirCreds.listFiles())){
            if(!file.getName().contains("Administrator")){
                students.put(number, file.getName().split("\\.")[0]);
                System.out.println(number + " " + students.get(number));
                number++;
            }
        }
    }

    @Override
    public void deleteQuestion(TestBuilder testBuilder) {
        for (Map.Entry<Question, HashSet<Answer>> m: testBuilder.getMap().entrySet()) {
            System.out.println(m.getKey().getQuestion());
            for (Answer answer : m.getValue()) {
                System.out.println(answer.getAnswer());
            }
        }
        System.out.println("Введите номер вопроса для удаления: ");
        Scanner scanner = new Scanner(System.in);
        int numberQuestion;
        while(true) {
            try {
                numberQuestion = scanner.nextInt();
                if (numberQuestion > testBuilder.getMap().size()){
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Вы ввели неправильное значение, попробуйте снова");
            }
        }
        HashMap<Question, HashSet<Answer>> localMap = new LinkedHashMap<>();
        for (Map.Entry<Question, HashSet<Answer>> m: testBuilder.getMap().entrySet()){
            if(!m.getKey().getQuestion().contains(numberQuestion + ".")){
                localMap.put(m.getKey(), m.getValue());
            }
        }
        HashMap<Question, Answer> localCorrectAnswerMap = new LinkedHashMap<>();
        for (Map.Entry<Question, Answer> m: testBuilder.getCorrectAnswerMap().entrySet()){
            if(!m.getKey().getQuestion().contains(numberQuestion + ".")){
                localCorrectAnswerMap.put(m.getKey(), m.getValue());
            }
        }
        testBuilder.setMap(localMap);
        testBuilder.setCorrectAnswerMap(localCorrectAnswerMap);
        System.out.println("Вопрос удален");
    }

    @Override
    public void deleteAnswer(TestBuilder testBuilder) {
        boolean exit = false;
        while (!exit) {
            for (Map.Entry<Question, HashSet<Answer>> m : testBuilder.getMap().entrySet()) {
                System.out.println(m.getKey().getQuestion());
                for (Answer answer : m.getValue()) {
                    System.out.println(answer.getAnswer());
                }
            }
            System.out.println("Введите номер вопроса, в котором вы хотите удалить ответ");
            Scanner scanner = new Scanner(System.in);
            int numberQuestion = 0;
            Question question = null;
            while (true) {
                try {
                    String s = scanner.next();
                    if(s.equals("exit")){
                        exit = true;
                        break;
                    }
                    numberQuestion = Integer.parseInt(s);
                    Question lastQuestion = (Question) testBuilder.getMap().keySet().toArray()[testBuilder.getMap().keySet().toArray().length -1];
                    if (numberQuestion > Integer.parseInt(lastQuestion.getQuestion().split("\\.")[0])) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Вы ввели неправильное значение, попробуйте снова");
                }
            }
            if(exit){
                break;
            }
            HashSet<Answer> answers = null;
            for (Map.Entry<Question, HashSet<Answer>> m : testBuilder.getMap().entrySet()) {
                if (m.getKey().getQuestion().contains(numberQuestion + ".")) {
                    System.out.println(m.getKey().getQuestion());
                    question = m.getKey();
                    for (Answer answer : m.getValue()) {
                        System.out.println(answer.getAnswer());
                    }
                    answers = m.getValue();
                }
            }
            assert answers != null;
            if (answers.size() > 2) {
                System.out.println("Выберите ответ для удаления");
                String ans = null;
                boolean flag = true;
                while (flag) {
                    ans = scanner.next();
                    if(ans.equals("exit")){
                        exit = true;
                        break;
                    }
                    for (Map.Entry<Question, Answer> m : testBuilder.getCorrectAnswerMap().entrySet()) {
                        if (m.getKey().getQuestion().contains(numberQuestion + ".")) {
                            if (!m.getValue().getAnswer().contains(ans)) {
                                flag = false;
                            } else {
                                System.out.println("Этот ответ недоступен для удаления, выберите другой ответ");
                            }
                        }
                    }
                }
                //удалили ответ из мапы для нужного вопроса
                for (Answer answer : answers) {
                    if (answer.getAnswer().contains(ans)) {
                        testBuilder.getMap().get(question).remove(answer);
                    }
                }

            } else {
                System.out.println("Выберите вопрос в котором более двух вариантов ответа");
            }
        }




    }

}
