import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            System.out.println("Введите логин: ");
            String login = scanner.next();
            if(Registration.isRegistration(login)){
                while(true) {
                    System.out.println("Введите пароль: ");
                    String password = scanner.next();
                    if (Authentication.authenticateUser(password)) {
                        //вся работа аутентифицированного юзера проводится здесь
                        System.out.println("Вы вошли в систему как " + Authentication.getFile().getName().split("\\.")[0]);
                        //System.out.println("Введите команду");
                        if (Authentication.getFile().getName().equals("Administrator")){
                            workAdmin();
                        }else{
                            workStudent(login, password);
                        }
                    } else {
                        System.out.println("Введен неправильный пароль");
                    }
                    //для того чтоб после работы выйти на стадию аутентификации
                    break;
                }
            }else{
                //регистрироваться могут только студенты
                System.out.println("Вы не зарегистрированы. Введите повторно логин для регистрации: ");
                String login1 = scanner.next();
                System.out.println("Введите пароль: ");
                String password = scanner.next();
                Registration.register(login1, password);
                System.out.println("Вы успешно зарегистрированы в системе. Для входа в систему: ");

            }

        }
    }

    public static void workAdmin(){
        TestBuilder testBuilder = new TestBuilder();
        Admin admin = new Admin();
        scanner.nextLine();
        String command;
        while(true) {
            System.out.println("Введите команду:");
            command = scanner.nextLine();
            if(command.equals("exit")) {
                break;
            }else if(command.equals("add test")){
                try {
                    System.out.println("Введите название теста");
                    boolean flag = false;
                    //проверка на уникальность названия
                    while (true) {
                        String testName = scanner.nextLine();

                        if (testName.equals("exit")) {
                            flag = true;
                            break;
                        }
                        if (!TestSet.testNameSet.contains(testName)) {
                            testBuilder.setTestName(testName);
                            break;
                        }
                        System.out.println("Тест с таким именем уже существует. Пожалуйста, введите другое название теста");
                    }
                    if (flag) {
                        throw new Exception();
                    }
                    String s;
                    while (true) {
                        admin.addQuestion(testBuilder);
                        System.out.println("Для выхода из меню введите exit, для продолжения введите next");
                        s = scanner.next();
                        if (s.equals("exit")) {
                            break;
                        }
                        if (s.equals("next")) {
                        }

                    }

                    System.out.println("Добавьте правильные ответы к тесту:");
                    admin.addCorrectAnswersToQuestions(testBuilder);
                    testBuilder.saveTest();
                    testBuilder = new TestBuilder();
                    scanner.nextLine();
                }catch (Exception e){

                }
            }else if(command.equals("edit test")){ //процесс редактирования
                System.out.println("Введите номер теста для редактирования:");
                TestSet.listTest();
                int testNumber = scanner.nextInt();
                String testName = TestSet.testNames.get(testNumber);
                scanner.nextLine();
                while(true) {
                    System.out.println("Введите команду для редактирования:");
                    command = scanner.nextLine();
                    if(command.equals("add answer")){
                        System.out.println("Введите номер вопроса для добавления ответа");
                        admin.addAnswer(testName, testBuilder);
                        testBuilder = new TestBuilder();
                    }else if(command.equals("exit")){
                        break;
                    }else if(command.equals("add question")){
                        try {
                            testBuilder.downloadTest(testName);
                            admin.addQuestion(testBuilder);
                            System.out.println("Добавьте правильный ответ к вопросу:");
                            admin.addCorrectAnswersToQuestions(testBuilder);
                            testBuilder.saveTest();
                            testBuilder = new TestBuilder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(command.equals("delete question")){
                        try {
                            testBuilder.downloadTest(testName);
                            admin.deleteQuestion(testBuilder);
                            testBuilder.saveTest();
                            testBuilder = new TestBuilder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(command.equals("delete answer")){
                        try {
                            testBuilder.downloadTest(testName);
                            admin.deleteAnswer(testBuilder);
                            testBuilder.saveTest();
                            testBuilder = new TestBuilder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Введена неправильная команда");
                    }
                }
            }else if(command.equals("delete test")){
                System.out.println("Введите номер теста для удаления:");
                TestSet.listTest();
                int testNumber = scanner.nextInt();
                String testName = TestSet.testNames.get(testNumber);
                admin.removeTest(testName, testBuilder);

            }else if(command.equals("delete student")){
                admin.showStudentList();
                System.out.println("Введите номер студента:");
                int studentNumber = scanner.nextInt();
                admin.deleteStudent(studentNumber);
            }
        }
    }

    public static void workStudent(String login, String password){
        System.out.println("Введите команду:");
        scanner.nextLine();
        Student student = new Student(login, password);
        TestBuilder testBuilder = new TestBuilder();
        String command;
        while(true){
            command = scanner.nextLine();
            if(command.equals("exit")){
                break;
            }else if(command.equals("start test")){
                student.startTest();
            }else if(command.equals("show results")){
                student.showResults();
            }else{
                System.out.println("Введена неверная команда");
            }
        }
    }
}
