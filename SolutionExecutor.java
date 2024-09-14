import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

public class SolutionExecutor {
    public static void main(String[] args) {
        Object[] testCase = { "somewords", List.of("some", "words") };
        executeAndPrint(Solution.class, testCase);
    }

    private static void executeAndPrint(Class<?> solutionClass, Object... params) {
        try {
            Object solutionObject = solutionClass.getDeclaredConstructor().newInstance();

            Method solutionMethod = Arrays.stream(solutionClass.getDeclaredMethods())
                    .filter(method -> Modifier.isPublic(method.getModifiers())).findFirst().orElseThrow();
            System.out.println(solutionMethod);
            var solutionResult = solutionMethod.invoke(solutionObject, params);
            System.out.println(solutionResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
