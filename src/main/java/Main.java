import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true){
            Scanner scanner = new Scanner(System.in);
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
                            while(true) {
                                System.out.println();
                                break;
                                //вызов администратора(его интерфейса)
                            }

                        }else{
                            while(true) {
                                System.out.println();
                                //вызов студента(его интерфейса)
                                break;
                            }
                        }
                    } else {
                        System.out.println("Введен неправильный пароль");
                    }
                    //для того чтоб после работы выйти на стадию аутентификации
                    break;
                }
            }else{
                System.out.println("Вы не зарегистрированы. Введите логин: ");
                String login1 = scanner.next();
                System.out.println("Введите пароль: ");
                String password = scanner.next();
                Registration.register(login1, password);
                System.out.println("Вы успешно зарегистрированы в системе");
            }

        }
    }
}
