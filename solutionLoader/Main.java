import java.util.List;

class Main {
    public static void main(String[] args) {
        try {
            String problemId = "LC139";
            Object[] params = {"HelloWorld", List.of("Hello", "World")};
            Object result = DynamicSolutionLoader.executeSolution(problemId, params);
            System.out.println("Method execution result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}