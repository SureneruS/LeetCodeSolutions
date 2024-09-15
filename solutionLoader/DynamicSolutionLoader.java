import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicSolutionLoader {

    private static final String SOLUTIONS_DIRECTORY = "solutions";
    private static final String COMPILED_DIRECTORY = "compiled_solutions";
    private static final String SOLUTION_CLASS_NAME = "Solution";
    private static final String SOURCE_FILE_EXTENSION = ".java";
    private static final String TEST_CASES_METHOD_NAME = "getTestCases";
    private static final Logger LOGGER = Logger.getLogger(DynamicSolutionLoader.class.getName());

    public static List<Object> executeTestCases(String problemId) throws Exception {
        Class<?> solutionClass = loadSolutionClass(problemId);
        return executeTestCases(solutionClass);
    }

    private static List<Object> executeTestCases(Class<?> solutionClass) throws Exception {
        Constructor<?> constructor = solutionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object instance = constructor.newInstance();

        Method getTestCasesMethod = findMethodByName(solutionClass, TEST_CASES_METHOD_NAME)
                .orElseThrow(() -> new RuntimeException("Test cases method not found in class " + solutionClass.getName()));
        getTestCasesMethod.setAccessible(true);

        List<Object[]> testCases = (List<Object[]>) getTestCasesMethod.invoke(instance);
        
        if (testCases.isEmpty()) {
            throw new RuntimeException("No test cases found in class " + solutionClass.getName());
        }

        Method solutionMethod = findSolutionMethod(solutionClass, testCases.get(0))
                .orElseThrow(() -> new RuntimeException("Suitable solution method not found in class " + solutionClass.getName()));
        solutionMethod.setAccessible(true);

        return testCases.stream()
                .map(testCase -> {
                    try {
                        Object result = solutionMethod.invoke(instance, testCase);
                        LOGGER.log(Level.INFO, "\nINPUT\n" + Arrays.toString(testCase) + "\n\nOUTPUT\n" + result.toString());
                        return solutionMethod.invoke(instance, testCase);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error executing test case: " + Arrays.toString(testCase), e);
                        return null;
                    }
                })
                .toList();
    }

    private static Optional<Method> findSolutionMethod(Class<?> clazz, Object[] sampleTestCase) {
        Arrays.stream(sampleTestCase).map(Object::getClass).forEach(clazzz -> LOGGER.info(clazzz.getName()));
        Arrays.stream(clazz.getDeclaredMethods()).filter(method -> Modifier.isPublic(method.getModifiers()) && !method.getName().equals(TEST_CASES_METHOD_NAME) && method.getParameterCount() == sampleTestCase.length).forEach(method -> {
            LOGGER.info(method.getName());
            Arrays.stream(method.getParameterTypes()).forEach(zzz -> LOGGER.info(zzz.getName()));
        });
        return Arrays.stream(clazz.getDeclaredMethods()).filter(method -> Modifier.isPublic(method.getModifiers()) && !method.getName().equals(TEST_CASES_METHOD_NAME) && method.getParameterCount() == sampleTestCase.length)
                .findFirst();
    }

    private static Class<?> loadSolutionClass(String problemId) throws IOException, ClassNotFoundException {
        String sourceFilePath = buildSourceFilePath(problemId);
        ensureFileExists(sourceFilePath);
        if (shouldCompile(sourceFilePath)) {
            compileSourceFile(sourceFilePath);
        }
        return loadCompiledClass();
    }

    private static String buildSourceFilePath(String problemId) {
        return SOLUTIONS_DIRECTORY + File.separator + problemId + SOURCE_FILE_EXTENSION;
    }

    private static void ensureFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Source file not found or is not a valid file: " + filePath);
        }
    }

    private static boolean shouldCompile(String sourceFilePath) {
        File compiledFile = new File(COMPILED_DIRECTORY + File.separator + SOLUTION_CLASS_NAME + ".class");
        File sourceFile = new File(sourceFilePath);
        return !compiledFile.exists() || sourceFile.lastModified() > compiledFile.lastModified();
    }

    private static void compileSourceFile(String sourceFilePath) throws IOException {
        javax.tools.JavaCompiler compiler = javax.tools.ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IOException("No Java compiler available. Ensure you're running on a JDK, not a JRE.");
        }

        File compiledDir = new File(COMPILED_DIRECTORY);
        if (!compiledDir.exists() && !compiledDir.mkdirs()) {
            throw new IOException("Failed to create compiled directory: " + COMPILED_DIRECTORY);
        }

        OutputStream compilationOutputStream = new ByteArrayOutputStream();
        int compilationResult = compiler.run(null, compilationOutputStream, compilationOutputStream, "-d", COMPILED_DIRECTORY, sourceFilePath);

        LOGGER.log(Level.INFO, compilationOutputStream.toString());

        if (compilationResult != 0) {
            throw new IOException("Compilation failed for file: \n" + sourceFilePath);
        }
    }

    private static Class<?> loadCompiledClass() throws IOException, ClassNotFoundException {
        URL[] classPath = {new File(COMPILED_DIRECTORY).toURI().toURL()};
        try (URLClassLoader classLoader = new URLClassLoader(classPath)) {
            return classLoader.loadClass(SOLUTION_CLASS_NAME);
        }
    }

    private static Optional<Method> findMethodByName(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals(methodName))
                .findFirst();
    }
}
