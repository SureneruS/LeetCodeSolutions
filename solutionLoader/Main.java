class Main {
    public static void main(String[] args) {
        try {
            String problemId = args[0];
            DynamicSolutionLoader.executeTestCases(problemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}