import java.util.HashSet;


//д/б метод подгружающий тесты(через сканирование папки Tests с тестами)
//методы для подгрузки тестов и имен тестов следует вынести в служебный класс
public class TestSet {
    public static HashSet<Test> testSet = new HashSet<>();
    public static HashSet<String> testNameSet = new HashSet<>();

}
