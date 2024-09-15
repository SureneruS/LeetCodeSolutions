import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        try {
            String problemId = "LC139";
            Object[] params = {"HelloWorld", List.of("Hello", "World")};
            Object result = DynamicSolutionLoader.executeSolution(problemId, params);
            System.out.println("\nInput:\n" + Arrays.toString(params) + "\n");
            System.out.println("Output:\n" + result + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}