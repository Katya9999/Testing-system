import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Test {
    private HashMap<Question, HashSet<Answer>> map;
    private HashMap<Question, Answer> correctAnswerMap;
    private String testName;

    public Test(TestBuilder testBuilder) {
        this.testName = testBuilder.getTestName();
        this.map= testBuilder.getMap();
        this.correctAnswerMap = testBuilder.getCorrectAnswerMap();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return testName.equals(test.testName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testName);
    }
}
