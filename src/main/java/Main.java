import java.util.Scanner;
//TODO: не должно быть двух одинаковых логинов(запрет на повтор логинов)

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
                System.out.println("Вы не зарегистрированы. Введите логин: ");
                String login1 = scanner.next();
                System.out.println("Введите пароль: ");
                String password = scanner.next();
                Registration.register(login1, password);
                System.out.println("Вы успешно зарегистрированы в системе");

            }

        }
    }

    public static void workAdmin(){
        while(true) {
            Admin admin = new Admin();
            String command = scanner.next();
            break;
            //вызов администратора(его интерфейса)
        }
    }

    public static void workStudent(String login, String password){
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
