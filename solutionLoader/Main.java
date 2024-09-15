import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        try {
            String problemId = null;

            if (args.length > 0) { 
                problemId = args[0];
            } else if (System.getenv("PROBLEM_ID") != null) {
                problemId = System.getenv("PROBLEM_ID");
            } else {
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.print("Enter problem ID: ");
                    problemId = scanner.nextLine();
                }
            }

            DynamicSolutionLoader.executeTestCases("LC130");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
