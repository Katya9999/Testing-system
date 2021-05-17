import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



public class TestSet {
    public static HashSet<Test> testSet = new HashSet<>();
    public static HashSet<String> testNameSet = new HashSet<>();
    public static HashMap<Integer, String> testNames = new HashMap<>();

    public static void listTest(){
        for(Map.Entry<Integer, String> m: testNames.entrySet()){
            System.out.println(m.getKey() + " " + m.getValue());
        }
    }
}
