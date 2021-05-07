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
                        System.out.println("Вы вошли в систему как " + Authentication.getFile().getName());
                        if (Authentication.getFile().getName().equals("Administrator")){
                            //вызов администратора(его интерфейса)

                        }else{
                            //вызов студента(его интерфейса)
                        }
                    } else {
                        System.out.println("Введен неправильный пароль");
                    }
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
